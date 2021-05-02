package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class e_PlayerHealthDecrease implements Listener {
    @EventHandler
    void onPlayerHealthDecrease(EntityDamageEvent e){
        if(e.getEntityType().equals(EntityType.PLAYER)){
            if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
                Player player = (Player) e.getEntity();
                User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

                double amount = - user.max_health * (e.getDamage() / 20);

                user.giveHealth((float) amount);
            }
            e.setCancelled(true);
        }
    }
}
