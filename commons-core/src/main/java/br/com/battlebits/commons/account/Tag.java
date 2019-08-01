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
    DONATOR("§d§lDONATOR§d", Group.DONATOR, false), //
    INFLUENCER("§6§lINFLUENCER§6", Group.INFLUENCER, false), //
    NORMAL("§7", Group.DEFAULT, false);

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

    public String getPrefix(Language language) {
        String prefix = this.prefix;
        Matcher matcher = null; // TODO T.getPattern().matcher(prefix);
        while (matcher.find()) {
            String replace = matcher.group(), id = matcher.group(2).toLowerCase();
            // TODO prefix = prefix.replace(replace, Commons.getTranslate().get(language, id).toUpperCase());
        }
        return prefix;
    }

    private static final Map<String, Tag> TAG_MAP;

    static {
        Map<String, Tag> map = new ConcurrentHashMap<>();
        for (Tag tag : Tag.values()) {
            map.put(tag.name().toLowerCase(), tag);
            for (Language lang : Language.values()) {
                String prefix = "";// TODO ChatColor.stripColor(tag.getPrefix(lang));
                map.put(prefix.toLowerCase(), tag);
            }
        }
        TAG_MAP = Collections.unmodifiableMap(map);
    }

    /**
     *
     * @param name String
     * @return Tag
     */
    public static Tag getByName(String name) {
        Objects.requireNonNull(name, "Parameter 'name' is null.");
        return TAG_MAP.get(name.toLowerCase());
    }
}
