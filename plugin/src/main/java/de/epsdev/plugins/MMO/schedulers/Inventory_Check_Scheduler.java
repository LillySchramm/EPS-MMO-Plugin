package de.epsdev.plugins.MMO.schedulers;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.particles.EF_Particle_Ring;
import de.epsdev.plugins.MMO.particles.ParticleConfig;
import de.epsdev.plugins.MMO.particles.Particle_Effect;
import de.epsdev.plugins.MMO.tools.D_RGB;
import de.epsdev.plugins.MMO.tools.Math;
import de.epsdev.plugins.MMO.tools.Vec3i;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;

public class Inventory_Check_Scheduler {
    public static void run() {
        BukkitScheduler scheduler = main.plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(main.plugin, () -> {

            for (Player player : Bukkit.getOnlinePlayers()){

                if (player.getGameMode() != GameMode.CREATIVE){
                    User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

                    if(user.playerInventory != null){
                        if(!Math.compareInventory(user.playerInventory, player.getInventory())){
                            player.getInventory().clear();
                            player.getInventory().setContents(user.playerInventory);
                        }
                    }
                }

                ParticleConfig config = new ParticleConfig(Particle.SPELL, new D_RGB(70,0,90));
                ParticleConfig config1 = new ParticleConfig(Particle.REDSTONE, new D_RGB(10,10,100));

                EF_Particle_Ring ef_particle_ring =     new EF_Particle_Ring(config, 16, 160);
                EF_Particle_Ring ef_particle_ring_2 =   new EF_Particle_Ring(config1,  8,80);
                EF_Particle_Ring ef_particle_ring_3 =   new EF_Particle_Ring(config,  4,40);
                EF_Particle_Ring ef_particle_ring_4 =   new EF_Particle_Ring(config1,  2,20);
                EF_Particle_Ring ef_particle_ring_5 =   new EF_Particle_Ring(config,  0,10);
                ef_particle_ring.display(player.getLocation());
                ef_particle_ring_2.display(player.getLocation());
                ef_particle_ring_3.display(player.getLocation());
                ef_particle_ring_4.display(player.getLocation());
                ef_particle_ring_5.display(player.getLocation());


            }

        }, 0L, 10L);
    }
}
