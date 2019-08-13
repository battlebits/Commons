package br.com.battlebits.commons.clan.member;

import br.com.battlebits.commons.clan.Clan;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class ClanMember {

    @NonNull
    private UUID uniqueId;

    private Clan clan;
    private MemberRole role;
    private long joinDate;

    public boolean hasClan() {
        return this.clan != null;
    }

    public boolean hasRole(MemberRole role) {
        return hasClan() && this.role.ordinal() >= role.ordinal();
    }

    public boolean isOwner() {
        return hasRole(MemberRole.OWNER);
    }

    public boolean isAdministrator() {
        return hasRole(MemberRole.ADMINISTRATOR);
    }

    public boolean isMember() {
        return hasRole(MemberRole.MEMBER);
    }
}
