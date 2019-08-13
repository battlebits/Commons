package br.com.battlebits.commons;

import br.com.battlebits.commons.account.AccountCommon;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.backend.DataAccount;
import br.com.battlebits.commons.backend.DataClan;
import br.com.battlebits.commons.backend.DataServer;
import br.com.battlebits.commons.backend.DataTeam;
import br.com.battlebits.commons.backend.logging.DataLog;
import br.com.battlebits.commons.clan.ClanCommon;
import br.com.battlebits.commons.party.PartyCommon;
import br.com.battlebits.commons.server.ServerType;
import br.com.battlebits.commons.team.TeamCommon;
import br.com.battlebits.commons.translate.Language;
import br.com.battlebits.commons.util.mojang.UUIDFetcher;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class Commons {

    @Getter
    private static AccountCommon accountCommon = new AccountCommon();

    @Getter
    private static TeamCommon teamCommon = new TeamCommon();

    @Getter
    private static PartyCommon partyCommon = new PartyCommon();

    @Getter
    private static ClanCommon clanCommon = new ClanCommon();

    @Getter
    private static String serverId;

    @Getter
    private static ServerType serverType;

    @Getter
    private static DataAccount dataAccount;

    @Getter
    private static DataTeam dataTeam;

    @Getter
    private static DataServer dataServer;

    @Getter
    private static DataClan dataClan;

    @Getter
    private static DataLog dataLog;

    @Getter
    private static CommonPlatform platform;

    @Getter
    private static Logger logger;

    private static boolean initialized = false;

    public static void initialize(Logger _logger, //
                                  String _serverId, //
                                  ServerType _serverType, //
                                  DataAccount _dataAccount, //
                                  DataTeam _dataTeam, //
                                  DataServer _dataServer, //
                                  DataClan _dataClan, //
                                  DataLog _dataLog,
                                  CommonPlatform commonPlatform) throws Exception {
        if (initialized)
            throw new Exception("Commons already have been initialized");
        logger = _logger;
        serverId = _serverId;
        serverType = _serverType;
        dataAccount = _dataAccount;
        dataTeam = _dataTeam;
        dataServer = _dataServer;
        dataClan = _dataClan;
        dataLog = _dataLog;
        platform = commonPlatform;
        initialized = true;
    }

    public static UUID getUuidOf(String playerName) {
        Objects.requireNonNull(platform);
        UUID uuid = platform.getUUID(playerName);
        if (uuid == null) {
            uuid = UUIDFetcher.getUUID(playerName);
        }
        return uuid;
    }

    public static BattleAccount getAccount(UUID uuid) {
        return accountCommon.getBattleAccount(uuid);
    }

    public static Language getLanguage(UUID uuid) {
        BattleAccount account = getAccount(uuid);
        if (account != null)
            return account.getLanguage();
        return CommonsConst.DEFAULT_LANGUAGE;
    }
}