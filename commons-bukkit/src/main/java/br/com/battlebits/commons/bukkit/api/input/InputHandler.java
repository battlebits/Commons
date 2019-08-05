package br.com.battlebits.commons.bukkit.api.input;

import org.bukkit.entity.Player;

public interface InputHandler {

	public void onDone(Player p, String name);

	public void onClose(Player p);

}
