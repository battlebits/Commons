package br.com.battlebits.commons.bukkit.api.item;

import br.com.battlebits.commons.Commons;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class ActionItemStack {

    private static final BiMap<Integer, InteractHandler> handlers =  HashBiMap.create();

    private static int HANDLER_ID = 0;

	public static void setInteractHandler(ItemStack stack, InteractHandler handler) {
        if (!handlers.containsValue(handler)) {
            Commons.getLogger().warning("Handler not registered: " + handler);
            return;
        }
        int id = handlers.inverse().get(handler);
		try {
			if (stack == null || stack.getType() == Material.AIR)
				throw new Exception();
			Constructor<?> caller = MinecraftReflection.getCraftItemStackClass()
					.getDeclaredConstructor(ItemStack.class);
			caller.setAccessible(true);
			ItemStack item = (ItemStack) caller.newInstance(stack);
			NbtCompound compound = (NbtCompound) NbtFactory.fromItemTag(item);
			compound.put("interactHandler", id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static int register(InteractHandler handler) {
        if (handlers.containsValue(handler)) {
            return handlers.inverse().get(handler);
        }
        handlers.put(++HANDLER_ID, handler);
        return HANDLER_ID - 1;
    }

    public static void unregister(int id) {
        handlers.remove(id);
    }

    static InteractHandler getHandler(int id) {
        return handlers.get(id);
    }

    public static void unregister(InteractHandler handler) {
        handlers.inverse().remove(handler);
    }

	public interface InteractHandler {

		boolean onInteract(Player player, Player target, ItemStack item, ItemAction action);
	}

}


