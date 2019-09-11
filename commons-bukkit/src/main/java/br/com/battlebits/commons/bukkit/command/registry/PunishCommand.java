package br.com.battlebits.commons.bukkit.command.registry;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.account.punishment.Ban;
import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.command.BukkitCommandArgs;
import br.com.battlebits.commons.command.CommandClass;
import br.com.battlebits.commons.command.CommandFramework;
import br.com.battlebits.commons.command.CommandSender;
import br.com.battlebits.commons.translate.Language;
import br.com.battlebits.commons.util.DateUtils;
import org.bukkit.entity.Player;

import java.util.UUID;

import static br.com.battlebits.commons.translate.TranslateTag.*;
import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class PunishCommand implements CommandClass {

    @CommandFramework.Command(name = "ban", usage = "/<command> <player> <reason>", groupToUse = Group.ADMIN, runAsync = true)
    public void ban(BukkitCommandArgs cmdArgs) {
        final CommandSender sender = cmdArgs.getSender();
        final String[] args = cmdArgs.getArgs();
        Language lang = cmdArgs.getSender().getLanguage();
        String banPrefix = tl(lang, COMMAND_BAN_PREFIX);
        if (args.length != 2) {
            sender.sendMessage(banPrefix + tl(lang, COMMAND_BAN_USAGE));
            return;
        }
        UUID uuid = Commons.getUuidOf(args[0]);
        if (uuid == null) {
            sender.sendMessage(banPrefix + tl(lang, PLAYER_NOT_EXIST));
            return;
        }
        BattleAccount player = Commons.getOfflineAccount(uuid);
        if (player == null) {
            sender.sendMessage(banPrefix + tl(lang, PLAYER_NOT_EXIST));
            return;
        }
        if (player.getUniqueId() == sender.getUniqueId()) {
            sender.sendMessage(banPrefix + tl(lang, COMMAND_BAN_CANT_YOURSELF));
            return;
        }
        Ban currentBan = player.getPunishmentHistory().getCurrentBan();
        if (currentBan != null && !currentBan.isUnbanned() && currentBan.isPermanent()) {
            sender.sendMessage(banPrefix + tl(lang, COMMAND_BAN_ALREADY_BANNED));
            return;
        }
        if (player.isStaff()) {
            Group group = Group.ADMIN;
            if (cmdArgs.isPlayer()) {
                group = Commons.getAccountCommon().getBattleAccount(uuid).getServerGroup();
                if (group != Group.ADMIN && group != Group.DEVELOPER) {
                    sender.sendMessage(banPrefix + tl(lang, COMMAND_BAN_CANT_STAFF));
                    return;
                }
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                String space = " ";
                if (i >= args.length - 1) {
                    space = "";
                }
                builder.append(args[i] + space);
            }
            Ban ban = null;
            String playerIp = "";
            try {
                playerIp = player.getIpAddress();
            } catch (Exception ex) {
                playerIp = "OFFLINE";
            }
            if (cmdArgs.isPlayer()) {
                Player bannedBy = cmdArgs.getPlayer();
                ban = new Ban(bannedBy.getName(), bannedBy.getUniqueId(), playerIp, player.getServerConnected(), builder.toString());
                bannedBy = null;
            } else {
                ban = new Ban("CONSOLE", playerIp, player.getServerConnected(), builder.toString());
            }
            BukkitMain.getInstance().getPunishManager().ban(player, ban);

        }
    }

    @CommandFramework.Command(name = "tempban", usage = "/<command> <time> <reason>", aliases = {"tempbanir, ahktempban"}, groupToUse = Group.ADMIN)
    public void tempban(BukkitCommandArgs cmdArgs) {
        final CommandSender sender = cmdArgs.getSender();
        final String[] args = cmdArgs.getArgs();
        Language lang = cmdArgs.getSender().getLanguage();
        String tempbanPrefix = tl(lang, COMMAND_TEMPBAN_PREFIX);
        if (args.length != 3) {
            sender.sendMessage(tempbanPrefix + tl(lang, COMMAND_TEMPBAN_USAGE));
            return;
        }
        UUID uuid = Commons.getUuidOf(args[0]);
        if (uuid == null) {
            sender.sendMessage(tempbanPrefix + tl(lang, PLAYER_NOT_EXIST));
            return;
        }
        BattleAccount player = Commons.getOfflineAccount(uuid);
        if (player == null) {
            sender.sendMessage(tempbanPrefix + tl(lang, PLAYER_NOT_EXIST));
            return;
        }
        if (player.getUniqueId() == sender.getUniqueId()) {
            sender.sendMessage(tempbanPrefix + tl(lang, COMMAND_BAN_CANT_YOURSELF));
            return;
        }
        Ban currentBan = player.getPunishmentHistory().getCurrentBan();
        if (currentBan != null && !currentBan.isUnbanned() && currentBan.isPermanent()) {
            sender.sendMessage(tempbanPrefix + tl(lang, COMMAND_BAN_ALREADY_BANNED));
            return;
        }
        if (player.isStaff()) {
            Group group = Group.ADMIN;
            if (cmdArgs.isPlayer()) {
                group = Commons.getAccountCommon().getBattleAccount(uuid).getServerGroup();
                if (group != Group.ADMIN && group != Group.DEVELOPER) {
                    sender.sendMessage(tempbanPrefix + tl(lang, COMMAND_BAN_CANT_STAFF));
                    return;
                }
            }
            long expiresCheck;
            try {
                expiresCheck = DateUtils.parseDateDiff(args[1], true);
            } catch (Exception e) {
                sender.sendMessage(tempbanPrefix + tl(lang, COMMAND_TEMPBAN_INVALID_FORMAT));
                return;
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                String space = " ";
                if (i >= args.length - 1) {
                    space = "";
                }
                builder.append(args[i] + space);
            }
            Ban ban = null;
            String playerIp = "";
            try {
                playerIp = player.getIpAddress();
            } catch (Exception ex) {
                playerIp = "OFFLINE";
            }
            if (cmdArgs.isPlayer()) {
                Player bannedBy = cmdArgs.getPlayer();
                ban = new Ban(bannedBy.getName(), bannedBy.getUniqueId(), playerIp, player.getServerConnected(), builder.toString(), expiresCheck);
                bannedBy = null;
            } else {
                ban = new Ban("CONSOLE", playerIp, player.getServerConnected(), builder.toString());
            }
            BukkitMain.getInstance().getPunishManager().ban(player, ban);
        }
    }

    @CommandFramework.Command(name = "unban", usage = "/<command> <player>", aliases = {"ahkdesban", "desbanir"}, groupToUse = Group.ADMIN)
    public void unban(BukkitCommandArgs cmdArgs) {
        final CommandSender sender = cmdArgs.getSender();
        final String[] args = cmdArgs.getArgs();
        Language lang = cmdArgs.getSender().getLanguage();
        String unbanPrefix = tl(lang, COMMAND_UNBAN_PREFIX);
        if (args.length != 1) {
            sender.sendMessage(unbanPrefix + tl(lang, COMMAND_UNBAN_USAGE));
            return;
        }
        UUID uuid = Commons.getUuidOf(args[0]);
        if (uuid == null) {
            sender.sendMessage(unbanPrefix + tl(lang, PLAYER_NOT_EXIST));
            return;
        }
        BattleAccount player = Commons.getOfflineAccount(uuid);
        if (player == null) {
            sender.sendMessage(unbanPrefix + tl(lang, PLAYER_NOT_EXIST));
            return;
        }
        Ban currentBan = player.getPunishmentHistory().getCurrentBan();
        if (currentBan == null) {
            sender.sendMessage(unbanPrefix + tl(lang, COMMAND_UNBAN_NOT_BANNED));
            return;
        }
        BattleAccount unbannedBy = null;
        if (cmdArgs.isPlayer()) {
            unbannedBy = Commons.getAccountCommon().getBattleAccount(cmdArgs.getPlayer().getUniqueId());
        }
        BukkitMain.getInstance().getPunishManager().unban(unbannedBy, player, currentBan);
    }
}
