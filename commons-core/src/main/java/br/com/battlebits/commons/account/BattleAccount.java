package br.com.battlebits.commons.account;

import lombok.Data;

import java.util.UUID;

@Data
public final class BattleAccount {
    private String name;
    private String fakeName = "";
    private UUID uniqueId;
}
