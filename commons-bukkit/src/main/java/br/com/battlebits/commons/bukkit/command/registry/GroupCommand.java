package br.com.battlebits.commons.bukkit.command.registry;

        import br.com.battlebits.commons.Commons;
        import br.com.battlebits.commons.account.BattleAccount;
        import br.com.battlebits.commons.account.Group;
        import br.com.battlebits.commons.bukkit.account.BukkitAccount;
        import br.com.battlebits.commons.bukkit.command.BukkitCommandArgs;
        import br.com.battlebits.commons.command.CommandArgs;
        import br.com.battlebits.commons.command.CommandClass;
        import br.com.battlebits.commons.command.CommandFramework;
        import br.com.battlebits.commons.command.CommandSender;
        import br.com.battlebits.commons.server.ServerType;
        import br.com.battlebits.commons.translate.Language;
        import br.com.battlebits.commons.translate.TranslateTag;
        import org.bukkit.Bukkit;
        import org.bukkit.Server;
        import org.bukkit.entity.Player;

        import java.util.List;
        import java.util.UUID;

        import static br.com.battlebits.commons.translate.TranslateTag.*;
        import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class GroupCommand implements CommandClass {

    @CommandFramework.Command(name = "groupset", usage = "/<command> <player> <group>", groupToUse = Group.DEVELOPER, aliases = {"setgroup", "addgroup"}, noPermMessageId = TranslateTag.COMMAND_NO_PERMISSION, runAsync = true)
    public void groupset(BukkitCommandArgs cmdArgs) {
        final CommandSender sender = cmdArgs.getSender();
        final String[] args = cmdArgs.getArgs();
        Language lang = Language.PORTUGUESE;
        final String groupSetPrefix = tl(lang, COMMAND_GROUPSET_PREFIX) + " ";
        if (cmdArgs.isPlayer()) {
            lang = Commons.getAccountCommon().getBattleAccount(cmdArgs.getPlayer().getUniqueId()).getLanguage();
        }
        final Language language = lang;
        if (args.length != 2) {
            sender.sendMessage(groupSetPrefix + tl(language, COMMAND_GROUPSET_USAGE));
            return;
        }
        Group grupo;
        try {
            grupo = Group.valueOf(args[1].toUpperCase());
        } catch (Exception e) {
            sender.sendMessage(groupSetPrefix + tl(language, COMMAND_GROUPSET_GROUP_NOT_EXIST));
            return;
        }
        final Group group = grupo;
        ServerType serverType = ServerType.DEFAULT;
        boolean admin;
        if (cmdArgs.isPlayer()) {
            BattleAccount battleAccount = Commons.getAccountCommon().getBattleAccount(cmdArgs.getPlayer().getUniqueId());
            admin = battleAccount.getServerGroup() == Group.ADMIN;
            if (!admin) {
                if (group.ordinal() > Group.DONATORPLUS.ordinal()) {
                    sender.sendMessage(tl(language, COMMAND_GROUPSET_NOT_ADMIN));
                    return;
                }
                UUID uuid = cmdArgs.getPlayer().getUniqueId();
                if (uuid == null) {
                    sender.sendMessage(tl(language, PLAYER_NOT_EXIST));
                    return;
                }
                BattleAccount battleAccount1 = Commons.getAccountCommon().getBattleAccount(uuid);
                if (battleAccount1 == null) {
                    try {
                        battleAccount1 = Commons.getAccount(uuid);
                    } catch (Exception e) {
                        e.printStackTrace();
                        sender.sendMessage(groupSetPrefix + tl(language, SERVER_CANT_REQUEST_OFFLINE));
                        return;
                    }
                    if (battleAccount1 == null) {
                        sender.sendMessage(groupSetPrefix + tl(language, PLAYER_NEVER_JOINED));
                        return;
                    }
                }
                Group actualGroup = battleAccount1.getGroup();
                if (actualGroup == group) {
                    sender.sendMessage(groupSetPrefix + tl(language, COMMAND_GROUPSET_ALREADY_IN_GROUP));
                    return;
                }
                battleAccount1.setGroup(group);
                sender.sendMessage(groupSetPrefix + tl(language, COMMAND_GROUPSET_CHANGE_GROUP));
                return;
            }
        } else {
            //TODO: command for cmd
        }
    }
}
