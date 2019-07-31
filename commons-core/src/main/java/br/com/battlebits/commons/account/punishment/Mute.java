package br.com.battlebits.commons.account.punishment;

import java.util.UUID;

import br.com.battlebits.commons.core.account.BattlePlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Mute {

	private String mutedBy;
	private UUID mutedByUniqueId;
	private String mutedIp;

	private String server;

	private long muteTime;
	private String reason;

	private boolean unmuted;
	private String unmutedBy;
	private UUID unmutedByUniqueId;
	private long unmuteTime;

	private long expire;
	private long duration;

	public Mute(String mutedBy, String mutedIp, String server, String reason, long duration) {
		this(mutedBy, null, mutedIp, server, reason, duration);
	}

	public Mute(String mutedBy, UUID mutedByUuid, String mutedIp, String server, String reason, long expire) {
		this(mutedBy, mutedByUuid, mutedIp, server, System.currentTimeMillis(), reason, false, null, null, -1, expire, expire - System.currentTimeMillis());
	}

	public Mute(String mutedBy, String mutedIp, String server, String reason) {
		this(mutedBy, null, mutedIp, server, reason);
	}

	public Mute(String mutedBy, UUID mutedByUuid, String mutedIp, String server, String reason) {
		this(mutedBy, mutedByUuid, mutedIp, server, System.currentTimeMillis(), reason, false, null, null, -1, -1, -1);
	}
	
	public boolean hasExpired() {
		return expire != -1 && expire < System.currentTimeMillis();
	}

	public boolean isPermanent() {
		return expire == -1;
	}

	public void unmute() {
		this.unmuted = true;
		this.unmutedBy = "CONSOLE";
		this.unmuteTime = System.currentTimeMillis();
	}

	public void unmute(BattlePlayer unmutePlayer) {
		unmute(unmutePlayer.getUniqueId(), unmutePlayer.getName());
	}

	public void unmute(UUID unmuteUuid, String unmuteName) {
		this.unmuted = true;
		this.unmutedBy = unmuteName;
		this.unmutedByUniqueId = unmuteUuid;
		this.unmuteTime = System.currentTimeMillis();
	}
	
	
}
