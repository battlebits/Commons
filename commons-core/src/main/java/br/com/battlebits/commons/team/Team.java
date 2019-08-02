package br.com.battlebits.commons.team;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.backend.DataTeam;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
public class Team {
    private UUID uniqueId;
    private String name;
    private String abbreviation;
    private int xp = 0;
    private int slots = 5;
    private UUID owner;
    private Set<UUID> administrators;
    private HashMap<UUID, String> participants;
    private Set<UUID> invites;
    private long confirmDisband = Long.MAX_VALUE;
    @Setter
    private boolean cacheOnQuit = false;

    private static DataTeam STORAGE = Commons.getDataTeam();

    public Team(String name, String abbreviation, BattleAccount owner) {
        this.uniqueId = UUID.randomUUID();
        this.owner = owner.getUniqueId();
        this.name = name;
        this.abbreviation = abbreviation;
        participants = new HashMap<>();
        administrators = new HashSet<>();
        invites = new HashSet<>();
        participants.put(owner.getUniqueId(), owner.getName());
        administrators.add(owner.getUniqueId());
    }

    public String getPlayerName(UUID uuid) {
        return participants.get(uuid);
    }

    public void addXp(int xp) {
        if (xp < 0)
            xp = 0;
        this.xp += xp;
        STORAGE.saveTeam(this, "xp");
    }

    public void changeAbbreviation(String str) {
        abbreviation = str;
        STORAGE.saveTeam(this, "abbreviation");
    }

    public boolean isOwner(BattleAccount player) {
        return isOwner(player.getUniqueId());
    }

    public boolean isOwner(UUID uuid) {
        return owner.equals(uuid);
    }

    public boolean isAdministrator(BattleAccount player) {
        return isAdministrator(player.getUniqueId());
    }

    public boolean isAdministrator(UUID uuid) {
        return administrators.contains(uuid);
    }

    public boolean isParticipant(BattleAccount player) {
        return isParticipant(player.getUniqueId());
    }

    public boolean isParticipant(UUID uuid) {
        return participants.containsKey(uuid);
    }

    public boolean isInvited(BattleAccount player) {
        return isInvited(player.getUniqueId());
    }

    public boolean isInvited(UUID uuid) {
        return invites.contains(uuid);
    }

    public boolean confirm() {
        if (System.currentTimeMillis() < confirmDisband + 60000)
            return true;
        confirmDisband = System.currentTimeMillis();
        return false;
    }

    public boolean promote(UUID uuid) {
        if (!participants.containsKey(uuid))
            return false;
        if (administrators.contains(uuid))
            return false;
        administrators.add(uuid);
        STORAGE.saveTeam(this, "administrators");
        return true;
    }

    public boolean demote(UUID uuid) {
        if (!participants.containsKey(uuid))
            return false;
        if (!administrators.contains(uuid))
            return false;
        administrators.remove(uuid);
        STORAGE.saveTeam(this, "administrators");
        return true;
    }

    public boolean updatePlayer(BattleAccount player) {
        if (getSlots() >= getParticipants().size())
            return false;
        if (isOwner(player))
            return false;
        UUID uuid = player.getUniqueId();
        if (isAdministrator(player)) {
            ArrayList<UUID> random = new ArrayList<>();
            for (UUID unique : getParticipants().keySet()) {
                if (!isAdministrator(unique)) {
                    random.add(unique);
                }
            }
            if (random.size() > 0) {
                int i = random.size() - 1 > 0 ? new Random().nextInt(random.size() - 1) : 0;
                uuid = random.get(i);
            }
            if (uuid == player.getUniqueId())
                demote(uuid);
        }
        removeParticipant(player.getUniqueId());
        return true;
    }

    public void addParticipant(BattleAccount player) {
        invites.remove(player.getUniqueId());
        participants.put(player.getUniqueId(), player.getName());
        STORAGE.saveTeam(this, "invites");
        STORAGE.saveTeam(this, "participants");
        player.setTeamUniqueId(uniqueId);
    }

    public boolean removeParticipant(UUID uuid) {
        if (owner == uuid)
            return false;
        if (administrators.contains(uuid))
            return false;
        participants.remove(uuid);
        STORAGE.saveTeam(this, "participants");
        return true;
    }

    public void invite(BattleAccount player) {
        if (invites.contains(player.getUniqueId()))
            return;
        invites.add(player.getUniqueId());
        STORAGE.saveTeam(this, "invites");
    }

    public void removeInvite(UUID uuid) {
        if (!invites.contains(uuid))
            return;
        invites.remove(uuid);
        STORAGE.saveTeam(this, "invites");
    }

    public int getSlots() {
        return slots;
    }

    private void setSlots(int slots) {
        this.slots = slots;
        STORAGE.saveTeam(this, "slots");
    }

    public int addSlots(int slots) {
        if (xp < 0)
            xp = 0;
        int set = this.slots + slots;
        setSlots(set);
        return xp;
    }

    public int removeSlots(int slots) {
        if (slots < 0)
            slots = 0;
        int setarxp = this.slots - slots;
        setSlots(setarxp);
        return xp;
    }

}
