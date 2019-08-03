package br.com.battlebits.commons.bukkit;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.bukkit.services.Services;
import br.com.battlebits.commons.bukkit.services.scoreboard.ScoreboardService;
import br.com.battlebits.commons.bukkit.services.scoreboard.impl.ScoreboardServiceImpl;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class BukkitMain extends JavaPlugin {

    @Getter
    private static BukkitMain instance;

    @Override
    public void onLoad() {
        instance = this;
        try {

            // TODO Check for config file and initialize Commons
            //Commons.initialize(getLogger(), );
        } catch (Exception e) {
            e.printStackTrace();
        }
        Services.add(ScoreboardService.class, new ScoreboardServiceImpl());
    }

    @Override
    public void onEnable() {
        registerListeners();
        Commons.getLogger().info("Plugin has enabled successfully");
    }

    @Override
    public void onDisable() {
        instance = null;
        Commons.getLogger().info("Plugin has disabled successfully");
    }

    public void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
    }
}
