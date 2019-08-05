package br.com.battlebits.commons.bukkit.listener;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.event.update.UpdateEvent;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class AntiAfkListener implements Listener {
    private Map<Player, Location> locations = new HashMap<>();
    private Map<Player, Long> afkMap = new HashMap<>();

    public long getTime(Player p) {
        return System.currentTimeMillis() - afkMap.getOrDefault(p, System.currentTimeMillis());
    }

    public void resetTimer(Player p) {
        afkMap.put(p, System.currentTimeMillis());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onUpdate(UpdateEvent event) {
        if (event.getType() != UpdateEvent.UpdateType.SECOND)
            return;
        if (!BukkitMain.getInstance().isAntiAfkEnabled())
            return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            BattleAccount player = Commons.getAccount(p.getUniqueId());
            if (player == null)
                return;
            Location loc = locations.get(p);
            if (loc == null) {
                locations.put(p, p.getLocation().clone());
                continue;
            }

            Location l = p.getLocation();
            if (loc.getX() != l.getX() || loc.getY() != l.getY() || loc.getZ() != l.getZ()) {
                resetTimer(p);
            }

            long time = getTime(p);

            if (time >= 1000 * 60 * 3) { // 3 minutes
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF("LIMBO");
                p.sendPluginMessage(BukkitMain.getInstance(), "BungeeCord", out.toByteArray());
                continue;
            }

            locations.put(p, p.getLocation().clone());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLeave(PlayerQuitEvent e) {
        if (!BukkitMain.getInstance().isAntiAfkEnabled())
            return;
        locations.remove(e.getPlayer());
        afkMap.remove(e.getPlayer());
    }

}
