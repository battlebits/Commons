package br.com.battlebits.commons.bukkit;

import br.com.battlebits.commons.CommonPlatform;
import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.backend.DataAccount;
import br.com.battlebits.commons.backend.DataServer;
import br.com.battlebits.commons.backend.DataTeam;
import br.com.battlebits.commons.backend.Database;
import br.com.battlebits.commons.backend.logging.DataLog;
import br.com.battlebits.commons.backend.logging.DataLogType;
import br.com.battlebits.commons.backend.mongodb.MongoDatabase;
import br.com.battlebits.commons.backend.mongodb.MongoStorageDataAccount;
import br.com.battlebits.commons.backend.nullable.VoidDataLog;
import br.com.battlebits.commons.backend.nullable.VoidDataServer;
import br.com.battlebits.commons.backend.nullable.VoidDataTeam;
import br.com.battlebits.commons.backend.properties.PropertiesStorageDataTranslation;
import br.com.battlebits.commons.bukkit.api.cooldown.CooldownAPI;
import br.com.battlebits.commons.bukkit.api.item.ActionItemListener;
import br.com.battlebits.commons.bukkit.api.item.glow.Glow;
import br.com.battlebits.commons.bukkit.api.menu.MenuListener;
import br.com.battlebits.commons.bukkit.command.BukkitCommandFramework;
import br.com.battlebits.commons.bukkit.generator.VoidGenerator;
import br.com.battlebits.commons.bukkit.listener.*;
import br.com.battlebits.commons.bukkit.manager.PunishManager;
import br.com.battlebits.commons.bukkit.scheduler.UpdateScheduler;
import br.com.battlebits.commons.bukkit.scoreboard.tagmanager.TagListener;
import br.com.battlebits.commons.bukkit.scoreboard.tagmanager.TagManager;
import br.com.battlebits.commons.bukkit.translate.BukkitTranslationCommon;
import br.com.battlebits.commons.command.CommandLoader;
import br.com.battlebits.commons.server.ServerType;
import br.com.battlebits.commons.translate.TranslateTag;
import br.com.battlebits.commons.translate.TranslationCommon;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class BukkitMain extends JavaPlugin {

    public static final int GLOW_ID = 1000;

    @Getter
    private static BukkitMain instance;
    @Getter
    private ProtocolManager protocolManager;


    @Setter
    private boolean antiAfkEnabled = true;
    @Setter
    private boolean tagControlEnabled = true;
    private Database database;

    private TranslationCommon translationCommon;

    private TagManager tagManager;
    private PunishManager punishManager;

    @Override
    public void onLoad() {
        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();

    }

    @Override
    public void onEnable() {
        try {
            String serverId = "NONE"; // TODO getServerId
            ServerType type = ServerType.DEFAULT; // TODO getServerType
            // TODO Check for config file and initialize Commons
            database = new MongoDatabase("localhost", "test", "test", "test", 27017);
            database.connect();

            DataServer dataServer = new VoidDataServer();
            DataAccount dataAccount = new MongoStorageDataAccount((MongoDatabase) database);
            DataTeam dataTeam = new VoidDataTeam();
            DataLog dataLog = new VoidDataLog();
            CommonPlatform platform = new BukkitPlatform();

            Commons.initialize(getLogger(), serverId, type, dataAccount, dataTeam, dataServer, dataLog, platform);
        } catch (Exception e) {
            e.printStackTrace();
        }

        translationCommon = new BukkitTranslationCommon(new PropertiesStorageDataTranslation(TranslateTag.class));
        tagManager = new TagManager(this);
        tagManager.onEnable();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        try {
            new CommandLoader(new BukkitCommandFramework(this)).loadCommandsFromPackage(getFile(), "br.com.battlebits.commons.bukkit.command.registry");
        } catch (Exception e) {
            getLogger().severe("An internal error happened when trying to register commands!");
            e.printStackTrace();
        }

        registerListeners();
        getServer().getScheduler().runTaskTimer(this, new UpdateScheduler(), 1, 1);
        // getServer().getScheduler().runTaskLater(this, () -> unregisterCommands("pl", "plugins", "icanhasbukkit", "ver", "version", "?", "help", "me"), 2L);
        Commons.getDataServer().startServer(Bukkit.getMaxPlayers());
        Commons.getDataLog().log(DataLogType.SERVER_START);

        // API
        registerGlow();

        Commons.getLogger().info("Plugin has enabled successfully");
    }

    @Override
    public void onDisable() {
        instance = null;
        try {
            database.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Commons.getDataServer().stopServer();
        Commons.getDataLog().log(DataLogType.SERVER_STOP);
        translationCommon.onDisable();
        tagManager.onDisable();
        Commons.getLogger().info("Plugin has disabled successfully");
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        if (isAntiAfkEnabled())
            pluginManager.registerEvents(new AntiAfkListener(), this);
        pluginManager.registerEvents(new PlayerListener(), this);
        pluginManager.registerEvents(new AccountListener(), this);
        pluginManager.registerEvents(new ScoreboardListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new TagListener(tagManager), this);

        // APIs
        pluginManager.registerEvents(new ActionItemListener(), this);
        pluginManager.registerEvents(new CooldownAPI(), this);
        pluginManager.registerEvents(new MenuListener(), this);

    }

    @SuppressWarnings("unchecked")
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
                        String substr = key.substring(key.indexOf(":") + 1);
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

    private void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glow glow = new Glow(GLOW_ID);
            Enchantment.registerEnchantment(glow);
        } catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new VoidGenerator();
    }

}
