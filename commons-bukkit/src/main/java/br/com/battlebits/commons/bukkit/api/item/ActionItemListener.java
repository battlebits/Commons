package br.com.battlebits.commons.bukkit.api.item;

import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.api.item.ActionItemStack.InteractHandler;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.lang.reflect.Constructor;

public class ActionItemListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getItem() == null)
			return;
		ItemStack stack = event.getItem();
		if (stack == null || stack.getType() == Material.AIR)
			return;
		if(!(stack instanceof ActionItemStack))
			return;
		ActionItemStack item = (ActionItemStack) stack;
		if(item.getInteractHandler() == null)
			return;
		Player player = event.getPlayer();
		Action action = event.getAction();
		event.setCancelled(!item.getInteractHandler().onInteract(player, null, stack, action));
	}

}
