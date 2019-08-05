package br.com.battlebits.commons.bukkit.api.player;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

import com.comphenix.protocol.utility.MinecraftReflection;

public class PingAPI {

	public static int getPing(Player player) {
		Object entityPlayer;
		try {
			entityPlayer = MinecraftReflection.getCraftPlayerClass().getMethod("getHandle").invoke(player);
			return (int) MinecraftReflection.getEntityPlayerClass().getField("ping").get(entityPlayer);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
