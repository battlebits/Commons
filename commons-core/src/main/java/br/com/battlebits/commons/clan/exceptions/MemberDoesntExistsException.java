package br.com.battlebits.commons.clan.exceptions;

public class MemberDoesntExistsException extends Exception {

    public MemberDoesntExistsException() {
        super("this member doesn't exists.");
    }
}
