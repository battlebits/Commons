package br.com.battlebits.commons.server;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.server.loadbalancer.BaseBalancer;
import br.com.battlebits.commons.server.loadbalancer.server.*;
import br.com.battlebits.commons.server.loadbalancer.type.LeastConnection;
import br.com.battlebits.commons.server.loadbalancer.type.MostConnection;

import java.util.*;

public class ServerManager {

    private Map<String, BattleServer> activeServers;

    private HashMap<ServerType, BaseBalancer<BattleServer>> balancers;

    public ServerManager() {
        balancers = new HashMap<>();

        balancers.put(ServerType.LOBBY, new LeastConnection<>());
        balancers.put(ServerType.PVP_FULLIRON, new MostConnection<>());
        balancers.put(ServerType.PVP_SIMULATOR, new MostConnection<>());

        balancers.put(ServerType.CUSTOMHG, new MostConnection<>());
        balancers.put(ServerType.DOUBLEKITHG, new MostConnection<>());
        balancers.put(ServerType.HUNGERGAMES, new MostConnection<>());

        balancers.put(ServerType.SWNS, new MostConnection<>());
        balancers.put(ServerType.SWNT, new MostConnection<>());
        balancers.put(ServerType.SWIS, new MostConnection<>());
        balancers.put(ServerType.SWIT, new MostConnection<>());

        activeServers = new HashMap<>();
    }

    public BaseBalancer<BattleServer> getBalancer(ServerType type) {
        return balancers.get(type);
    }

    public void putBalancer(ServerType type, BaseBalancer<BattleServer> balancer) {
        balancers.put(type, balancer);
    }

    public void addActiveServer(String serverAddress, String serverIp, ServerType type, int maxPlayers) {
        Commons.getLogger().info("Battlebits Server carregado. ServerId: " + serverIp);
        updateActiveServer(serverIp, type, new HashSet<>(), maxPlayers, true);
    }

    public void updateActiveServer(String serverId, ServerType type, Set<UUID> onlinePlayers, int maxPlayers, boolean canJoin) {
        updateActiveServer(serverId, type, onlinePlayers, maxPlayers, canJoin, 0, "Unknown", null);
    }

    public void updateActiveServer(String serverId, ServerType type, Set<UUID> onlinePlayers, int maxPlayers, boolean canJoin, int tempo, String map, MinigameState state) {
        BattleServer server = activeServers.get(serverId);
        if (server == null) {
            if (type == ServerType.HUNGERGAMES || type == ServerType.DOUBLEKITHG || type == ServerType.FAIRPLAY || type == ServerType.CUSTOMHG) {
                server = new HungerGamesServer(serverId, type, onlinePlayers, true);
            } else if (type == ServerType.SWIS || type == ServerType.SWIT || type == ServerType.SWNS || type == ServerType.SWNT) {
                server = new SkywarsServer(serverId, type, onlinePlayers, true);
            } else {
                server = new BattleServer(serverId, type, onlinePlayers, maxPlayers, true);
            }
            activeServers.put(serverId.toLowerCase(), server);
        }
        server.setOnlinePlayers(onlinePlayers);
        server.setJoinEnabled(canJoin);
        if (state != null && server instanceof MinigameServer) {
            ((MinigameServer) server).setState(state);
            ((MinigameServer) server).setTime(tempo);
            ((MinigameServer) server).setMap(map);
        }
        addToBalancers(serverId, server);
    }

    public BattleServer getServer(String str) {
        return activeServers.get(str.toLowerCase());
    }

    public void removeActiveServer(String str) {
        if (getServer(str) != null)
            removeFromBalancers(getServer(str));
        activeServers.remove(str.toLowerCase());
    }

    public void addToBalancers(String serverId, BattleServer server) {
        BaseBalancer<BattleServer> balancer = getBalancer(server.getServerType());
        if (balancer == null)
            return;
        balancer.add(serverId.toLowerCase(), server);


        /*if (server.getServerType() == ServerType.HUNGERGAMES || server.getServerType() == ServerType.DOUBLEKITHG || server.getServerType() == ServerType.FAIRPLAY || server.getServerType() == ServerType.CUSTOMHG) {
            balancer.add(serverId.toLowerCase(), (HungerGamesServer) server);
        } else if (server.getServerType() == ServerType.SWIS || server.getServerType() == ServerType.SWIT || server.getServerType() == ServerType.SWNS || server.getServerType() == ServerType.SWNT) {
            balancer.add(serverId.toLowerCase(), (SkywarsServer) server);
        } else {
        }*/
    }

    public void removeFromBalancers(BattleServer serverId) {
        BaseBalancer<BattleServer> balancer = getBalancer(serverId.getServerType());
        if (balancer != null)
            balancer.remove(serverId.getServerId().toLowerCase());
    }

}
