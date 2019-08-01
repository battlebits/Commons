package br.com.battlebits.commons.account;

import br.com.battlebits.commons.CommonsConst;
import br.com.battlebits.commons.account.punishment.PunishmentHistory;
import br.com.battlebits.commons.server.ServerType;
import br.com.battlebits.commons.translate.Language;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public final class BattleAccount {

    // INFORMACOES DA CONTA
    private String name;
    private UUID uniqueId;

    // DADOS DA CONTA
    private int battleCoins = 0;
    private int battleMoney = 0;
    private int xp = 0;

    // XP MULTIPLIER
    private int xpMultiplier = 0;
    private long lastActivatedMultiplier = Long.MIN_VALUE;
    private long lastVIPMultiplierReceived = Long.MIN_VALUE;

    private Tag tag;

    // ADDRESSES E NETWORKING
    private transient String ipAddress = "";
    private String lastIpAddress = "";

    // PLAYING
    private long onlineTime = 0l;
    private long joinTime;
    private long lastLoggedIn;
    private long firstTimePlaying;

    // GRUPOS
    private Group group = Group.DEFAULT;

    // BLOCKED
    private Map<UUID, Blocked> blockedPlayers = new HashMap<>();

    // DADOS DE LOCALIZACAO

    private String country = "";
    private String region = "";
    private String city = "";

    // CONFIGURACOES
    private AccountConfiguration configuration = new AccountConfiguration(this);

    // PAIS E LINGUA
    private String countryCode = "";
    private Language language = CommonsConst.DEFAULT_LANGUAGE;

    // HISTORIA
    private PunishmentHistory punishmentHistory = new PunishmentHistory();

    private transient boolean online;

    private String serverConnected = "";
    private ServerType serverConnectedType = ServerType.NONE;

    @Getter(AccessLevel.NONE)
    private transient String lastServer = "";
}
