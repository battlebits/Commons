package br.com.battlebits.commons.backend;

public interface Database {

    public void connect() throws Exception;

    public void disconnect() throws Exception;

    public boolean isConnected() throws Exception;

}
