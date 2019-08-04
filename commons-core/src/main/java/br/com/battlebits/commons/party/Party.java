package br.com.battlebits.commons.party;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class Party {

    public static final String PREFIX = "§6§lPARTY §r";

    @NonNull
    private UUID owner;

    public boolean isOwner(UUID uuid) {
        return uuid != null && owner.equals(uuid);
    }

    private Set<UUID> members = new HashSet<>();
    //private Set<UUID> promoted = new HashSet<>();

    public String getID() {
        return "party:" + owner.toString();
    }

    /*/public boolean isPromoted(UUID uuid) {
        return uuid.equals(owner) || promoted.contains(uuid);
    }*/

    public boolean contains(UUID uuid) {
        return uuid.equals(owner) || members.contains(uuid);
    }

    public void removeMember(UUID member) {
        members.remove(member);
    }

    public void addMember(UUID member) {
        members.add(member);
    }

    public void sendMessage(String message) {
        sendMessage(false, false, message);
    }

    public void sendMessage(String id, String[]... replacement) {
        sendMessage(true, true, id, replacement);
    }

    public abstract void init();

    public abstract void onOwnerJoin();

    public abstract void onOwnerLeave();

    public abstract int getOnlineCount();

    public abstract void onMemberJoin(UUID member);

    public abstract void onMemberLeave(UUID member);

    public abstract void sendMessage(boolean prefix, boolean translate, String id, String[]... replacement);
}