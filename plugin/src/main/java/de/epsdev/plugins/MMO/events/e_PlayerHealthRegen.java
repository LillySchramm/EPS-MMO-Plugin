package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.output.Out;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class e_PlayerHealthRegen implements Listener {
    @EventHandler
    void onPlayerHealthGain(EntityRegainHealthEvent e){
        if(e.getEntityType().equals(EntityType.PLAYER)){
            e.setCancelled(true);
        }
    }
}
