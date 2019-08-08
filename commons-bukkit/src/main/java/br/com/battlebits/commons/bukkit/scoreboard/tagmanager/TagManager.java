package br.com.battlebits.commons.bukkit.scoreboard.tagmanager;

import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class TagManager {
    private BukkitMain main;

    public TagManager(BukkitMain main) {
        this.main = main;
    }

    public void onEnable() {
        main.getServer().getPluginManager().registerEvents(new TagListener(this), main);
        for (Player player : main.getServer().getOnlinePlayers()) {
            BukkitAccount account = BukkitAccount.getAccount(player.getUniqueId());
            if(account != null)
                account.setTag(account.getTag());
        }
    }

    public void removePlayerTag(Player p) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            final Team entryTeam = players.getScoreboard().getEntryTeam(p.getName());
            if(entryTeam != null && entryTeam.getEntries().contains(p.getName())) {
                entryTeam.removeEntry(p.getName());
                if(entryTeam.getEntries().isEmpty()) {
                    entryTeam.unregister();
                }
            }
        }
    }

    public void onDisable() {
        for (Player player : main.getServer().getOnlinePlayers()) {
            removePlayerTag(player);
        }
    }
}
