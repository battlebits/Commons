package br.com.battlebits.commons.backend.mongodb.pojo;

import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.account.Tag;
import br.com.battlebits.commons.server.ServerType;
import br.com.battlebits.commons.translate.Language;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class ModelAccount {
    // INFORMACOES DA CONTA

    private String name;
    private UUID uniqueId;

    // DADOS DA CONTA
    private int battleCoins;
    private int battleMoney;
    private int xp;

    // XP MULTIPLIER
    private int xpMultiplier;
    private long lastActivatedMultiplier;
    private long lastVIPMultiplierReceived;

    private Tag tag;

    // ADDRESSES E NETWORKING
    private transient String ipAddress;
    private String lastIpAddress;

    // PLAYING
    private long onlineTime;
    private long joinTime;
    private long lastLoggedIn;
    private long firstTimePlaying;

    // GRUPOS
    private Group group;

    // BLOCKED
    private Map<UUID, ModelBlocked> blockedPlayers;

    // DADOS DE LOCALIZACAO

    private String country;
    private String region;
    private String city;

    // CONFIGURACOES
    private ModelAccountConfiguration configuration;

    // PAIS E LINGUA
    private String countryCode;
    private Language language;

    // HISTORIA
    private ModelPunishmentHistory punishmentHistory;

    private String serverConnected;
    private ServerType serverConnectedType;

}
