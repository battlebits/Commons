package br.com.battlebits.commons.bukkit.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

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
}
