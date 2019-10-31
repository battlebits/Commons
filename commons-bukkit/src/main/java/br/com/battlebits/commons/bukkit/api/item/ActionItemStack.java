package br.com.battlebits.commons.bukkit.api.item;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class ActionItemStack {

	public static ItemStack setInteractHandler(ItemStack stack, InteractHandler handler) {
		try {
			if (stack == null || stack.getType() == Material.AIR)
				throw new Exception();
			Constructor<?> caller = MinecraftReflection.getCraftItemStackClass()
					.getDeclaredConstructor(ItemStack.class);
			caller.setAccessible(true);
			ItemStack item = (ItemStack) caller.newInstance(stack);
			NbtCompound compound = (NbtCompound) NbtFactory.fromItemTag(item);
			compound.putObject("interactHandler", handler);
			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public interface InteractHandler {

		boolean onInteract(Player player, Player target, ItemStack item, ItemAction action);
	}

}


