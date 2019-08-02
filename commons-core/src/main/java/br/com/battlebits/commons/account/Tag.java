package br.com.battlebits.commons.account;

import br.com.battlebits.commons.translate.Language;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;

public enum Tag {

    ADMIN("§c§lADMIN§c", Group.ADMIN, false), //
    DEV("§3§lDEV§3", Group.DEVELOPER, true), //
    BUILDER("§e§lBUILDER§e", Group.BUILDER, true), //
    DONATORPLUS("§3§lDONATOR+§3", Group.DONATORPLUS, true), //
    DONATOR("§d§lDONATOR§d", Group.DONATOR, true), //
    INFLUENCER("§6§lINFLUENCER§6", Group.INFLUENCER, true), //
    DEFAULT("§7", Group.DEFAULT, false);

    private String prefix;
    private Group groupToUse;
    private boolean isExclusive;

    Tag(String prefix, Group toUse, boolean exclusive) {
        this.prefix = prefix;
        this.groupToUse = toUse;
        this.isExclusive = exclusive;
    }

    public String getPrefix() {
        return prefix;
    }

    public Group getGroupToUse() {
        return groupToUse;
    }

    public boolean isExclusive() {
        return isExclusive;
    }

}
