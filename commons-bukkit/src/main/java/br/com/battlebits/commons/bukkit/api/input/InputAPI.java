package br.com.battlebits.commons.bukkit.api.input;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.battlebits.commons.bukkit.api.input.anvil.AnvilInputGui;
import br.com.battlebits.commons.bukkit.api.input.anvil.AnvilInputManager;

public class InputAPI {
	private static AnvilInputManager anvilInputManager = new AnvilInputManager();
	
	public static void openAnvilGui(Player player, String string, ItemStack itemStack, InputHandler handler) {
		player.closeInventory();
		anvilInputManager.openAnvilSearchGui(new AnvilInputGui(player, itemStack, handler));
	}
}
