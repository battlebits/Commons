package br.com.battlebits.commons.server;

public enum ServerType {

    PVP, //
    LOBBY, //
    NETWORK, //
    DEFAULT;

    public boolean isLobby() {
        return this == LOBBY;
    }


}
