package br.com.battlebits.commons.bukkit.scoreboard.modules;

public class Line {

    private String name;
    private String prefix;
    private String suffix;

    public Line(String name, String prefix, String suffix) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }
}