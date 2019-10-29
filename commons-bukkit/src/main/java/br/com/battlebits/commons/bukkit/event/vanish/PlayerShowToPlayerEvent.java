package br.com.battlebits.commons.bukkit.event.vanish;

import br.com.battlebits.commons.bukkit.event.PlayerCancellableEvent;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class PlayerShowToPlayerEvent extends PlayerCancellableEvent {
	private Player toPlayer;

	public PlayerShowToPlayerEvent(Player player, Player toPlayer) {
		super(player);
		this.toPlayer = toPlayer;
	}
}
