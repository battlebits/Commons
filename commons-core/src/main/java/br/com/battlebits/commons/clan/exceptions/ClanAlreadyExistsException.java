package br.com.battlebits.commons.clan.exceptions;

public class ClanAlreadyExistsException extends Exception {

    public ClanAlreadyExistsException() {
        super("this clan already exists.");
    }
}
