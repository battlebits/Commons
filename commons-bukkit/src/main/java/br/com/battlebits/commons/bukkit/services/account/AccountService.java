package br.com.battlebits.commons.bukkit.services.account;

import br.com.battlebits.commons.account.BattleAccount;

import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public interface AccountService {

    Set<BattleAccount> getAccounts();

    BattleAccount getAccount(Predicate<BattleAccount> predicate);
    BattleAccount getAccount(UUID uuid);
    BattleAccount getAccount(String name);

    void addAccount(BattleAccount account);
    void removeAccount(BattleAccount account);
}
