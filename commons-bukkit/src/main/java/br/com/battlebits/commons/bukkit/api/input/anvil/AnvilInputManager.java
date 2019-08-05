package br.com.battlebits.commons.bukkit.api.input.anvil;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

import br.com.battlebits.commons.bukkit.BukkitMain;

public class AnvilInputManager {

	private HashMap<UUID, AnvilInputGui> uuidAnvils;
	private AnvilInputListener anvilInputListener;

	public AnvilInputManager() {
		uuidAnvils = new HashMap<>();
		anvilInputListener = new AnvilInputListener(this);
		Bukkit.getPluginManager().registerEvents(anvilInputListener, BukkitMain.getInstance());
	}

	public void stop() {
		for (UUID id : uuidAnvils.keySet()) {
			Bukkit.getPlayer(id).closeInventory();
		}
		uuidAnvils.clear();
		HandlerList.unregisterAll(anvilInputListener);
		uuidAnvils = null;
		anvilInputListener = null;
	}

	public boolean isAnvilSearchInventory(UUID id, Inventory inv) {
		if (uuidAnvils.containsKey(id) && uuidAnvils.get(id).getAnvilInventory().equals(inv)) {
			return true;
		} else {
			return false;
		}
	}

	public AnvilInputGui openAnvilSearchGui(AnvilInputGui gui) {
		if (uuidAnvils.containsKey(gui.getPlayerUUID())) {
			uuidAnvils.remove(gui.getPlayerUUID());
		}
		uuidAnvils.put(gui.getPlayerUUID(), gui);
		return gui;
	}

	public void handleClick(Player p, String name) {
		if (hasAnvilSearch(p.getUniqueId())) {
			if (!getAnvilSearch(p.getUniqueId()).wasHandled()) {
				getAnvilSearch(p.getUniqueId()).setHandled(true);
				getAnvilSearch(p.getUniqueId()).getInputHandler().onDone(p, name);
			}
		}
	}

	public void handleClose(Player p) {
		if (hasAnvilSearch(p.getUniqueId())) {
			if (!getAnvilSearch(p.getUniqueId()).wasHandled()) {
				getAnvilSearch(p.getUniqueId()).setHandled(true);
				if (p != null && Bukkit.getPlayer(p.getUniqueId()) != null && p.isOnline()) {
					getAnvilSearch(p.getUniqueId()).getInputHandler().onClose(p);
				}
			}
		}
		destroyInventory(p.getUniqueId());
	}

	public AnvilInputGui getAnvilSearch(UUID id) {
		return uuidAnvils.get(id);
	}

	public boolean hasAnvilSearch(UUID id) {
		return uuidAnvils.containsKey(id);
	}

	public void tryToRemoveFromMap(UUID id) {
		uuidAnvils.remove(id);
	}

	public void destroyInventory(UUID id) {
		if (uuidAnvils.containsKey(id)) {
			uuidAnvils.get(id).destroyThisInventory();
			uuidAnvils.remove(id);
		}
	}

}
