package de.epsdev.plugins.MMO.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class e_FoodDepleteEvent implements Listener {
    @EventHandler
    void FoodDeplete(FoodLevelChangeEvent e){
        e.setCancelled(true);
        e.setFoodLevel(20);
    }
}
