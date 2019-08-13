package br.com.battlebits.commons.clan.exceptions;

public class ClanDoesntExistsException extends Exception {

    public ClanDoesntExistsException() {
        super("this clan doesn't exists.");
    }
}
