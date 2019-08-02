package br.com.battlebits.commons;

import br.com.battlebits.commons.account.AccountCommon;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.backend.DataAccount;
import br.com.battlebits.commons.backend.DataServer;
import br.com.battlebits.commons.party.PartyCommon;
import br.com.battlebits.commons.server.ServerType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.util.logging.Logger;

public class Commons {

    @Getter
    private static AccountCommon accountCommon = new AccountCommon();

    @Getter
    private static PartyCommon partyCommon = new PartyCommon();

    @Getter
    @Setter
    private static String serverId;

    @Getter
    @Setter
    private static ServerType serverType;

    @Getter
    @Setter
    private static DataAccount dataAccount;

    @Getter
    @Setter
    private static DataServer dataServer;

    @Setter
    @Getter
    private static CommonPlatform platform;


    @Getter
    @Setter
    private static Logger logger;

    public static BattleAccount getAccount(UUID uuid) {
        return accountCommon.getBattlePlayer(uuid);
    }

}