package br.com.battlebits.commons.backend;

import br.com.battlebits.commons.server.ServerType;
import br.com.battlebits.commons.server.loadbalancer.server.MinigameState;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface DataServer {

    public String getServerId(String ipAddress);

    public ServerType getServerType(String ipAddress);

    public void startServer(int maxPlayers);

    public void stopServer();

    public void setJoinEnabled(boolean bol);

    public void updateStatus(MinigameState state, int time);

    public void updateStatus(MinigameState state, String map, int time);

    public void joinPlayer(UUID uuid);

    public void leavePlayer(UUID uuid);

    public int getPlayerCount(String serverId);

    public int getPlayerCount(ServerType serverType);

    public Set<UUID> getPlayers(String serverId);

    public Map<String, Map<String, String>> getServers();
}
