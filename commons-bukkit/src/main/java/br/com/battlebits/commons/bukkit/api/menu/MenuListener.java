package br.com.battlebits.commons.bukkit.api.menu;

import br.com.battlebits.commons.bukkit.event.update.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClickListener(InventoryClickEvent event) {
		if (event.getInventory() == null)
			return;
		Inventory inv = event.getInventory();
		if (inv.getType() != InventoryType.CHEST)
			return;
		if (inv.getHolder() == null)
			return;
		if (!(inv.getHolder() instanceof MenuHolder))
			return;
		event.setCancelled(true);
		if (event.getClickedInventory() != inv)
			return;
		if (!(event.getWhoClicked() instanceof Player))
			return;
		if (event.getSlot() < 0)
			return;
		MenuHolder holder = (MenuHolder) inv.getHolder();
		MenuInventory menu = holder.getMenu();
		if (menu.hasItem(event.getSlot())) {
			Player p = (Player) event.getWhoClicked();
			MenuItem item = menu.getItem(event.getSlot());
			item.getHandler().onClick(p, inv,
					((event.getAction() == InventoryAction.PICKUP_HALF) ? ClickType.RIGHT : ClickType.LEFT),
					event.getCurrentItem(), event.getSlot());
		}
	}

	//@EventHandler
	public void onClose(InventoryCloseEvent event) {
		if (event.getInventory() == null)
			return;
		Inventory inv = event.getInventory();
		if (inv.getType() != InventoryType.CHEST)
			return;
		if (inv.getHolder() == null)
			return;
		if (!(inv.getHolder() instanceof MenuHolder))
			return;
		if (!(event.getPlayer() instanceof Player))
			return;
		MenuHolder holder = (MenuHolder) inv.getHolder();
		holder.destroy();
	}


	@EventHandler(priority = EventPriority.MONITOR)
    public void onUpdate(UpdateEvent event) {
	    if (event.getType() != UpdateEvent.UpdateType.SECOND)
	        return;
        Bukkit.getOnlinePlayers().forEach(p -> {
            InventoryHolder holder = p.getOpenInventory().getTopInventory().getHolder();
            if (holder != null && holder instanceof MenuHolder) {
                MenuInventory menu = ((MenuHolder) holder).getMenu();
                if (menu.getUpdateHandler() != null)
                    menu.getUpdateHandler().onUpdate(p, menu);
            }
        });
    }
}
