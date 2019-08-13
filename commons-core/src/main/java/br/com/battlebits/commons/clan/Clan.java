package br.com.battlebits.commons.clan;

import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.clan.exceptions.MemberCantPromoteException;
import br.com.battlebits.commons.clan.exceptions.MemberDoesntExistsException;
import br.com.battlebits.commons.clan.member.ClanMember;
import br.com.battlebits.commons.clan.member.MemberRole;
import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
public class Clan {

    private UUID uniqueId;
    private UUID owner;
    private String name;
    private String tag;
    private int xp;
    private Set<ClanMember> clanMembers;

    public Clan(BattleAccount owner, String name, String tag) {
        this.uniqueId = UUID.randomUUID();
        this.owner = owner.getUniqueId();
        this.name = name;
        this.tag = tag;
        this.xp = 0;
        this.clanMembers = new HashSet<>();

        this.clanMembers.add(ClanMember.builder()
                .uniqueId(this.owner)
                .clan(this)
                .role(MemberRole.OWNER)
                .joinDate(System.currentTimeMillis())
                .build());
    }

    private Set<ClanMember> searchMembers(Predicate<ClanMember> predicate) {
        return this.clanMembers.stream().filter(predicate).collect(Collectors.toSet());
    }

    private ClanMember searchMember(Predicate<ClanMember> predicate) {
        return this.clanMembers.stream().filter(predicate).findFirst().orElse(null);
    }

    public ClanMember getOwner() {
        return searchMember(clanMember -> clanMember.getRole() == MemberRole.OWNER);
    }

    public Set<ClanMember> getAdministrators() {
        return searchMembers(clanMember -> clanMember.getRole() == MemberRole.ADMINISTRATOR);
    }

    public Set<ClanMember> getMembers() {
        return searchMembers(clanMember -> clanMember.getRole() == MemberRole.MEMBER);
    }

    public ClanMember getMember(UUID uniqueId) {
        return searchMember(clanMember -> clanMember.getUniqueId().equals(uniqueId));
    }

    public boolean isOwner(UUID uniqueId) {
        return searchMember(clanMember -> clanMember.getUniqueId().equals(uniqueId) && clanMember.hasRole(MemberRole.OWNER)) != null;
    }

    public boolean isAdministrator(UUID uniqueId) {
        return searchMember(clanMember -> clanMember.getUniqueId().equals(uniqueId) && clanMember.hasRole(MemberRole.ADMINISTRATOR)) != null;
    }

    public boolean isMember(UUID uniqueId) {
        return searchMember(clanMember -> clanMember.getUniqueId().equals(uniqueId) && clanMember.hasRole(MemberRole.MEMBER)) != null;
    }

    public void addMember(ClanMember member) {
        Objects.requireNonNull(member, "member can't be null.");
        this.clanMembers.add(member);
    }

    public void removeMember(ClanMember member) {
        Objects.requireNonNull(member, "member can't be null.");
        this.clanMembers.remove(member);
    }

    public void promote(UUID uniqueId) throws MemberDoesntExistsException, MemberCantPromoteException {
        final ClanMember clanMember = getMember(uniqueId);
        if (clanMember == null) {
            throw new MemberDoesntExistsException();
        }
        if(clanMember.getRole() != MemberRole.MEMBER) {
            throw new MemberCantPromoteException();
        }
        clanMember.setRole(MemberRole.ADMINISTRATOR);
    }

    public void demote(UUID uniqueId) throws MemberDoesntExistsException, MemberCantPromoteException {
        final ClanMember clanMember = getMember(uniqueId);
        if (clanMember == null) {
            throw new MemberDoesntExistsException();
        }
        if(clanMember.getRole() != MemberRole.ADMINISTRATOR) {
            throw new MemberCantPromoteException();
        }
        clanMember.setRole(MemberRole.MEMBER);
    }
}
