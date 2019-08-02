package br.com.battlebits.commons.bukkit.services;

import br.com.battlebits.commons.bukkit.BukkitMain;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;

public class Services {

    private static BukkitMain plugin;

    static {
        plugin = BukkitMain.getInstance();
    }

    public static <T> T add(Class<T> clazz, T instance) {
        Validate.notNull(clazz, "clazz can't be null.");
        Validate.notNull(instance, "instance can't be null.");
        plugin.getServer().getServicesManager().register(clazz, instance, plugin, ServicePriority.Normal);
        return instance;
    }

    public static <T> T add(Class<T> clazz, T instance, ServicePriority priority) {
        Validate.notNull(clazz, "clazz can't be null.");
        Validate.notNull(instance, "instance can't be null.");
        Validate.notNull(priority, "priority can't be null.");
        plugin.getServer().getServicesManager().register(clazz, instance, plugin, priority);
        return instance;
    }

    public static <T> T get(Class<T> clazz) {
        Validate.notNull(clazz, "clazz can't be null.");
        RegisteredServiceProvider<T> registration = plugin.getServer().getServicesManager().getRegistration(clazz);
        if(registration != null) {
            return registration.getProvider();
        }
        return null;
    }
}
