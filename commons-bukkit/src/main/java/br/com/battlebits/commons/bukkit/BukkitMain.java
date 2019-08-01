package br.com.battlebits.commons.bukkit;

import br.com.battlebits.commons.Commons;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class BukkitMain extends JavaPlugin {

    private static BukkitMain instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Commons.getLogger().info("Plugin has enabled sucessfully");
    }

    @Override
    public void onDisable() {
        instance = null;
        Commons.getLogger().info("Plugin has disabled sucessfully");
    }

    public void registerListeners() {

    }
}
