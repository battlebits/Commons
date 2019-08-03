package br.com.battlebits.commons.util.mojang;

import com.google.gson.JsonElement;

import java.util.UUID;

public class UUIDParser {

    public static UUID parse(JsonElement element) {
        return parse(element.getAsString());
    }

    public static UUID parse(String string) {
        if (string != null && !string.isEmpty()) {
            if (string.matches("[0-9a-fA-F]{32}")) {
                return UUID.fromString(string.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
            } else if (string.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}")) {
                return UUID.fromString(string);
            }
        }
        return null;
    }

}
