package br.com.battlebits.commons.clan.invites;

import br.com.battlebits.commons.clan.Clan;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class ClanInvites {

    private UUID author;
    private Clan clan;
    private UUID target;
    private long date;

    public boolean isValid() {
        return this.date + 8640 * 1000 > System.currentTimeMillis();
    }

}
