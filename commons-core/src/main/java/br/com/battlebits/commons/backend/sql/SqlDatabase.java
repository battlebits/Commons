package br.com.battlebits.commons.backend.sql;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.backend.Database;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;

import java.sql.*;

@RequiredArgsConstructor
public class SqlDatabase implements Database {

    private HikariDataSource hikariDataSource;
    private final String hostname, database, username, password;
    private final int port;

    public SqlDatabase() {
        this("localhost", "commons", "root", "", 3306);
    }

    @Override
    public void connect() throws SQLException {
        Commons.getLogger().info("Conectando ao MySQL");

        HikariConfig hikariConfig = buildHikariConfig();

        this.hikariDataSource = new HikariDataSource(hikariConfig);
    }

    @Override
    public void disconnect() throws Exception {
        if (isConnected())
            this.hikariDataSource.close();
    }

    @Override
    public boolean isConnected() throws SQLException {
        if (this.hikariDataSource == null)
            return false;
        return this.hikariDataSource.isRunning();
    }

    public void update(String sqlString)
            throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.executeUpdate(sqlString);
        stmt.close();
    }

    public PreparedStatement prepareStatement(String sql)
            throws SQLException {
        return getConnection().prepareStatement(sql);
    }

    public Connection getConnection() throws SQLException {
        if(!isConnected()){
            this.connect();
        }

        return this.hikariDataSource.getConnection();
    }

    private HikariConfig buildHikariConfig() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://" + hostname + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return config;
    }

}
