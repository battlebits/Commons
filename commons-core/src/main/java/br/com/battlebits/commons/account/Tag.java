package br.com.battlebits.commons.account;

public enum Tag {

    ADMIN("ADMIN", Group.ADMIN, 'c', true), //
    DEV("DEV", Group.DEVELOPER, '3', true), //
    BUILDER("BUILDER", Group.BUILDER, 'e', true), //
    DONATORPLUS("DONATOR+", Group.DONATORPLUS, '3', true), //
    DONATOR("DONATOR", Group.DONATOR, 'd', true), //
    INFLUENCER("INFLUENCER", Group.INFLUENCER, '6', true), //
    DEFAULT("", Group.DEFAULT, '7', false);

    private String prefix;
    private char color;
    private Group groupToUse;
    private boolean isExclusive;

    Tag(String prefix, Group toUse, char color, boolean exclusive) {
        this.prefix = prefix;
        this.color = color;
        this.groupToUse = toUse;
        this.isExclusive = exclusive;
    }

    public String getPrefix() {
        return prefix;
    }

    public char getColor() {
        return color;
    }

    public Group getGroupToUse() {
        return groupToUse;
    }

    public boolean isExclusive() {
        return isExclusive;
    }

}
