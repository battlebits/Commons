package br.com.battlebits.commons.backend.nullable;

import br.com.battlebits.commons.backend.DataServer;
import br.com.battlebits.commons.server.ServerType;
import br.com.battlebits.commons.server.loadbalancer.server.MinigameState;

import java.util.*;

public class VoidDataServer implements DataServer {
    @Override
    public String getServerId(String ipAddress) {
        return "";
    }

    @Override
    public ServerType getServerType(String ipAddress) {
        return ServerType.DEFAULT;
    }

    @Override
    public void startServer(int maxPlayers) {

    }

    @Override
    public void stopServer() {

    }

    @Override
    public void setJoinEnabled(boolean bol) {

    }

    @Override
    public void updateStatus(MinigameState state, int time) {

    }

    @Override
    public void updateStatus(MinigameState state, String map, int time) {

    }

    @Override
    public void joinPlayer(UUID uuid) {

    }

    @Override
    public void leavePlayer(UUID uuid) {

    }

    @Override
    public int getPlayerCount(String serverId) {
        return 0;
    }

    @Override
    public int getPlayerCount(ServerType serverType) {
        return 0;
    }

    @Override
    public Set<UUID> getPlayers(String serverId) {
        return new HashSet<>();
    }

    @Override
    public Map<String, Map<String, String>> getServers() {
        return new HashMap<>();
    }
}
