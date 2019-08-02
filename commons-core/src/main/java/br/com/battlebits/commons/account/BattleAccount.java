package br.com.battlebits.commons.account;

import br.com.battlebits.commons.CommonsConst;
import br.com.battlebits.commons.account.punishment.PunishmentHistory;
import br.com.battlebits.commons.backend.mongodb.pojo.ModelAccount;
import br.com.battlebits.commons.backend.mongodb.pojo.ModelBlocked;
import br.com.battlebits.commons.command.CommandSender;
import br.com.battlebits.commons.server.ServerType;
import br.com.battlebits.commons.translate.Language;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public final class BattleAccount implements CommandSender {

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

    //private String country = "";
    //private String region = "";
    //private String city = "";

    // CONFIGURACOES
    private AccountConfiguration configuration = new AccountConfiguration(this);

    // PAIS E LINGUA
    //private String countryCode = "";
    private Language language = CommonsConst.DEFAULT_LANGUAGE;

    // HISTORIA
    private PunishmentHistory punishmentHistory = new PunishmentHistory();

    private transient boolean online;

    private String serverConnected = "";
    private ServerType serverConnectedType = ServerType.NONE;

    @Getter(AccessLevel.NONE)
    private transient String lastServer = "";

    public BattleAccount(ModelAccount account) {
        this.name = account.getName();
        this.uniqueId = account.getUniqueId();
        this.battleCoins = account.getBattleCoins();
        this.battleMoney = account.getBattleMoney();
        this.xp = account.getXp();
        this.xpMultiplier = account.getXpMultiplier();
        this.lastActivatedMultiplier = account.getLastActivatedMultiplier();
        this.lastVIPMultiplierReceived = account.getLastVIPMultiplierReceived();
        this.tag = account.getTag();
        this.lastIpAddress = account.getLastIpAddress();
        this.onlineTime = account.getOnlineTime();
        this.lastLoggedIn = account.getLastLoggedIn();
        this.firstTimePlaying = account.getFirstTimePlaying();
        this.group = account.getGroup();
        Map<UUID, Blocked> blockedPlayers = new HashMap<>();
        for (ModelBlocked b : account.getBlockedPlayers().values())
            blockedPlayers.put(b.getUniqueId(), new Blocked(b));
        this.blockedPlayers = blockedPlayers;
        this.configuration = new AccountConfiguration(this, account.getConfiguration());
        this.language = account.getLanguage();
        this.punishmentHistory = new PunishmentHistory(account.getPunishmentHistory());
        this.serverConnected = account.getServerConnected();
        this.serverConnectedType = account.getServerConnectedType();
    }

    public BattleAccount(UUID uniqueId, String name, String ipAddress) {
        this.name = name;
        this.uniqueId = uniqueId;

        this.ipAddress = ipAddress;
        if (ipAddress != null)
            this.lastIpAddress = ipAddress;

        this.lastLoggedIn = System.currentTimeMillis();
        this.firstTimePlaying = System.currentTimeMillis();
    }

    @Override
    public void sendMessage(String str) {

    }

    @Override
    public boolean hasGroupPermission(Group g) {
        return false;
    }
}
