package br.com.battlebits.commons.server;

public enum ServerType {

    DOUBLEKITHG, //
    FAIRPLAY, //
    CUSTOMHG, //
    HUNGERGAMES, //
    PVP_FULLIRON, //
    PVP_SIMULATOR, //
    SWNS, //
    SWNT, //
    SWIS, //
    SWIT, //
    SKYWARS_LOBBY, //
    HG_LOBBY, //
    LOBBY, //
    RAID, //
    GARTICCRAFT, //
    TESTSERVER, //
    NETWORK, //
    NONE;

    public boolean isLobby() {
        return this == LOBBY || this == SKYWARS_LOBBY;
    }

    public ServerType getServerLobby() {
        switch (this) {
            case SWIS:
            case SWIT:
            case SWNT:
            case SWNS:
            case SKYWARS_LOBBY:
                return SKYWARS_LOBBY;
            default:
                return LOBBY;
        }
    }

}
