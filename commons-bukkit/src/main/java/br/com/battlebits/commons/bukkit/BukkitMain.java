package br.com.battlebits.commons.bukkit;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.bukkit.command.BukkitCommandFramework;
import br.com.battlebits.commons.bukkit.listener.PlayerListener;
import br.com.battlebits.commons.bukkit.services.Services;
import br.com.battlebits.commons.bukkit.services.scoreboard.ScoreboardService;
import br.com.battlebits.commons.bukkit.services.scoreboard.impl.ScoreboardServiceImpl;
import br.com.battlebits.commons.bukkit.translate.BukkitTranslationCommon;
import br.com.battlebits.commons.command.CommandLoader;
import br.com.battlebits.commons.translate.TranslationCommon;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Field;

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

        try {
            new CommandLoader(new BukkitCommandFramework(this)).loadCommandsFromPackage(getFile(), "br.com.battlebits.commons.bukkit.command.register");
        } catch (Exception e) {
            getLogger().severe("An internal error happened when trying to register commands!");
            e.printStackTrace();
        }

        registerListeners();
        getServer().getScheduler().runTaskLater(this, () -> unregisterCommands("pl", "plugins", "icanhasbukkit", "ver", "version", "?", "help", "me"), 2L);
        Commons.getLogger().info("Plugin has enabled successfully");
    }

    @Override
    public void onDisable() {
        instance = null;
        translationCommon.onDisable();
        Commons.getLogger().info("Plugin has disabled successfully");
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerListener(), this);
    }

    private void unregisterCommands(String... commands) {
        try {
            Field f1 = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f1.setAccessible(true);
            CommandMap commandMap = (CommandMap) f1.get(Bukkit.getServer());
            Field f2 = commandMap.getClass().getDeclaredField("knownCommands");
            f2.setAccessible(true);
            HashMap<String, Command> knownCommands = (HashMap<String, Command>) f2.get(commandMap);
            for (String command : commands) {
                if (knownCommands.containsKey(command)) {
                    knownCommands.remove(command);
                    List<String> aliases = new ArrayList<>();
                    for (String key : knownCommands.keySet()) {
                        if (!key.contains(":")) continue;
                        String substr = key.substring(key.indexOf(":")+1);
                        if (substr.equalsIgnoreCase(command)) {
                            aliases.add(key);
                        }
                    }
                    for (String alias : aliases) {
                        knownCommands.remove(alias);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
