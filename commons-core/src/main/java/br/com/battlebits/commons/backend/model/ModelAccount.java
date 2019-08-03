package br.com.battlebits.commons.backend.model;

import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Blocked;
import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.account.Tag;
import br.com.battlebits.commons.server.ServerType;
import br.com.battlebits.commons.translate.Language;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ModelAccount {
    // INFORMACOES DA CONTA

    private String name;
    private UUID id;

    // DADOS DA CONTA
    private int battleCoins;
    private int battleMoney;
    private int xp;

    // XP MULTIPLIER
    private int doubleXpMultiplier;
    private long lastActivatedMultiplier;

    private Tag tag;

    // ADDRESSES E NETWORKING
    private String lastIpAddress;

    // PLAYING
    private long onlineTime;
    private long lastLoggedIn;
    private long firstTimePlaying;

    // GRUPOS
    private Group group;

    // BLOCKED
    private Map<String, ModelBlocked> blockedPlayers;

    // CONFIGURACOES
    private ModelAccountConfiguration configuration;

    // PAIS E LINGUA
    private Language language;

    // HISTORIA
    private ModelPunishmentHistory punishmentHistory;

    private String serverConnected;
    private ServerType serverConnectedType;

    public ModelAccount(BattleAccount account) {
        this.name = account.getName();
        this.id = account.getUniqueId();
        this.battleCoins = account.getBattleCoins();
        this.battleMoney = account.getBattleMoney();
        this.xp = account.getXp();
        this.doubleXpMultiplier = account.getDoubleXpMultiplier();
        this.lastActivatedMultiplier = account.getLastActivatedMultiplier();
        this.tag = account.getTag();
        this.lastIpAddress = account.getLastIpAddress();
        this.onlineTime = account.getOnlineTime();
        this.lastLoggedIn = account.getLastLoggedIn();
        this.firstTimePlaying = account.getFirstTimePlaying();
        this.group = account.getGroup();
        Map<String, ModelBlocked> blockedPlayers = new HashMap<>();
        for (Blocked b : account.getBlockedPlayers().values())
            blockedPlayers.put(b.getUniqueId().toString(), new ModelBlocked(b));
        this.blockedPlayers = blockedPlayers;
        this.configuration = new ModelAccountConfiguration(account.getConfiguration());
        this.language = account.getLanguage();
        this.punishmentHistory = new ModelPunishmentHistory(account.getPunishmentHistory());
        this.serverConnected = account.getServerConnected();
        this.serverConnectedType = account.getServerConnectedType();
    }
}
