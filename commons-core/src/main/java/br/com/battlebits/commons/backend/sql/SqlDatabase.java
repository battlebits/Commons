package br.com.battlebits.commons.backend.sql;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.backend.Database;
import lombok.RequiredArgsConstructor;

import java.sql.*;

@RequiredArgsConstructor
public class SqlDatabase implements Database {

    private Connection connection = null;
    private final String hostname, database, username, password;
    private final int port;

    public SqlDatabase() {
        this("localhost", "commons", "root", "", 3306);
    }

    @Override
    public void connect() throws Exception {
        Commons.getLogger().info("Conectando ao MySQL");
        Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
        connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database, username,
                password);
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
        if (connection.isClosed())
            return false;
        return true;
    }

    private void recallConnection()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        if (!isConnected()) {
            Commons.getLogger().info("Reconectando ao MySQL");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database, username,
                    password);
        }
    }

    public void update(String sqlString)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        if (!isConnected()) {
            recallConnection();
        }
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sqlString);
        stmt.close();
        stmt = null;
    }

    public PreparedStatement prepareStatment(String sql)
            throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (!isConnected()) {
            recallConnection();
        }
        return connection.prepareStatement(sql);
    }

    private Connection getConnection()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        if (!isConnected()) {
            recallConnection();
        }
        return connection;
    }
}
