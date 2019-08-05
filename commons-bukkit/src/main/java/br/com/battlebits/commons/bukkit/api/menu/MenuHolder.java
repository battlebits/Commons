package br.com.battlebits.commons.bukkit.api.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

class MenuHolder implements InventoryHolder {

	private MenuInventory menu;

	public MenuHolder(MenuInventory menuInventory) {
		this.menu = menuInventory;
	}

	public MenuInventory getMenu() {
		return menu;
	}

	public void setMenu(MenuInventory menu) {
		this.menu = menu;
	}

	public boolean isOnePerPlayer() {
		return menu.isOnePerPlayer();
	}

	public void destroy() {
		menu = null;
	}

	@Override
	public Inventory getInventory() {
		if (isOnePerPlayer()) {
			return null;
		} else {
			return menu.getInventory();
		}
	}
}
