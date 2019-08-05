package br.com.battlebits.commons.bukkit.scoreboard.tagmanager;

import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import br.com.battlebits.commons.bukkit.scoreboard.ScoreboardAPI;
import org.bukkit.entity.Player;

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
        ScoreboardAPI.leaveCurrentTeamForOnlinePlayers(p);
    }

    public void onDisable() {
        for (Player player : main.getServer().getOnlinePlayers()) {
            removePlayerTag(player);
        }
    }
}
