package br.com.battlebits.commons.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor
@Getter
public enum Tag {

    ADMIN("ADMIN", Group.ADMIN, '4', true), //
    DEVELOPER("DEV", Group.DEVELOPER, '1', true), //
    BUILDER("BUILDER", Group.BUILDER, 'e', true), //
    DONATORPLUS("DONATOR+", Group.DONATORPLUS, '6', true), //
    DONATOR("DONATOR", Group.DONATOR, '9', true), //
    CREATOR("CREATOR", Group.CREATOR, '5', true), //
    DEFAULT("", Group.DEFAULT, '7', false);

    private String prefix;
    private Group groupToUse;
    private char color;
    private boolean isExclusive;

    public static Optional<Tag> byId(int id) {
        for (Tag tag : values()) {
            if(tag.ordinal() == id) {
                return Optional.of(tag);
            }
        }
        return Optional.empty();
    }

}
