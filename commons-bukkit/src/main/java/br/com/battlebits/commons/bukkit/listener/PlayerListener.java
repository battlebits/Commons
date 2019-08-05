package br.com.battlebits.commons.bukkit.listener;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.api.vanish.VanishAPI;
import br.com.battlebits.commons.bukkit.event.vanish.PlayerShowToPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;
import static br.com.battlebits.commons.translate.TranslateTag.*;
import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class PlayerListener implements Listener {


    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPreProcessCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().toLowerCase().startsWith("/me ")) {
            event.setCancelled(true);
        }
        if (event.getMessage().split(" ")[0].contains(":")) {
            event.getPlayer().sendMessage(tl(COMMAND_NO_PERMISSION));
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
            BattleAccount battlePlayer = Commons.getAccount(event.getPlayer().getUniqueId());
            if (battlePlayer == null) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, tl(SERVER_WHITELIST));
            } else if (battlePlayer.hasGroupPermission(Group.DONATORPLUS)){
                event.allow();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoinMonitor(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        VanishAPI.getInstance().updateVanishToPlayer(player);
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getUniqueId().equals(player.getUniqueId())) {
                continue;
            }
            PlayerShowToPlayerEvent eventCall = new PlayerShowToPlayerEvent(player, online);
            Bukkit.getPluginManager().callEvent(eventCall);
            if (eventCall.isCancelled()) {
                if (online.canSee(player)) {
                    online.hidePlayer(player);
                } else if (!online.canSee(player)) {
                    online.showPlayer(player);
                }
            }
        }
    }
}
