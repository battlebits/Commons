package br.com.battlebits.commons.backend.mongodb.pojo;

import lombok.Data;

@Data
public class ModelAccountConfiguration {
    private boolean ignoreAll;
    private boolean tellEnabled;
    private boolean canPlaySound;
    private boolean alertsEnabled;
    private boolean staffChatEnabled;
    private boolean clanChatEnabled;
    private boolean partyInvites;
    private boolean rankedEnabled;
}
