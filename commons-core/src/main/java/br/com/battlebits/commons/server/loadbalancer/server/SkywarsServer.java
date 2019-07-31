package br.com.battlebits.commons.server.loadbalancer.server;

import br.com.battlebits.commons.server.ServerType;

import java.util.Set;
import java.util.UUID;

public class SkywarsServer extends MinigameServer {

    public SkywarsServer(String serverId, ServerType type, Set<UUID> onlinePlayers, boolean joinEnabled) {
        super(serverId, type, onlinePlayers, 12, joinEnabled);
        setState(MinigameState.PREGAME);
    }

    @Override
    public boolean canBeSelected() {
        return super.canBeSelected() && (getState() == MinigameState.PREGAME && getTime() > 5);
    }

    @Override
    public boolean isInProgress() {
        return getState() != MinigameState.PREGAME;
    }

}
