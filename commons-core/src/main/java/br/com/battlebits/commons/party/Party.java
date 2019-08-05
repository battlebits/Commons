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

    public void sendMessage(String tag, Object... objects) {
        sendMessage(true, true, tag, objects);
    }

    public abstract void init();

    public abstract void onOwnerJoin();

    public abstract void onOwnerLeave();

    public abstract int getOnlineCount();

    public abstract void onMemberJoin(UUID member);

    public abstract void onMemberLeave(UUID member);

    public abstract void sendMessage(boolean prefix, boolean translate, String tag, Object... objects);
}