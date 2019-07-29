package br.com.battlebits.commons;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Commons extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("Plugin started successfully!");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin stopped successfully!");
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(event.getBlock().getType() == Material.GRASS)
            event.getBlock().getWorld().createExplosion(event.getBlock().getLocation(), 0.1f);
    }
}