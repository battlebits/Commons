package br.com.battlebits.commons.bukkit.event.teleport;

import br.com.battlebits.commons.bukkit.event.PlayerCancellableEvent;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

public class PlayerTeleportCommandEvent extends PlayerCancellableEvent {

	@Getter
	@Setter
	private TeleportResult result;

	public PlayerTeleportCommandEvent(Player player, TeleportResult result) {
		super(player);
		this.result = result;
	}

	public static enum TeleportResult {
		NO_PERMISSION, ONLY_PLAYER_TELEPORT, ALLOWED;
	}

}
