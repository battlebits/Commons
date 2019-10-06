package br.com.battlebits.commons.backend.sql;

import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.backend.DataAccount;
import br.com.battlebits.commons.backend.model.ModelAccount;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class SqlStorageDataAccount implements DataAccount {

    private final SqlDatabase database;

    public SqlStorageDataAccount(SqlDatabase database) {
        this.database = database;
    }

    private void setupTables() {
        try (Statement statement = database.getConnection().createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS `accounts` (`id` INT NOT NULL AUTO_INCREMENT, `uuid` " +
                    "VARCHAR(32) NOT NULL, `name` VARCHAR(16) NOT NULL, `battleCoins` INT(8) NOT NULL DEFAULT '0', " +
                    "`battleMoney` INT(8) NOT NULL DEFAULT '0', `xp` INT(8) NOT NULL DEFAULT '0', `level` INT(8) NOT NULL" +
                    " DEFAULT '0', `doubleXpMultiplier` INT(8) NOT NULL DEFAULT '0', `lastActivatedMultiplier` INT(8) NOT" +
                    " NULL DEFAULT '0', `tag` INT(2) NOT NULL, `lastIpAddress` VARCHAR(32) NOT NULL, `onlineTime` " +
                    "BIGINT(32) NOT NULL DEFAULT '0', `lastLoggedIn` BIGINT(32) NOT NULL, `firstTimePlaying` BIGINT(32) " +
                    "NOT NULL, `group` INT(2) NOT NULL, `language` INT(2) NOT NULL DEFAULT '0', PRIMARY KEY (`id`, `uuid`," +
                    " `name`) ENGINE = InnoDB;");
            statement.execute("CREATE TABLE IF NOT EXISTS `accounts_configuration` (`id` INT NOT NULL AUTO_INCREMENT," +
                    " `uuid` VARCHAR(32) NOT NULL, `ignoreAll` TINYINT(1) NOT NULL DEFAULT '0', `tellEnabled` TINYINT(1)" +
                    " NOT NULL DEFAULT '0', `staffChatEnabled` TINYINT(1) NOT NULL DEFAULT '0', `partyInvites` " +
                    "TINYINT(1) NOT NULL DEFAULT '0', PRIMARY KEY (`id`, `uuid`)) ENGINE = InnoDB;");
            statement.execute("CREATE TABLE IF NOT EXISTS `accounts_punishment_bans` (`id` INT NOT NULL AUTO_INCREMENT," +
                    " `uuid` VARCHAR(32) NOT NULL, `bannedBy` VARCHAR(16) NOT NULL, `bannedByUniqueId` VARCHAR(32) NOT " +
                    "NULL, `bannedIp` VARCHAR(32) NOT NULL, `server` VARCHAR(16) NOT NULL, `banTime` BIGINT(32) NOT NULL," +
                    " `reason` VARCHAR(128) NOT NULL, `unbanned` TINYINT(1) NOT NULL DEFAULT '0', `unbannedBy` VARCHAR(16)" +
                    " NOT NULL DEFAULT ' ', `unbannedByUniqueId` BIGINT(32) NOT NULL DEFAULT '0', `unbanTime` BIGINT(32)" +
                    " NOT NULL DEFAULT '0', `expire` BIGINT(32) NOT NULL, `duration` BIGINT(32) NOT NULL, PRIMARY KEY " +
                    "(`id`, `uuid`, `bannedIp`)) ENGINE = InnoDB;");
            statement.execute("CREATE TABLE IF NOT EXISTS `accounts_punishment_mutes` (`id` INT NOT NULL AUTO_INCREMENT," +
                    " `uuid` VARCHAR(32) NOT NULL, `mutedBy` VARCHAR(16) NOT NULL, `mutedByUniqueId` VARCHAR(32) NOT " +
                    "NULL, `mutedIp` VARCHAR(32) NOT NULL, `server` VARCHAR(16) NOT NULL, `banTime` BIGINT(32) NOT NULL," +
                    " `reason` VARCHAR(128) NOT NULL, `unmuted` TINYINT(1) NOT NULL DEFAULT '0', `unmutedBy` VARCHAR(16)" +
                    " NOT NULL DEFAULT ' ', `unmutedByUniqueId` BIGINT(32) NOT NULL DEFAULT '0', `unbanTime` BIGINT(32)" +
                    " NOT NULL DEFAULT '0', `expire` BIGINT(32) NOT NULL, `duration` BIGINT(32) NOT NULL, PRIMARY KEY " +
                    "(`id`, `uuid`, `mutedIp`)) ENGINE = InnoDB;");
            statement.execute("CREATE TABLE IF NOT EXISTS `accounts_punishment_kicks` (`id` INT NOT NULL AUTO_INCREMENT," +
                    " `uuid` VARCHAR(32) NOT NULL, `server` VARCHAR(16) NOT NULL, `kickTime` BIGINT(32) NOT NULL, `reason`" +
                    " VARCHAR(128) NOT NULL, PRIMARY KEY (`uuid`)) ENGINE = InnoDB;");
            statement.execute("CREATE TABLE IF NOT EXISTS `accounts_blocks` (`id` INT NOT NULL AUTO_INCREMENT, `uuid`" +
                    " VARCHAR(32) NOT NULL, `uuidTarget` VARCHAR(32) NOT NULL, `blockedTime` BIGINT(32) NOT NULL, PRIMARY" +
                    " KEY (`id`, `uuid`)) ENGINE = InnoDB;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ModelAccount getAccount(UUID uuid) {
        return null;
    }

    @Override
    public void saveAccount(BattleAccount account) {

    }

    @Override
    public void saveAccount(BattleAccount account, String field) {

    }

    @Override
    public void saveConfiguration(BattleAccount account, String field) {

    }
}
