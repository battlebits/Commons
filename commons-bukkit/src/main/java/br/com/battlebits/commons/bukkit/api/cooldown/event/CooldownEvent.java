package br.com.battlebits.commons.bukkit.api.cooldown.event;

import br.com.battlebits.commons.bukkit.api.cooldown.types.Cooldown;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@RequiredArgsConstructor
public abstract class CooldownEvent extends Event {

    @Getter
    @NonNull
    private Player player;

    @Getter
    @NonNull
    private Cooldown cooldown;
}
