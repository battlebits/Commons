package br.com.battlebits.commons.bukkit.account;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.account.Tag;
import br.com.battlebits.commons.backend.model.ModelAccount;
import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.event.account.PlayerChangeTagEvent;
import br.com.battlebits.commons.bukkit.event.account.PlayerLanguageEvent;
import br.com.battlebits.commons.translate.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class BukkitAccount extends BattleAccount {

    private Player player;
    private UUID lastTellUUID;
    private ArrayList<Tag> tags = new ArrayList<>();

    public BukkitAccount(ModelAccount model) {
        super(model);
    }

    public BukkitAccount(UUID uniqueId, String name) {
        super(uniqueId, name);
    }

    @Override
    public boolean setTag(Tag tag) {
        return setTag(tag, false);
    }

    public boolean setTag(Tag tag, boolean forcetag) {
        if (!tags.contains(tag) && !forcetag) {
            tag = getDefaultTag();
        }
        if(!Bukkit.isPrimaryThread())
            return false;
        PlayerChangeTagEvent event = new PlayerChangeTagEvent(getPlayer(), getTag(), tag, forcetag);
        BukkitMain.getInstance().getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            if (!forcetag)
                super.setTag(tag);
        }
        return !event.isCancelled();
    }

    public Tag getDefaultTag() {
        return tags.get(0);
    }

    @Override
    public void setXp(int xp) {
        super.setXp(xp);
        // TODO Level Up
    }

    public void loadTags() {
        tags.clear();
        for (Tag t : Tag.values()) {
            if ((t.isExclusive()
                    && (t.getGroupToUse() == getServerGroup() || getServerGroup().ordinal() >= Group.ADMIN.ordinal()))
                    || (!t.isExclusive() && getServerGroup().ordinal() >= t.getGroupToUse().ordinal())) {
                tags.add(t);
            }
        }
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    @Override
    public void setJoinData(String userName, String ipAdrress) {
        super.setJoinData(userName, ipAdrress);
        loadTags();
        setTag(getDefaultTag());
    }

    @Override
    public void setLanguage(Language language) {
        super.setLanguage(language);
        Bukkit.getPluginManager().callEvent(new PlayerLanguageEvent(getPlayer(), this, language));
    }

    public UUID getLastTellUUID() {
        return lastTellUUID;
    }

    public void setLastTellUUID(UUID lastTellUUID) {
        this.lastTellUUID = lastTellUUID;
    }

    public boolean hasLastTell() {
        return this.lastTellUUID != null;
    }

    public Player getPlayer() {
        if (player == null)
            this.player = BukkitMain.getInstance().getServer().getPlayer(this.getUniqueId());
        return player;
    }

    public static BukkitAccount getAccount(UUID uuid) {
        return (BukkitAccount) Commons.getAccount(uuid);
    }

    @Override
    public void sendMessage(String tag, Object... objects) {
        player.sendMessage(tl(getLanguage(), tag, objects));
    }
}
