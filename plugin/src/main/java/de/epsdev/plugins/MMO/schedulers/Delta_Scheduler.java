package de.epsdev.plugins.MMO.schedulers;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class Delta_Scheduler {

    public static List<Base_Mob> mobs = new ArrayList<>();

    public static void run() {
        BukkitScheduler scheduler = main.plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(main.plugin, () -> {
            DataManager.delta.update();

            for (Base_Mob m : mobs){
                m.updatePos();
            }


        }, 0L, 1L);
    }
}
