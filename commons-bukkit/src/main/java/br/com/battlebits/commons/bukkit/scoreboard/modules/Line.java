package br.com.battlebits.commons.bukkit.scoreboard.modules;

public class Line {

    private String prefix;
    private String name;
    private String suffix;

    public Line(String prefix, String name, String suffix) {
        this.prefix = prefix;
        this.name = name;
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getName() {
        return name;
    }

    public String getSuffix() {
        return suffix;
    }
}