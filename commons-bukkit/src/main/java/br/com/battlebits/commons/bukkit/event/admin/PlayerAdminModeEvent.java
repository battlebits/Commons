package br.com.battlebits.commons.bukkit.event.admin;

import br.com.battlebits.commons.bukkit.event.PlayerCancellableEvent;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@Getter
public class PlayerAdminModeEvent extends PlayerCancellableEvent {

	private AdminMode adminMode;
	@Setter
	private GameMode gameMode;

	public PlayerAdminModeEvent(Player player, AdminMode adminMode, GameMode mode) {
		super(player);
		this.adminMode = adminMode;
		this.gameMode = mode;
	}

	public static enum AdminMode {
		ADMIN, //
		PLAYER
	}

}
