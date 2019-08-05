package br.com.battlebits.commons.bukkit.api.input.anvil;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;

import net.md_5.bungee.api.ChatColor;

class AnvilInputListener implements Listener {

	private AnvilInputManager inputManager;

	public AnvilInputListener(AnvilInputManager manager) {
		this.inputManager = manager;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryClickListener(InventoryClickEvent e) {
		if (e.getInventory().getType() == InventoryType.ANVIL) {
			if (e.getWhoClicked() instanceof Player) {
				if (inputManager.isAnvilSearchInventory(((Player) e.getWhoClicked()).getUniqueId(), e.getInventory())) {
					if (e.getSlot() == 2) {
						if (e.getCurrentItem() != null) {
							if (e.getCurrentItem().getType() != Material.AIR) {
								if (e.getCurrentItem().hasItemMeta()) {
									if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
										if (ChatColor.stripColor(
												e.getCurrentItem().getItemMeta().getDisplayName().replace("&", "§"))
												.length() >= 1) {
											inputManager.handleClick(((Player) e.getWhoClicked()),
													ChatColor.stripColor(e.getCurrentItem().getItemMeta()
															.getDisplayName().replace("&", "§")));
											e.setCancelled(true);
											return;
										}
									}
								}
								inputManager.handleClick(((Player) e.getWhoClicked()), "");
							}
						}
					}
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryCloseListener(InventoryCloseEvent e) {
		if (e.getInventory().getType() == InventoryType.ANVIL) {
			if (e.getPlayer() instanceof Player) {
				Player p = (Player) e.getPlayer();
				if (inputManager.isAnvilSearchInventory(p.getUniqueId(), e.getInventory())) {
					e.getInventory().clear();
					inputManager.handleClose((Player) e.getPlayer());
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerQuitListener(PlayerQuitEvent e) {
		inputManager.tryToRemoveFromMap(e.getPlayer().getUniqueId());
	}

}
