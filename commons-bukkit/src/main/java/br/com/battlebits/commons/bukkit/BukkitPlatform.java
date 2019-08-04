package br.com.battlebits.commons.bukkit;

import br.com.battlebits.commons.CommonPlatform;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Arquivo criado em 01/06/2017.
 * Desenvolvido por:
 *
 * @author Luãn Pereira.
 */
public class BukkitPlatform implements CommonPlatform {

    @Override
    public UUID getUUID(String name) {
        Player player = getPlayerExact(name, Player.class);
        return player != null ? player.getUniqueId() : null;
    }

    @Override
    public String getName(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return player != null ? player.getName() : null;
    }

    @Override
    public boolean isOnline(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return player != null && player.isOnline();
    }

    @Override
    public boolean isOnline(String name) {
        Player player = getPlayerExact(name, Player.class);
        return player != null && player.isOnline();
    }

    @Override
    public <T> T getPlayerExact(String name, Class<T> clazz) {
        Player found = null;
        if (name != null && !name.isEmpty()) {
            found = Bukkit.getPlayerExact(name);
            if (found == null) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getName().equalsIgnoreCase(name)) {
                        found = player;
                        break;
                    }
                }
            }
        }
        return found != null ? clazz.cast(found) : null;
    }

    @Override
    public void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(BukkitMain.getInstance(), runnable);
    }

}
