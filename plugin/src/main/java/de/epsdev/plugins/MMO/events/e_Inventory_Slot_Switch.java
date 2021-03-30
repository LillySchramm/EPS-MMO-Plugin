package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.particles.EF_Particle_Ring;
import de.epsdev.plugins.MMO.particles.ParticleConfig;
import de.epsdev.plugins.MMO.tools.D_RGB;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class e_Inventory_Slot_Switch implements Listener {

    @EventHandler
    void onInventorySwitch(PlayerItemHeldEvent e){
        Player player = e.getPlayer();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        new EF_Particle_Ring(new ParticleConfig(Particle.REDSTONE, new D_RGB(200,100,100)), 1, 30).display(DataManager.onlineUsers.get(player.getUniqueId().toString()).getLOS_Block());

        if(player.getGameMode() == GameMode.SURVIVAL){
            if (user.currentCharacter != null){
                int slot = e.getNewSlot();
                if (slot != 4){
                    if (slot <= 4) slot++;
                    user.currentCharacter.handleSkill(player, slot);
                }
            }
            e.setCancelled(true);
        }

    }

}
