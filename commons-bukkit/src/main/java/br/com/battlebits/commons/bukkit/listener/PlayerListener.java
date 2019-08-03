package br.com.battlebits.commons.bukkit.listener;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.bukkit.BukkitMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener implements Listener {


    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPreProcessCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().toLowerCase().startsWith("/me ")) {
            event.setCancelled(true);
        }
        if (event.getMessage().split(" ")[0].contains(":")) {
            event.getPlayer().sendMessage(ChatColor.RED + "Você não pode digitar comando com dois pontos (:)");
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onWhitelist(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().toLowerCase().startsWith("/whitelist")) {
            if (event.getPlayer().hasPermission("minecraft.command.whitelist") || event.getPlayer().hasPermission("bukkit.command.whitelist")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Commons.getDataServer().setJoinEnabled(!Bukkit.hasWhitelist());
                    }
                }.runTaskLater(BukkitMain.getInstance(), 2L);
            }
        }
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST) {
            BattleAccount battlePlayer = Commons.getAccountCommon().getBattlePlayer(event.getPlayer().getUniqueId());
            if (battlePlayer == null) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Servidor está em whitelist!");
            } else if (battlePlayer.hasGroupPermission(Group.DONATORPLUS)){
                event.allow();
            }
        }
    }
}
