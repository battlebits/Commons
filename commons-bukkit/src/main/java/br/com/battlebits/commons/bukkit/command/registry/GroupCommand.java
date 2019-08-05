package br.com.battlebits.commons.bukkit.command.registry;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.CommonsConst;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import br.com.battlebits.commons.bukkit.command.BukkitCommandArgs;
import br.com.battlebits.commons.command.CommandClass;
import br.com.battlebits.commons.command.CommandFramework;
import br.com.battlebits.commons.command.CommandSender;
import br.com.battlebits.commons.server.ServerType;
import br.com.battlebits.commons.translate.Language;
import br.com.battlebits.commons.translate.TranslateTag;
import static br.com.battlebits.commons.translate.TranslateTag.*;
import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class GroupCommand implements CommandClass {

    @CommandFramework.Command(name = "groupset", usage = "/<command> <player> <group>", groupToUse = Group.DEVELOPER, aliases = {"setgroup", "addgroup"}, noPermMessageId = TranslateTag.COMMAND_NO_PERMISSION, runAsync = true)
    public void groupset(BukkitCommandArgs cmdArgs) {
        final CommandSender sender = cmdArgs.getSender();
        final String[] args = cmdArgs.getArgs();
        Language lang = CommonsConst.DEFAULT_LANGUAGE;
        final String groupSetPrefix = tl(lang, COMMAND_GROUPSET_PREFIX);
        if (cmdArgs.isPlayer()) {
            lang = Commons.getAccountCommon().getBattleAccount(cmdArgs.getPlayer().getUniqueId()).getLanguage();
        }
        final Language language = lang;
        if (args.length != 2) {
            sender.sendMessage(groupSetPrefix + tl(language, COMMAND_GROUPSET_USAGE));
            return;
        }
        String groupName = args[1].toUpperCase();
        try {
            Group.valueOf(groupName);
        } catch (Exception e) {
            sender.sendMessage(groupSetPrefix + tl(language, COMMAND_GROUPSET_GROUP_NOT_EXIST));
            return;
        }
        final Group group = Group.valueOf(groupName);
        ServerType serverType = ServerType.DEFAULT;
        String targetPlayer = cmdArgs.getArgs()[0];
        boolean admin;
        if(cmdArgs.isPlayer()) {
            BattleAccount battleAccount = Commons.getAccountCommon().getBattleAccount(cmdArgs.getPlayer().getUniqueId());
            if(battleAccount.getGroup().ordinal() < Group.DEVELOPER.ordinal()) {
                sender.sendMessage(tl(language, COMMAND_GROUPSET_NOT_ADMIN));
                return;
            }
        }
        BattleAccount account = Commons.getAccount(targetPlayer);
        if(account == null) {
            sender.sendMessage(groupSetPrefix + tl(language, PLAYER_NOT_EXIST));
            return;
        }
        Group targetGroup = account.getGroup();
        if(targetGroup == group) {
            sender.sendMessage(groupSetPrefix + tl(language, COMMAND_GROUPSET_ALREADY_IN_GROUP));
            return;
        }
        account.setGroup(group);
        BukkitAccount bukkitAccount = (BukkitAccount) account;
        bukkitAccount.loadTags();
        sender.sendMessage(groupSetPrefix + tl(language, COMMAND_GROUPSET_CHANGE_GROUP));
    }
}