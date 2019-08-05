package br.com.battlebits.commons.account;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.CommonsConst;
import br.com.battlebits.commons.account.punishment.PunishmentHistory;
import br.com.battlebits.commons.backend.DataAccount;
import br.com.battlebits.commons.backend.model.ModelAccount;
import br.com.battlebits.commons.backend.model.ModelBlocked;
import br.com.battlebits.commons.command.CommandSender;
import br.com.battlebits.commons.server.ServerType;
import br.com.battlebits.commons.team.Team;
import br.com.battlebits.commons.translate.Language;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public abstract class BattleAccount implements CommandSender {

    // INFORMACOES DA CONTA
    /**
     * player name
     */
    private String name;
    /**
     * uniqueId
     */
    private UUID uniqueId;

    /**
     * economy and xp
     */
    private int battleCoins = 0;
    private int battleMoney = 0;
    private int xp = 0;

    /**
     * XP Multiplier
     */
    private int doubleXpMultiplier = 0;
    private long lastActivatedMultiplier = Long.MIN_VALUE;

    /**
     * Player tag
     */
    private Tag tag;

    /**
     * Addesses
     */
    private transient String ipAddress = "";
    private String lastIpAddress = "";

    /**
     * information
     */
    private long onlineTime = 0l;
    private long joinTime;
    private long lastLoggedIn;
    private long firstTimePlaying;

    /**
     * groups
     */
    private Group group = Group.DEFAULT;

    private UUID teamUniqueId = null;

    /**
     * blocked players
     */
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

    private boolean online;

    private String serverConnected = "";
    private ServerType serverConnectedType = ServerType.DEFAULT;

    private String lastServer = "";

    private static DataAccount STORAGE = Commons.getDataAccount();


    public BattleAccount(ModelAccount account) {
        this.name = account.getName();
        this.uniqueId = account.getId();
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

    public BattleAccount(UUID uniqueId, String name) {
        this.name = name;
        this.uniqueId = uniqueId;

        this.lastLoggedIn = System.currentTimeMillis();
        this.firstTimePlaying = System.currentTimeMillis();
    }

    @Override
    public abstract void sendMessage(String tag, Object... objects);

    @Override
    public boolean hasGroupPermission(Group group) {
        return getServerGroup().ordinal() >= group.ordinal();
    }

    public Group getServerGroup() {
        if (CommonsConst.isChristmas()) {
            if (group.ordinal() <= Group.DONATORPLUS.ordinal())
                return Group.DONATORPLUS;
        }
        return group;
    }

    public boolean isStaff() {
        if (group.ordinal() > Group.DEVELOPER.ordinal())
            return true;
        return false;
    }

    public long calculateOnlineTime() {
        return (System.currentTimeMillis() - joinTime) + onlineTime;
    }

    public Team getTeam() {
        if (teamUniqueId == null)
            return null;
        return Commons.getTeamCommon().getTeam(teamUniqueId);
    }

    public String getHostname() {
        return ipAddress;
    }

    public AccountConfiguration getConfiguration() {
        if (configuration == null)
            configuration = new AccountConfiguration(this);
        return configuration;
    }

    public void activateDoubleXp() {
        removeDoubleXpMultiplier(1);
        lastActivatedMultiplier = System.currentTimeMillis() + CommonsConst.MULTIPLIER_DURATION;
        STORAGE.saveAccount(this, "lastActivatedMultiplier");
    }

    public void addDoubleXpMultiplier(int i) {
        setDoubleXpMultiplier(doubleXpMultiplier += i);
    }

    public void removeDoubleXpMultiplier(int i) {
        int a = doubleXpMultiplier - i;
        if (a < 0)
            a = 0;
        setDoubleXpMultiplier(a);
    }

    private void setDoubleXpMultiplier(int i) {
        doubleXpMultiplier = i;
        STORAGE.saveAccount(this, "doubleXpMultiplier");
    }

    public int addBattleCoin(int coins) {
        this.battleCoins += coins;
        setBattleCoins(this.battleCoins);
        return this.battleCoins;
    }

    public int removeBattleCoin(int fichas) {
        this.battleCoins -= fichas;
        if (this.battleCoins < 0)
            this.battleCoins = 0;
        setBattleCoins(this.battleCoins);
        return this.battleCoins;
    }

    private void setBattleCoins(int i) {
        this.battleCoins = i;
        STORAGE.saveAccount(this, "battleCoins");
    }

    public int addBattleMoney(int money) {
        int multiplier = 1;
        int plus = money * multiplier;
        this.battleMoney += plus;
        setBattleMoney(this.battleMoney);
        return plus;
    }

    public int removeBattleMoney(int money) {
        this.battleMoney -= money;
        if (this.battleMoney < 0)
            this.battleMoney = 0;
        setBattleMoney(this.battleMoney);
        return this.battleMoney;
    }

    private void setBattleMoney(int i) {
        this.battleMoney = i;
        STORAGE.saveAccount(this, "battleMoney");
    }

    private void setXp(int xp) {
        this.xp = xp;
        STORAGE.saveAccount(this, "xp");
    }

    public int addXp(int xp) {
        if (xp < 0)
            xp = 0;
        if (isDoubleXPActivated())
            xp *= 2;
        int setarxp = this.xp + xp;
        setXp(setarxp);
        return xp;
    }

    public int removeXp(int xp) {
        if (xp < 0)
            xp = 0;
        int setarxp = this.xp - xp;
        setXp(setarxp);
        return xp;
    }

    public void setLanguage(Language language) {
        this.language = language;
        STORAGE.saveAccount(this, "language");
    }

    public void setTeamUniqueId(UUID teamUniqueId) {
        this.teamUniqueId = teamUniqueId;
        STORAGE.saveAccount(this, "teamUniqueId");
    }

    public void setGroup(Group group) {
        this.group = group;
        STORAGE.saveAccount(this, "group");
    }

    public void saveRanks() {
        STORAGE.saveAccount(this, "ranks");
    }

    public void connect(String serverId, ServerType type) {
        this.serverConnected = serverId;
        this.serverConnectedType = type;
        STORAGE.saveAccount(this, "serverConnected");
        STORAGE.saveAccount(this, "serverConnectedType");
    }

    public boolean setTag(Tag tag) {
        this.tag = tag;
        if (hasGroupPermission(Group.INFLUENCER)) {
            STORAGE.saveAccount(this, "tag");
        }
        return true;
    }

    public void setJoinData(String ipAdrress) {
        this.ipAddress = ipAdrress;
        connect(Commons.getServerId(), Commons.getServerType());
        joinTime = System.currentTimeMillis();
        this.online = true;
        STORAGE.saveAccount(this, "joinTime");
        STORAGE.saveAccount(this, "online");
    }

    public void setLeaveData() {
        this.online = false;
        lastLoggedIn = System.currentTimeMillis();
        onlineTime = calculateOnlineTime();
        if (ipAddress != null)
            lastIpAddress = ipAddress;
        ipAddress = null;
        STORAGE.saveAccount(this, "online");
        STORAGE.saveAccount(this, "lastLoggedIn");
        STORAGE.saveAccount(this, "onlineTime");
        STORAGE.saveAccount(this, "lastIpAddress");
    }

    public void setServerConnectedType(ServerType serverConnectedType) {
        if (this.serverConnectedType == null || serverConnectedType != this.serverConnectedType) {
            this.serverConnectedType = serverConnectedType;
            STORAGE.saveAccount(this, "serverConnectedType");
        }
    }

    public Tag getTag() {
        if (tag == null)
            tag = Tag.valueOf(getServerGroup().toString());
        return tag;
    }


    public boolean isDoubleXPActivated() {
        return System.currentTimeMillis() < lastActivatedMultiplier;
    }
}
