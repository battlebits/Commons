package br.com.battlebits.commons.backend.sql;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.backend.Database;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

@RequiredArgsConstructor
public class SqlDatabase implements Database {

    private Connection connection = null;
    private final String hostname, database, username, password;
    private final boolean autoReconnect;
    private final int port;

    public SqlDatabase() {
        this("localhost", "commons", "root", "", true, 3306);
    }

    @Override
    public void connect() throws Exception {
        Commons.getLogger().info("Conectando ao MySQL");
        Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
        connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database + "autoReconnect="
                + autoReconnect, username, password);
    }

    @Override
    public void disconnect() throws Exception {
        if (isConnected())
            connection.close();
    }

    @Override
    public boolean isConnected() throws SQLException {
        if (connection == null)
            return false;
        return !connection.isClosed();
    }

    private void recallConnection()
            throws SQLException, ClassNotFoundException {
        if (!isConnected()) {
            Commons.getLogger().info("Reconectando ao MySQL");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database, username,
                    password);
        }
    }

    public void update(String sqlString)
            throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            recallConnection();
        }
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sqlString);
        stmt.close();
        stmt = null;
    }

    public PreparedStatement prepareStatment(String sql)
            throws SQLException, ClassNotFoundException {
        if (!isConnected()) {
            recallConnection();
        }
        return connection.prepareStatement(sql);
    }

    private Connection getConnection()
            throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            recallConnection();
        }
        return connection;
    }
}
