package br.com.battlebits.commons.bukkit.util.string;


import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.COLOR_CHAR;

public class StringScroller {

    private int position;
    private List<String> list;
    private ChatColor colour = null;

    /**
     * @param message      The String to scroll
     * @param width        The width of the window to scroll across (i.e. 16 for signs)
     * @param spaceBetween The amount of spaces between each repetition
     */
    public StringScroller(String message, int width, int spaceBetween) {
        list = new ArrayList<String>();

        // Validation
        // String is too short for window
        if (message.length() < width) {
            StringBuilder sb = new StringBuilder(message);
            while (sb.length() < width)
                sb.append(" ");
            message = sb.toString();
        }

        // Allow for colours which add 2 to the width
        width -= 2;

        // Invalid width/space size
        if (width < 1)
            width = 1;
        if (spaceBetween < 0)
            spaceBetween = 0;

        // Add substrings
        for (int i = 0; i < message.length() - width; i++)
            list.add(message.substring(i, i + width));

        // Add space between repeats
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < spaceBetween; ++i) {
            list.add(message.substring(message.length() - width + (i > width ? width : i),
                    message.length()) + space);
            if (space.length() < width)
                space.append(" ");
        }

        // Wrap
        for (int i = 0; i < width - spaceBetween; ++i)
            list.add(message.substring(message.length() - width + spaceBetween + i,
                    message.length()) + space + message.substring(0, i));

        // Join up
        for (int i = 0; i < spaceBetween; i++) {
            if (i > space.length())
                break;
            list.add(space.substring(0, space.length() - i) + message.substring(0,
                    width - (spaceBetween > width ? width : spaceBetween) + i));
        }
    }

    /**
     * @return Gets the next String to display
     */
    public String next() {
        StringBuilder sb = getNext();
        if (sb.charAt(sb.length() - 1) == COLOR_CHAR)
            sb.setCharAt(sb.length() - 1, ' ');

        if (sb.charAt(0) == COLOR_CHAR) {
            ChatColor c = ChatColor.getByChar(sb.charAt(1));
            if (c != null) {
                colour = c;
                sb = getNext();
                if (sb.charAt(0) != ' ')
                    sb.setCharAt(0, ' ');
            }
        }

        return (colour != null ? colour : "") + sb.toString();

    }

    private StringBuilder getNext() {
        return new StringBuilder(list.get(position++ % list.size()));
    }

}
