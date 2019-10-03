package br.com.battlebits.commons.translate;

public enum TranslateTag {

    /**
     * Account
     */
    ACCOUNT_NOT_LOADED("account.not-loaded"),
    ACCOUNT_ALREADY_ONLINE("account.already-online"),
    ACCOUNT_LOAD_FAILED("account.load-failed"),
    ACCOUNT_HOVER_INFO,

    /**
     * Team
     */
    TEAM_HOVER_INFO,

    /**
     * Party
     */
    PARTY_PREFIX,

    /**
     * Time
     */
    TIME_DAY,
    TIME_HOUR,
    TIME_MINUTE,
    TIME_SECOND,

    /**
     * Menu
     */
    MENU_ACCOUNT_TITLE,
    MENU_ACCOUNT_DONT_HAVE_TEAM("menu.account.dont-have-team"),
    MENU_ACCOUNT_TEAM_NAME("menu.account.team-name"),
    MENU_ACCOUNT_TIME_INFO("menu.account.time-info"),
    MENU_ACCOUNT_FIRST_LOGIN("menu.account.first-login"),
    MENU_ACCOUNT_LAST_LOGIN("menu.account.last-login"),
    MENU_ACCOUNT_CURRENT_ONLINE("menu.account.current-online"),
    MENU_ACCOUNT_TOTAL_ONLINE("menu.account.total-online"),
    MENU_ACCOUNT_ACTUAL_GROUP("menu.account.actual-group"),
    MENU_ACCOUNT_BANS,
    MENU_ACCOUNT_BANS_LORE("menu.account.bans-lore"),
    MENU_ACCOUNT_MUTES,
    MENU_ACCOUNT_MUTES_LORE("menu.account.mutes-lore"),
    MENU_ACCOUNT_SETTINGS,
    MENU_ACCOUNT_SETTINGS_LORE("menu.account.settings-lore"),

    /**
     * Command
     */

    COMMAND_NOT_FOUND("command.not-found"),
    COMMAND_NO_PERMISSION("command.no-permission"),

    COMMAND_ACCOUNT_PREFIX,


    COMMAND_ADMIN_PREFIX,
    COMMAND_ADMIN_ENABLED,
    COMMAND_ADMIN_DISABLED,

    COMMAND_VANISH_PREFIX,
    COMMAND_VANISH_VISIBLE_ALL("command.vanish.visible-all"),
    COMMAND_VANISH_RANK_NOT_EXIST("command.vanish.rank-not-exist"),
    COMMAND_VANISH_RANK_HIGH("command.vanish.rank-high"),
    COMMAND_VANISH_INVISIBLE,

    COMMAND_GROUPSET_PREFIX,
    COMMAND_GROUPSET_USAGE,
    COMMAND_GROUPSET_GROUP_NOT_EXIST("command.groupset.group-not-exist"),
    COMMAND_GROUPSET_NOT_ADMIN("command.groupset.not-admin"),
    COMMAND_GROUPSET_ALREADY_IN_GROUP("command.groupset.already-in-group"),
    COMMAND_GROUPSET_CHANGE_GROUP("command.groupset.change-group"),

    COMMAND_TAG_PREFIX,
    COMMAND_TAG_AVAILABLE,
    COMMAND_TAG_SELECT,
    COMMAND_TAG_SELECTED,
    COMMAND_TAG_CURRENT,
    COMMAND_TAG_NO_ACCESS("command.tag.no-access"),
    COMMAND_TAG_NOT_FOUND("command.tag.not-found"),
    COMMAND_TAG_NOT_ENABLED("command.tag.not-enabled"),
    COMMAND_TAG_CHANGE_FAIL("command.tag.change-fail"),

    COMMAND_INVENTORYSEE_PREFIX,
    COMMAND_INVENTORYSEE_USAGE,
    COMMAND_INVENTORYSEE_SUCCESS,
    COMMAND_INVENTORYSEE_NOT_FOUND("command.inventorysee.not-found"),

    COMMAND_CHAT_PREFIX,
    COMMAND_CHAT_ALREADY_ENABLED,
    COMMAND_CHAT_ENABLED,
    COMMAND_CHAT_DISABLED,
    COMMAND_CHAT_ALREADY_DISABLED,
    COMMAND_CHAT_USAGE,
    COMMAND_CHAT_SUCCESS,
    COMMAND_CHAT_CANT_TALK,

    COMMAND_BAN_PREFIX,
    COMMAND_BAN_USAGE,
    COMMAND_BAN_SUCCESS,
    COMMAND_BAN_CANT_YOURSELF,
    COMMAND_BAN_ALREADY_BANNED,
    COMMAND_BAN_CANT_STAFF,
    COMMAND_BAN_KICK,

    COMMAND_UNBAN_PREFIX,
    COMMAND_UNBAN_USAGE,
    COMMAND_UNBAN_NOT_BANNED,
    COMMAND_UNBAN_SUCCESS,

    COMMAND_TEMPBAN_PREFIX,
    COMMAND_TEMPBAN_USAGE,
    COMMAND_TEMPBAN_INVALID_FORMAT,
    COMMAND_TEMPBAN_SUCCESS,

    COMMAND_MUTE_PREFIX,
    COMMAND_MUTE_USAGE,
    COMMAND_MUTE_SUCCESS,
    COMMAND_MUTE_CANT_YOURSELF,
    COMMAND_MUTE_ALREADY_MUTED,
    COMMAND_MUTE_CANT_STAFF,
    COMMAND_MUTE_MESSAGE,

    COMMAND_UNMUTE_PREFIX,
    COMMAND_UNMUTE_USAGE,
    COMMAND_UNMUTE_NOT_MUTED,
    COMMAND_UNMUTE_SUCCESS,

    COMMAND_TEMPMUTE_PREFIX,
    COMMAND_TEMPMUTE_USAGE,
    COMMAND_TEMPMUTE_INVALID_FORMAT,
    COMMAND_TEMPMUTE_SUCCESS,

    /**
     * Server
     */
    SERVER_WHITELIST,
    SERVER_CANT_REQUEST_OFFLINE("server.cant-request-offline"),
    SERVER_COMMAND_ONLY_FOR_PLAYER("server.command-only-for-player"),

    /**
     * Player
     */
    PLAYER_NOT_EXIST("player.not-exist"),
    PLAYER_NEVER_JOINED("player.never-joined");

    private String key;

    TranslateTag(String key) {
        this.key = key;
    }

    TranslateTag() {
    }

    private String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return key == null ? name().replace("_", ".").replace("-", ".").toLowerCase() : key;
    }
}
