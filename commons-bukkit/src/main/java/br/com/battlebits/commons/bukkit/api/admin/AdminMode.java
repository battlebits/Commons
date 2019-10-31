package br.com.battlebits.commons.bukkit.api.admin;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.api.vanish.VanishAPI;
import br.com.battlebits.commons.bukkit.event.admin.PlayerAdminModeEvent;
import br.com.battlebits.commons.translate.Language;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static br.com.battlebits.commons.translate.TranslateTag.*;

public class AdminMode {
    private static Set<Player> admin = new HashSet<>();

    public static void setAdmin(Player p) {
        admin.add(p);
        PlayerAdminModeEvent event = new PlayerAdminModeEvent(p, PlayerAdminModeEvent.AdminMode.ADMIN, GameMode.CREATIVE);
        BukkitMain.getInstance().getServer().getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        p.setGameMode(event.getGameMode());
        Group group = VanishAPI.hidePlayer(p);
        Language l = Commons.getLanguage(p.getUniqueId());
        p.sendMessage(l.tl(COMMAND_ADMIN_PREFIX) + l.tl(COMMAND_ADMIN_ENABLED));
        p.sendMessage(l.tl(COMMAND_VANISH_PREFIX) + l.tl(COMMAND_VANISH_INVISIBLE, group.toString()));
    }

    public static void setPlayer(Player p) {
        PlayerAdminModeEvent event = new PlayerAdminModeEvent(p, PlayerAdminModeEvent.AdminMode.PLAYER, GameMode.SURVIVAL);
        BukkitMain.getInstance().getServer().getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        Language l = Commons.getLanguage(p.getUniqueId());
        if (admin.contains(p)) {
            p.sendMessage(l.tl(COMMAND_ADMIN_PREFIX) + l.tl(COMMAND_ADMIN_DISABLED));
            admin.remove(p);
        }
        p.sendMessage(l.tl(COMMAND_VANISH_PREFIX) + l.tl(COMMAND_VANISH_VISIBLE_ALL));
        p.setGameMode(event.getGameMode());
        VanishAPI.showPlayer(p);
    }

    public static boolean isAdmin(Player p) {
        return p != null && admin.contains(p);
    }

    public static int playersInAdmin() {
        return admin.size();
    }

    public static void removeAdmin(Player p) {
        admin.remove(p);
    }
}
