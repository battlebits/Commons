package br.com.battlebits.commons.bukkit.command.registry;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.bukkit.api.admin.AdminMode;
import br.com.battlebits.commons.bukkit.api.chat.ChatAPI;
import br.com.battlebits.commons.bukkit.api.vanish.VanishAPI;
import br.com.battlebits.commons.bukkit.command.BukkitCommandArgs;
import br.com.battlebits.commons.command.CommandClass;
import br.com.battlebits.commons.command.CommandFramework;
import br.com.battlebits.commons.translate.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static br.com.battlebits.commons.translate.TranslateTag.*;
import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class StaffCommand implements CommandClass {
    @CommandFramework.Command(name = "admin", groupToUse = Group.ADMIN)
    public void admin(BukkitCommandArgs cmdArgs) {
        if (cmdArgs.isPlayer()) {
            Player p = cmdArgs.getPlayer();
            if (AdminMode.isAdmin(p)) {
                AdminMode.setPlayer(p);
            } else {
                AdminMode.setAdmin(p);
            }
        }
    }

    @CommandFramework.Command(name = "updatevanish", groupToUse = Group.ADMIN)
    public void updateVanish(BukkitCommandArgs cmdArgs) {
        if (cmdArgs.isPlayer()) {
            Player p = cmdArgs.getPlayer();
            VanishAPI.updateVanishToPlayer(p);
        }
    }

    @CommandFramework.Command(name = "visible", aliases = {"vis", "visivel"}, groupToUse = Group.ADMIN)
    public void visible(BukkitCommandArgs cmdArgs) {
        if (cmdArgs.isPlayer()) {
            Player p = cmdArgs.getPlayer();
            VanishAPI.showPlayer(p);
            Language l = cmdArgs.getSender().getLanguage();
            p.sendMessage(l.tl(COMMAND_VANISH_PREFIX)  + l.tl(COMMAND_VANISH_VISIBLE_ALL));
        }
    }

    @CommandFramework.Command(name = "invisible", aliases = {"invis", "invisivel"}, groupToUse = Group.ADMIN)
    public void invisible(BukkitCommandArgs cmdArgs) {
        if (cmdArgs.isPlayer()) {
            Player p = cmdArgs.getPlayer();
            BattleAccount battlePlayer = Commons.getAccountCommon().getBattleAccount(p.getUniqueId());
            Language l = battlePlayer.getLanguage();
            Group group = Group.DEFAULT;
            if (cmdArgs.getArgs().length > 0) {
                try {
                    group = Group.valueOf(cmdArgs.getArgs()[0].toUpperCase());
                } catch (Exception e) {
                    p.sendMessage(l.tl(COMMAND_VANISH_PREFIX) + l.tl(COMMAND_VANISH_RANK_NOT_EXIST));
                    return;
                }
                if (group.ordinal() >= battlePlayer.getServerGroup().ordinal()) {
                    p.sendMessage(l.tl(COMMAND_VANISH_PREFIX) + l.tl(COMMAND_VANISH_RANK_HIGH));
                    return;
                }
            } else {
                group = VanishAPI.hidePlayer(p);
                VanishAPI.setPlayerVanishToGroup(p, group);
                p.sendMessage(l.tl(COMMAND_VANISH_PREFIX) + l.tl(COMMAND_VANISH_INVISIBLE, group.toString()));
                return;
            }
        }
    }

    @CommandFramework.Command(name = "inventorysee", aliases = {"invsee", "inv"}, groupToUse = Group.ADMIN)
    public void inventorysee(BukkitCommandArgs cmdArgs) {
        if (cmdArgs.isPlayer()) {
            Player p = cmdArgs.getPlayer();
            BattleAccount battlePlayer = Commons.getAccountCommon().getBattleAccount(p.getUniqueId());
            Language l = battlePlayer.getLanguage();
            if (cmdArgs.getArgs().length == 0) {
                p.sendMessage(l.tl(COMMAND_INVENTORYSEE_PREFIX) + l.tl(COMMAND_INVENTORYSEE_USAGE));
            } else {
                Player player = Bukkit.getPlayer(cmdArgs.getArgs()[0]);
                if (player != null) {
                    p.sendMessage(l.tl(COMMAND_INVENTORYSEE_PREFIX) + l.tl(COMMAND_INVENTORYSEE_SUCCESS));
                    p.openInventory(player.getInventory());
                } else {
                    p.sendMessage(l.tl(COMMAND_INVENTORYSEE_NOT_FOUND));
                }
            }
        }
    }

    @CommandFramework.Command(name = "chat", groupToUse = Group.ADMIN)
    public void chat(BukkitCommandArgs cmdArgs) {
        Player p = cmdArgs.getPlayer();
        BattleAccount battlePlayer = Commons.getAccountCommon().getBattleAccount(p.getUniqueId());
        Language l = battlePlayer.getLanguage();
        if (cmdArgs.isPlayer()) {
            if (cmdArgs.getArgs().length == 0 ) {
                p.sendMessage(tl(COMMAND_CHAT_PREFIX) + tl(COMMAND_CHAT_USAGE));
                return;
            }
            if (cmdArgs.getArgs().length == 1) {
                if (cmdArgs.getArgs()[0].equalsIgnoreCase("on")) {
                    if (ChatAPI.getChatState() == ChatAPI.ChatState.ENABLED) {
                        p.sendMessage(l.tl(COMMAND_CHAT_PREFIX) + l.tl(COMMAND_CHAT_ALREADY_ENABLED));
                        return;
                    }
                    ChatAPI.setChatState(ChatAPI.ChatState.ENABLED);
                    p.sendMessage(l.tl(COMMAND_CHAT_PREFIX) + l.tl(COMMAND_CHAT_ENABLED));
                } else if (cmdArgs.getArgs()[0].equalsIgnoreCase("off")) {
                    if (ChatAPI.getChatState() == ChatAPI.ChatState.CREATOR) {
                        p.sendMessage(l.tl(COMMAND_CHAT_PREFIX) + l.tl(COMMAND_CHAT_ALREADY_DISABLED));
                        return;
                    }
                    ChatAPI.setChatState(ChatAPI.ChatState.CREATOR);
                    p.sendMessage(l.tl(COMMAND_CHAT_PREFIX) + l.tl(COMMAND_CHAT_DISABLED));
                } else {
                    p.sendMessage(l.tl(COMMAND_CHAT_PREFIX) + l.tl(COMMAND_CHAT_USAGE));
                }
            }
        }
    }

    @CommandFramework.Command(name = "clearchat", groupToUse = Group.ADMIN)
    public void cleachat(BukkitCommandArgs cmdArgs) {
        Player p = cmdArgs.getPlayer();
        BattleAccount battlePlayer = Commons.getAccountCommon().getBattleAccount(p.getUniqueId());
        Language l = battlePlayer.getLanguage();
        if (cmdArgs.isPlayer()) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                for (int i = 0; i < 100; i++) {
                    p.sendMessage("");
                }
                p.sendMessage(l.tl(COMMAND_CHAT_PREFIX) + l.tl(COMMAND_CHAT_SUCCESS));
            }
        }
    }
}
