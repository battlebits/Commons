package br.com.battlebits.commons.bukkit.scoreboard.modules;

import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public class Line {
    private String id;

    private String prefix;
    private String name;
    private String suffix;

    public Line(String id, String text) {
        this.name = id;
        setText(text);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < id.length(); i++)
            builder.append(ChatColor.COLOR_CHAR).append(id.charAt(i));
        this.id = builder.toString();
        if (this.id.length() > 16)
            this.id = this.id.substring(0, 16);
    }

    public Line(String name, String prefix, String suffix) {
        this.prefix = prefix;
        this.name = name;
        this.id = name;
        this.suffix = suffix;
    }

    public void setText(String text) {
        String part1 = text;
        String part2 = "";
        if (text.length() > 16) {
            int a = 16;
            while (text.charAt(a) == ChatColor.COLOR_CHAR)
                --a;
            part1 = text.substring(0, a);
            part2 = ChatColor.getLastColors(part1) + text.substring(a);
            if (part2.charAt(0) != ChatColor.COLOR_CHAR)
                part2 = ChatColor.WHITE + part2;
        }

        this.prefix = part1;
        this.suffix = part2;
    }

}