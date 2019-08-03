package br.com.battlebits.commons.bukkit;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.bukkit.listener.PlayerListener;
import br.com.battlebits.commons.bukkit.services.Services;
import br.com.battlebits.commons.bukkit.services.scoreboard.ScoreboardService;
import br.com.battlebits.commons.bukkit.services.scoreboard.impl.ScoreboardServiceImpl;
import br.com.battlebits.commons.bukkit.translate.BukkitTranslationCommon;
import br.com.battlebits.commons.translate.TranslationCommon;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

@Getter
public class BukkitMain extends JavaPlugin {

    @Getter
    private static BukkitMain instance;

    private TranslationCommon translationCommon;

    @Override
    public void onLoad() {
        instance = this;

        Services.add(ScoreboardService.class, new ScoreboardServiceImpl());
    }

    @Override
    public void onEnable() {
        try {

            // TODO Check for config file and initialize Commons
            //Commons.initialize(getLogger(), );
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO Load Translations
        translationCommon = new BukkitTranslationCommon(null); // TODO Add translation storage
        translationCommon.onEnable();

        registerListeners();
        Commons.getLogger().info("Plugin has enabled successfully");
    }

    @Override
    public void onDisable() {
        instance = null;
        translationCommon.onDisable();
        Commons.getLogger().info("Plugin has disabled successfully");
    }

    public void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerListener(), this);
    }
}
