package br.com.battlebits.commons.bukkit.api.menu;

import org.bukkit.entity.Player;

/**
 * Created by luanpereira on 06/03/17.
 */
public interface MenuUpdateHandler
{
    void onUpdate(Player player, MenuInventory menu);
}
