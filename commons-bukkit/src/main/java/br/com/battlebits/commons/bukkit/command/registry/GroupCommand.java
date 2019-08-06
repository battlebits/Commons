package br.com.battlebits.commons.bukkit.command.registry;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.account.VoidBattleAccount;
import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.command.BukkitCommandArgs;
import br.com.battlebits.commons.command.CommandArgs;
import br.com.battlebits.commons.command.CommandClass;
import br.com.battlebits.commons.command.CommandFramework.Command;
import br.com.battlebits.commons.command.CommandFramework.Completer;
import br.com.battlebits.commons.command.CommandSender;
import br.com.battlebits.commons.translate.Language;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static br.com.battlebits.commons.translate.TranslateTag.*;
import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class GroupCommand implements CommandClass {

    @Command(name = "group", usage = "/<command> <player> <group>", groupToUse = Group.ADMIN, runAsync = true)
    public void group(BukkitCommandArgs cmdArgs) {
        CommandSender sender = cmdArgs.getSender();
        String[] args = cmdArgs.getArgs();
        Language lang = cmdArgs.getSender().getLanguage();
        String groupSetPrefix = tl(lang, COMMAND_GROUPSET_PREFIX);
        if (args.length != 2) {
            sender.sendMessage(groupSetPrefix + tl(lang, COMMAND_GROUPSET_USAGE));
            return;
        }
        String groupName = args[1].toUpperCase();
        try {
            Group.valueOf(groupName);
        } catch (Exception e) {
            sender.sendMessage(groupSetPrefix + tl(lang, COMMAND_GROUPSET_GROUP_NOT_EXIST));
            return;
        }
        Group group = Group.valueOf(groupName);
        String targetPlayer = cmdArgs.getArgs()[0];
        if (cmdArgs.isPlayer()) {
            BattleAccount battleAccount = Commons.getAccountCommon().getBattleAccount(cmdArgs.getPlayer().getUniqueId());
            if (battleAccount.getGroup().ordinal() < Group.ADMIN.ordinal()) {
                sender.sendMessage(tl(lang, COMMAND_GROUPSET_NOT_ADMIN));
                return;
            }
        }
        UUID uuid = Commons.getUuidOf(targetPlayer);
        if (uuid == null) {
            sender.sendMessage(groupSetPrefix + tl(lang, PLAYER_NOT_EXIST));
        }
        BattleAccount account = Commons.getAccount(uuid);
        if (account == null) {
            account = new VoidBattleAccount(Commons.getDataAccount().getAccount(uuid));
            if (account == null) {
                sender.sendMessage(groupSetPrefix + tl(lang, PLAYER_NOT_EXIST));
                return;
            }
        }
        Group targetGroup = account.getGroup();
        if (targetGroup == group) {
            sender.sendMessage(groupSetPrefix + tl(lang, COMMAND_GROUPSET_ALREADY_IN_GROUP));
            return;
        }
        account.setGroup(group);
        sender.sendMessage(groupSetPrefix + tl(lang, COMMAND_GROUPSET_CHANGE_GROUP));
    }

    @Completer(name = "group")
    public List<String> groupCompleter(CommandArgs args) {
        if (args.isPlayer()) {
            if (args.getArgs().length == 1) {
                ArrayList<String> players = new ArrayList<>();
                for (Player p : BukkitMain.getInstance().getServer().getOnlinePlayers()) {
                    if (p.getName().toLowerCase().startsWith(args.getArgs()[0].toLowerCase())) {
                        players.add(p.getName());
                    }
                }
                return players;
            } else if (args.getArgs().length == 2) {
                ArrayList<String> grupos = new ArrayList<>();
                for (Group group : Group.values()) {
                    if (group.toString().toLowerCase().startsWith(args.getArgs()[1].toLowerCase())) {
                        grupos.add(group.toString());
                    }
                }
                return grupos;
            }
        }
        return new ArrayList<>();
    }
}