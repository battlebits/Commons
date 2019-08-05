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

import java.util.List;
import java.util.UUID;

import static br.com.battlebits.commons.translate.TranslateTag.*;
import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class GroupCommand implements CommandClass {

    @CommandFramework.Command(name = "groupset", usage = "/<command> <player> <group>", groupToUse = Group.DEVELOPER, aliases = {"setgroup", "addgroup"}, noPermMessageId = TranslateTag.COMMAND_NO_PERMISSION, runAsync = true)
    public void groupset(BukkitCommandArgs cmdArgs) {
        final CommandSender sender = cmdArgs.getSender();
        final String[] args = cmdArgs.getArgs();
        Language language = BukkitAccount.getAccount(cmdArgs.getPlayer().getUniqueId()).getLanguage();
        final String groupSetPrefix = tl(language, COMMAND_GROUPSET_PREFIX) + " ";
        if (args.length != 2) {
            sender.sendMessage(groupSetPrefix + tl(language, COMMAND_GROUPSET_USAGE));
            return;
        }
        Group group = null;
        try {
            group = Group.valueOf(args[1].toUpperCase());
        } catch (Exception e) {
            sender.sendMessage(groupSetPrefix + tl(language, COMMAND_GROUPSET_GROUP_NOT_EXIST));
            return;
        }
        final Group group1 = group;
        boolean admin = false;
        ServerType serverType = ServerType.DEFAULT;
        if (cmdArgs.isPlayer()) {
            BattleAccount battleAccount = Commons.getAccountCommon().getBattleAccount(cmdArgs.getPlayer().getUniqueId());
            if (battleAccount.getServerGroup() == Group.ADMIN) {
                admin = true;
            } else {
                admin = true;
            }
            if (group1.ordinal() <= Group.INFLUENCER.ordinal() && group1 != Group.DEFAULT) {
                sender.sendMessage(groupSetPrefix + tl(language, COMMAND_GROUPSET_GROUP_TEMPORARY));
                return;
            }
            if (!admin) {
                if (group1.ordinal() > Group.DONATORPLUS.ordinal()) {
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
                        Commons.getAccount(uuid);
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
                if (actualGroup == group1) {
                    sender.sendMessage(groupSetPrefix + tl(language, COMMAND_GROUPSET_ALREADY_IN_GROUP));
                    return;
                }
                battleAccount1.setGroup(group1);
                String message = groupSetPrefix + tl(language, COMMAND_GROUPSET_CHANGE_GROUP);
                message = message.replace("%player%", battleAccount1.getName() + "(" + battleAccount1.getUniqueId().toString().replace("-", "") + ")");
                message = message.replace("%group%", group1.name());
                sender.sendMessage(message);
            }
        }
    }
}
