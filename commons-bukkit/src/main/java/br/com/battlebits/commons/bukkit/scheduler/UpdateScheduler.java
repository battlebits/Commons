package br.com.battlebits.commons.bukkit.scheduler;


import br.com.battlebits.commons.bukkit.event.update.UpdateEvent;
import br.com.battlebits.commons.bukkit.event.update.UpdateEvent.UpdateType;
import org.bukkit.Bukkit;

public class UpdateScheduler implements Runnable {

    private long currentTick;

    @Override
    public void run() {
        currentTick++;
        Bukkit.getPluginManager().callEvent(new UpdateEvent(UpdateType.TICK, currentTick));

        if (currentTick % 20 == 0) {
            Bukkit.getPluginManager().callEvent(new UpdateEvent(UpdateType.SECOND, currentTick));
        }

        if (currentTick % 1200 == 0) {
            Bukkit.getPluginManager().callEvent(new UpdateEvent(UpdateType.MINUTE, currentTick));
        }
    }
}
