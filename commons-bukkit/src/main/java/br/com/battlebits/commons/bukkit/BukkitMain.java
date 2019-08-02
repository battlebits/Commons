package br.com.battlebits.commons.bukkit;

import br.com.battlebits.commons.Commons;
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
    }

    @Override
    public void onEnable() {
        registerListeners();
        Commons.getLogger().info("Plugin has enabled sucessfully");
    }

    @Override
    public void onDisable() {
        instance = null;
        Commons.getLogger().info("Plugin has disabled sucessfully");
    }

    public void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
    }
}