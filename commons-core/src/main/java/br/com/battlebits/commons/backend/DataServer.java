package br.com.battlebits.commons.backend;

import br.com.battlebits.commons.server.ServerType;
import br.com.battlebits.commons.server.loadbalancer.server.MinigameState;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface DataServer {

    String getServerId(String ipAddress);

    ServerType getServerType(String ipAddress);

    void startServer(int maxPlayers);

    void stopServer();

    void setJoinEnabled(boolean bol);

    void updateStatus(MinigameState state, int time);

    void updateStatus(MinigameState state, String map, int time);

    void joinPlayer(UUID uuid);

    void leavePlayer(UUID uuid);

    int getPlayerCount(String serverId);

    int getPlayerCount(ServerType serverType);

    Set<UUID> getPlayers(String serverId);

    Map<String, Map<String, String>> getServers();
}
