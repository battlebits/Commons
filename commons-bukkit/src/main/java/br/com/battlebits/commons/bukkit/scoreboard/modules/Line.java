package br.com.battlebits.commons.bukkit.scoreboard.modules;

public class Line {

    private String prefix;
    private String name;
    private String suffix;

    public Line(String id, String text) {
        this.name = id;
        setText(text);
    }

    public Line(String name, String prefix, String suffix) {
        this.prefix = prefix;
        this.name = name;
        this.suffix = suffix;
    }

    public void setText(String text) {
        String part1 = text;
        String part2 = "";
        if (text.length() > 12) {
            int a = 12;
            while (text.substring(0, a).endsWith("§"))
                --a;
            part1 = text.substring(0, a);
            part2 = text.substring(a, text.length());
            if (!part2.startsWith("§"))
                for (int i = part1.length(); i > 0; i--) {
                    if (part1.substring(i - 1, i).equals("§") && part1.substring(i, i + 1) != null) {
                        part2 = part1.substring(i - 1, i + 1) + part2;
                        break;
                    }
                }
            if (!part2.startsWith("§"))
                part2 = "§f" + part2;
        }

        this.prefix = part1;
        this.suffix = part2;
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