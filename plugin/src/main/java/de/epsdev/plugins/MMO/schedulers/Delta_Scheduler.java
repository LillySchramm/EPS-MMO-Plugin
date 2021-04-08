package de.epsdev.plugins.MMO.schedulers;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import de.epsdev.plugins.MMO.particles.EF_Single_Particle;
import de.epsdev.plugins.MMO.particles.ParticleConfig;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class Delta_Scheduler {

    public static List<Base_Mob> mobs = new ArrayList<>();

    //TEMP
    private static Vec3f tmp_pos = new Vec3f(-650, 78, 724);

    public static void run() {
        BukkitScheduler scheduler = main.plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(main.plugin, () -> {
            DataManager.delta.update();

            for (Base_Mob m : mobs){
                m.updatePos();
            }

            tmp_pos.rotateAroundPointX(new Vec3f(-648, 80, 724), 5);
            new EF_Single_Particle(new ParticleConfig(Particle.REDSTONE)).display(tmp_pos.toLocation());

        }, 0L, 1L);
    }
}
