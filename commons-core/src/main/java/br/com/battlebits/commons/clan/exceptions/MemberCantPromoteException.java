package br.com.battlebits.commons.clan.exceptions;

public class MemberCantPromoteException extends Exception {

    public MemberCantPromoteException() {
        super("this member cannot be promoted.");
    }
}
