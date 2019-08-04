package br.com.battlebits.commons.bukkit.listener;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.backend.DataAccount;
import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

public class AccountListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public synchronized void onAsyncPreLogin(AsyncPlayerPreLoginEvent event) {
        if (Bukkit.getPlayer(event.getUniqueId()) != null) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(ChatColor.RED + "Você já está online!");
            return;
        }
        //TODO: loadAccount from BukkitAccount
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            return;
        }
        //TODO: check if player is null and block the access
    }
}
