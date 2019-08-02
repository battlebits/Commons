package br.com.battlebits.commons.bukkit.services.account.impl;

import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.bukkit.services.account.AccountService;
import org.apache.commons.lang.Validate;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public class AccountServiceImpl implements AccountService {

    private Set<BattleAccount> accounts;

    public AccountServiceImpl() {
        accounts = new HashSet<>();
    }

    @Override
    public Set<BattleAccount> getAccounts() {
        return accounts;
    }

    @Override
    public BattleAccount getAccount(Predicate<BattleAccount> predicate) {
        return accounts.stream().filter(predicate).findFirst().orElse(null);
    }

    @Override
    public BattleAccount getAccount(UUID uuid) {
        return getAccount(account -> account.getUniqueId().equals(uuid));
    }

    @Override
    public BattleAccount getAccount(String name) {
        return getAccount(account -> account.getName().equals(name));
    }

    @Override
    public void addAccount(BattleAccount account) {
        Validate.notNull(account, "account can't be null.");
        this.accounts.add(account);
    }

    @Override
    public void removeAccount(BattleAccount account) {
        Validate.notNull(account, "account can't be null.");
        this.accounts.remove(account);
    }
}
