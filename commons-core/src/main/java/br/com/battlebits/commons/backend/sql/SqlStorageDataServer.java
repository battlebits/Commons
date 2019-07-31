package br.com.battlebits.commons.backend.sql;

import br.com.battlebits.commons.backend.DataServer;
import br.com.battlebits.commons.server.ServerType;
import br.com.battlebits.commons.server.loadbalancer.server.MinigameState;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class SqlStorageDataServer implements DataServer {
    @Override
    public String getServerId(String ipAddress) {
        return null;
    }

    @Override
    public ServerType getServerType(String ipAddress) {
        return null;
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
        return null;
    }

    @Override
    public Map<String, Map<String, String>> getServers() {
        return null;
    }
}
