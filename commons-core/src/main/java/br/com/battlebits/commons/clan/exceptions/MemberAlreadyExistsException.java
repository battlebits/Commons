package br.com.battlebits.commons.clan.exceptions;

public class MemberAlreadyExistsException extends Exception {

    public MemberAlreadyExistsException() {
        super("this member already exists.");
    }
}
