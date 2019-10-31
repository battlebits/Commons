package br.com.battlebits.commons.bukkit.api.item;

import br.com.battlebits.commons.bukkit.api.item.ActionItemStack.InteractHandler;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Constructor;

public class ActionItemListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getItem() == null)
			return;
		ItemStack stack = event.getItem();
		try {
			if (stack == null || stack.getType() == Material.AIR)
				throw new Exception();
			Constructor<?> caller = MinecraftReflection.getCraftItemStackClass()
					.getDeclaredConstructor(ItemStack.class);
			caller.setAccessible(true);
			ItemStack item = (ItemStack) caller.newInstance(stack);
			NbtCompound compound = (NbtCompound) NbtFactory.fromItemTag(item);
			if (!compound.containsKey("interactHandler")) {
				return;
			}
			InteractHandler handler = (InteractHandler) compound.getObject("interactHandler");
			if (handler == null) {
				throw new NullPointerException("NbtCompound with null interactHandler");
			}
			Player player = event.getPlayer();
			try {
				ItemAction action = ItemAction.valueOf(event.getAction().name());
				event.setCancelled(!handler.onInteract(player, null, stack, action));
			} catch (Exception ignored) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//
//	@EventHandler
//	public void onInteractEntity(PlayerInteractEntityEvent event) {
//		if (!(event.getRightClicked() instanceof Player))
//			return;
//		Player player = event.getPlayer();
//		PlayerInventory inv = player.getInventory();
//		ItemStack stack;
//		switch (event.getHand()) {
//			case HAND:
//				stack = inv.getItemInMainHand();
//				break;
//			case OFF_HAND:
//				stack = inv.getItemInOffHand();
//				break;
//			default:
//				stack = null;
//		}
//		if (stack == null || stack.getType() == Material.AIR)
//			return;
//		if(!(stack instanceof ActionItemStack))
//			return;
//		ActionItemStack item = (ActionItemStack) stack;
//		if(item.getInteractHandler() == null)
//			return;
//		event.setCancelled(!item.getInteractHandler().onInteract(player, (Player) event.getRightClicked(), stack, ItemAction.RIGHT_CLICK_PLAYER));
//	}
//
//	@EventHandler
//	public void onDamage(EntityDamageByEntityEvent event) {
//		if (!(event.getDamager() instanceof Player))
//			return;
//		if (!(event.getEntity() instanceof Player))
//			return;
//		Player player = (Player) event.getDamager();
//		PlayerInventory inv = player.getInventory();
//		ItemStack stack = inv.getItemInMainHand();
//		if (stack == null || stack.getType() == Material.AIR)
//			return;
//		if(!(stack instanceof ActionItemStack))
//			return;
//		ActionItemStack item = (ActionItemStack) stack;
//		if(item.getInteractHandler() == null)
//			return;
//		event.setCancelled(!item.getInteractHandler().onInteract(player, (Player) event.getEntity(), stack, ItemAction.LEFT_CLICK_PLAYER));
//	}

}
