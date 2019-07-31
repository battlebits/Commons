package br.com.battlebits.commons.server.loadbalancer.server;

import java.util.Set;
import java.util.UUID;

import br.com.battlebits.commons.server.ServerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class MinigameServer extends BattleServer {

    private int time;
    private String map;
    private MinigameState state;

    public MinigameServer(String serverId, ServerType type, Set<UUID> onlinePlayers, int maxPlayers, boolean joinEnabled) {
        super(serverId, type, onlinePlayers, 100, joinEnabled);
        this.state = MinigameState.WAITING;
    }

    @Override
    public int getActualNumber() {
        return super.getActualNumber();
    }

    public abstract boolean isInProgress();

}
