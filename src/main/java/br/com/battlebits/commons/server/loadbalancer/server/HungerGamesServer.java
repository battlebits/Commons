package br.com.battlebits.commons.server.loadbalancer.server;

import java.util.Set;
import java.util.UUID;

import br.com.battlebits.commons.server.ServerType;

public class HungerGamesServer extends MinigameServer {

    public HungerGamesServer(String serverId, ServerType type, Set<UUID> onlinePlayers, boolean joinEnabled) {
        super(serverId, type, onlinePlayers, 100, joinEnabled);
        setState(MinigameState.WAITING);
    }

    @Override
    public boolean canBeSelected() {
        return super.canBeSelected() && !isInProgress() && ((getState() == MinigameState.PREGAME && getTime() >= 15) || getState() == MinigameState.WAITING);
    }

    @Override
    public boolean isInProgress() {
        return getState() == MinigameState.GAMETIME || getState() == MinigameState.INVINCIBILITY;
    }

}
