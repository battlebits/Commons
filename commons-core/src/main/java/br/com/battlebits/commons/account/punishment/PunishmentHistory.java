package br.com.battlebits.commons.account.punishment;

import java.util.ArrayList;
import java.util.List;

public class PunishmentHistory {
    private List<Ban> banHistory;
    private List<Mute> muteHistory;
    private List<Kick> kickHistory;

    public PunishmentHistory() {
        banHistory = new ArrayList<>();
        muteHistory = new ArrayList<>();
        kickHistory = new ArrayList<>();
    }

    public Ban getCurrentBan() {
        for (Ban ban : banHistory) {
            if (ban.isUnbanned())
                continue;
            if (ban.hasExpired())
                continue;
            return ban;
        }
        return null;
    }

    public Mute getCurrentMute() {
        for (Mute mute : muteHistory) {
            if (mute.isUnmuted())
                continue;
            if (mute.hasExpired())
                continue;
            return mute;
        }
        return null;
    }

    public boolean shouldAutoban() {
        int count = 0;
        for (Kick kick : kickHistory) {
            if ((System.currentTimeMillis() - kick.getKickTime()) > 2 * 60 * 60 * 1000)
                continue;
            ++count;
        }
        return count >= 2;
    }


    public List<Ban> getBanHistory() {
        return banHistory;
    }

    public List<Mute> getMuteHistory() {
        return muteHistory;
    }

    public List<Kick> getKickHistory() {
        if(kickHistory == null)
            kickHistory = new ArrayList<>();
        return kickHistory;
    }
}
