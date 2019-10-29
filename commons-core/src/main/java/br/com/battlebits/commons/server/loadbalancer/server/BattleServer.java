package br.com.battlebits.commons.server.loadbalancer.server;

import br.com.battlebits.commons.server.ServerType;
import br.com.battlebits.commons.server.loadbalancer.element.LoadBalancerObject;
import br.com.battlebits.commons.server.loadbalancer.element.NumberConnection;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

public class BattleServer implements LoadBalancerObject, NumberConnection {

	private String serverId;
	@Getter
	private ServerType serverType;
	private Set<UUID> players;
	private int maxPlayers;

	private boolean joinEnabled;

	public BattleServer(String serverId, ServerType serverType, Set<UUID> onlinePlayers, int maxPlayers, boolean joinEnabled) {
		this.serverId = serverId.toUpperCase();
		this.serverType = serverType;
		this.players = onlinePlayers;
		this.maxPlayers = maxPlayers;
		this.joinEnabled = joinEnabled;
	}

	public void setOnlinePlayers(Set<UUID> onlinePlayers) {
		this.players = onlinePlayers;
	}

	public void joinPlayer(UUID uuid) {
		players.add(uuid);
	}

	public void leavePlayer(UUID uuid) {
		players.remove(uuid);
	}

	public String getServerId() {
		return serverId;
	}

	public int getOnlinePlayers() {
		return players.size();
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public boolean isFull() {
		return players.size() >= maxPlayers;
	}

	public void setJoinEnabled(boolean joinEnabled) {
		this.joinEnabled = joinEnabled;
	}

	public boolean isJoinEnabled() {
		return joinEnabled;
	}

	@Override
	public boolean canBeSelected() {
		return isJoinEnabled() && !isFull();
	}

	@Override
	public int getActualNumber() {
		return getOnlinePlayers();
	}

	@Override
	public int getMaxNumber() {
		return getMaxPlayers();
	}
}
