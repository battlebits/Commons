package br.com.battlebits.commons.backend.sql;

import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.account.Tag;
import br.com.battlebits.commons.account.punishment.Ban;
import br.com.battlebits.commons.account.punishment.Kick;
import br.com.battlebits.commons.account.punishment.Mute;
import br.com.battlebits.commons.account.punishment.PunishmentHistory;
import br.com.battlebits.commons.backend.DataAccount;
import br.com.battlebits.commons.backend.model.ModelAccount;
import br.com.battlebits.commons.backend.model.ModelAccountConfiguration;
import br.com.battlebits.commons.backend.model.ModelBlocked;
import br.com.battlebits.commons.backend.model.ModelPunishmentHistory;
import br.com.battlebits.commons.translate.Language;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

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
                    " DEFAULT '0', `doubleXpMultiplier` INT(8) NOT NULL DEFAULT '0', `lastActivatedMultiplier` BIGINT(32) NOT" +
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
                    "NULL, `mutedIp` VARCHAR(32) NOT NULL, `server` VARCHAR(16) NOT NULL, `muteTime` BIGINT(32) NOT NULL," +
                    " `reason` VARCHAR(128) NOT NULL, `unmuted` TINYINT(1) NOT NULL DEFAULT '0', `unmutedBy` VARCHAR(16)" +
                    " NOT NULL DEFAULT ' ', `unmutedByUniqueId` BIGINT(32) NOT NULL DEFAULT '0', `unmuteTime` BIGINT(32)" +
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
        try (PreparedStatement stmt = database.prepareStatement("SELECT * FROM `accounts` WHERE `uuid`=?")) {
            stmt.setString(1, uuid.toString());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                /**
                 * Account
                 */
                final ModelAccount.ModelAccountBuilder builder = ModelAccount.builder()
                        .name(rs.getString("name"))
                        .id(uuid)
                        .battleCoins(rs.getInt("battleCoins"))
                        .battleMoney(rs.getInt("battleMoney"))
                        .xp(rs.getInt("xp"))
                        .level(rs.getInt("level"))
                        .doubleXpMultiplier(rs.getInt("doubleXpMultiplier"))
                        .lastActivatedMultiplier(rs.getLong("lastActivatedMultiplier"))
                        .tag(Tag.byId(rs.getInt("tag")).orElse(Tag.DEFAULT))
                        .lastIpAddress(rs.getString("lastIpAddress"))
                        .onlineTime(rs.getLong("onlineTime"))
                        .lastLoggedIn(rs.getLong("lastLoggedIn"))
                        .firstTimePlaying(rs.getLong("firstTimePlaying"))
                        .group(Group.byId(rs.getInt("group")).orElse(Group.DEFAULT))
                        .language(Language.byId(rs.getInt("language")).orElse(Language.PORTUGUESE));

                /**
                 * Configuration
                 */
                PreparedStatement configuration = database.prepareStatement("SELECT * FROM `accounts_configuration` WHERE `uuid`=?");
                configuration.setString(1, uuid.toString());
                ResultSet rsConfiguration = configuration.executeQuery();
                if (rsConfiguration.next()) {
                    builder.configuration(new ModelAccountConfiguration(rsConfiguration.getBoolean("ignoreAll"),
                            rsConfiguration.getBoolean("tellEnabled"), rsConfiguration.getBoolean("staffChatEnabled"),
                            rsConfiguration.getBoolean("partyInvites")));
                }
                rsConfiguration.close();

                /**
                 * Bans
                 */
                PreparedStatement bans = database.prepareStatement("SELECT * FROM `accounts_punishment_bans` WHERE `uuid`=?");
                bans.setString(1, uuid.toString());
                ResultSet rsBans = bans.executeQuery();
                List<Ban> bansList = new ArrayList<>();
                while (rsBans.next()) {
                    bansList.add(Ban.builder()
                            .bannedBy(rs.getString("bannedBy"))
                            .bannedByUniqueId(UUID.fromString(rs.getString("bannedByUniqueId")))
                            .bannedIp(rs.getString("bannedIp"))
                            .server(rs.getString("server"))
                            .banTime(rs.getLong("banTime"))
                            .reason(rs.getString("reason"))
                            .unbanned(rs.getBoolean("unbanned"))
                            .unbannedBy(rs.getString("unbannedBy"))
                            .unbannedByUniqueId(UUID.fromString(rs.getString("unbannedByUniqueId")))
                            .unbanTime(rs.getLong("unbanTime"))
                            .expire(rs.getLong("expire"))
                            .duration(rs.getLong("duration"))
                            .build());
                }
                rsBans.close();

                /**
                 * Mutes
                 */
                PreparedStatement mutes = database.prepareStatement("SELECT * FROM `accounts_punishment_mutes` WHERE `uuid`=?");
                mutes.setString(1, uuid.toString());
                ResultSet rsMutes = mutes.executeQuery();
                List<Mute> mutesList = new ArrayList<>();
                while (rsMutes.next()) {
                    mutesList.add(Mute.builder()
                            .mutedBy(rs.getString("mutedBy"))
                            .mutedByUniqueId(UUID.fromString(rs.getString("mutedByUniqueId")))
                            .mutedIp(rs.getString("mutedIp"))
                            .server(rs.getString("server"))
                            .muteTime(rs.getLong("muteTime"))
                            .reason(rs.getString("reason"))
                            .unmuted(rs.getBoolean("unmuted"))
                            .unmutedBy(rs.getString("unmutedBy"))
                            .unmutedByUniqueId(UUID.fromString(rs.getString("unmutedByUniqueId")))
                            .unmuteTime(rs.getLong("unmuteTime"))
                            .expire(rs.getLong("expire"))
                            .duration(rs.getLong("duration"))
                            .build());
                }
                rsMutes.close();

                /**
                 * Kicks
                 */
                PreparedStatement kicks = database.prepareStatement("SELECT * FROM `accounts_punishment_kicks` WHERE `uuid`=?");
                kicks.setString(1, uuid.toString());
                ResultSet rsKicks = kicks.executeQuery();
                List<Kick> kicksList = new ArrayList<>();
                while (rsKicks.next()) {
                    kicksList.add(Kick.builder()
                            .server(rs.getString("server"))
                            .kickTime(rs.getLong("kickTime"))
                            .reason(rs.getString("reason"))
                            .build());
                }
                rsKicks.close();

                /**
                 * Kicks
                 */
                PreparedStatement blocks = database.prepareStatement("SELECT * FROM `accounts_blocks` WHERE `uuid`=?");
                kicks.setString(1, uuid.toString());
                ResultSet rsBlocks = blocks.executeQuery();
                Map<String, ModelBlocked> blocksMap = new HashMap<>();
                while (rsBlocks.next()) {
                    blocksMap.put(rsBlocks.getString("uuidTarget"), new ModelBlocked(UUID.fromString(rsBlocks
                            .getString("uuidTarget")), rsBlocks.getLong("blockedTime")));
                }
                rsBlocks.close();

                builder.punishmentHistory(new ModelPunishmentHistory(bansList, mutesList, kicksList));
                builder.blockedPlayers(blocksMap);
                return builder.build();
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final ModelAccount newAccount = ModelAccount.builder()
                .id(uuid)
                .battleCoins(0)
                .battleMoney(0)
                .xp(0)
                .level(0)
                .doubleXpMultiplier(1)
                .lastActivatedMultiplier(0)
                .tag(Tag.DEFAULT)
                .lastIpAddress(" ")
                .onlineTime(0)
                .lastLoggedIn(System.currentTimeMillis())
                .firstTimePlaying(System.currentTimeMillis())
                .group(Group.DEFAULT)
                .language(Language.PORTUGUESE)
                .punishmentHistory(new ModelPunishmentHistory(new PunishmentHistory()))
                .configuration(new ModelAccountConfiguration(true, true, true, true))
                .blockedPlayers(new HashMap<>())
                .build();
        saveNewAccount(newAccount);
        return newAccount;
    }

    private void saveNewAccount(ModelAccount account) {
        try (PreparedStatement accountStmt = database.prepareStatement("INSERT INTO `accounts` (`uuid`, `name`, `tag`, " +
                "`lastIpAddress`, `lastLoggedIn`, `firstTimePlaying`, `group`) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            accountStmt.setString(1, account.getId().toString());
            accountStmt.setString(2, account.getName());
            accountStmt.setInt(3, account.getTag().ordinal());
            accountStmt.setString(4, account.getLastIpAddress());
            accountStmt.setLong(5, account.getLastLoggedIn());
            accountStmt.setLong(6, account.getFirstTimePlaying());
            accountStmt.setLong(7, account.getGroup().ordinal());
            accountStmt.execute();

            PreparedStatement configurationStmt = database.prepareStatement("INSERT INTO `accounts_configuration` " +
                    "(`uuid`) VALUES (?)");
            configurationStmt.setString(1, account.getId().toString());
            configurationStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAccount(BattleAccount account) {
        try (PreparedStatement stmt = database.prepareStatement("UPDATE `accounts` SET `name`=?, `battleCoins`=?, " +
                "`battleMoney`=?, `xp`=?, `level`=?, `doubleXpMultiplier`=?, `lastActivatedMultiplier`=?, `tag`=?, " +
                "`lastIpAddress`=?, `onlineTime`=?, `lastLoggedIn`=?, `group`=?, `language`=? WHERE `uuid`=?")) {
            stmt.setString(1, account.getName());
            stmt.setInt(2, account.getBattleCoins());
            stmt.setInt(3, account.getBattleMoney());
            stmt.setInt(4, account.getXp());
            stmt.setInt(5, account.getLevel());
            stmt.setInt(6, account.getDoubleXpMultiplier());
            stmt.setLong(7, account.getLastActivatedMultiplier());
            stmt.setInt(8, account.getTag().ordinal());
            stmt.setString(9, account.getLastIpAddress());
            stmt.setLong(10, account.getOnlineTime());
            stmt.setLong(11, account.getLastLoggedIn());
            stmt.setInt(12, account.getGroup().ordinal());
            stmt.setInt(13, account.getLanguage().ordinal());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAccount(BattleAccount account, String field) {

    }

    @Override
    public void saveConfiguration(BattleAccount account, String field) {

    }
}
