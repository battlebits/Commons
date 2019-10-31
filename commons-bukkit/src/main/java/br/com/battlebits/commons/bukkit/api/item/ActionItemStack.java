package br.com.battlebits.commons.bukkit.api.item;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class ActionItemStack extends ItemStack {

	@Getter
	@Setter
	private InteractHandler interactHandler;

	public ActionItemStack(Material mat) {
		super(mat);
	}

	public ActionItemStack(ItemStack stack, InteractHandler handler) {
		setType(stack.getType());
		setAmount(stack.getAmount());
		setData(stack.getData());
		setDurability(stack.getDurability());
		setItemMeta(stack.getItemMeta());
		this.interactHandler = handler;
	}

	public interface InteractHandler {

		boolean onInteract(Player player, Player target, ItemStack item, Action action);
	}
}
