package de.epsdev.plugins.MMO.schedulers;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;

public class MAIN_UPDATE_SCHEDULER {
    public static void run() {
        BukkitScheduler scheduler = main.plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(main.plugin, () -> {
            ArrayList<House> houses = DataManager.getSoldHouses();
            for(House house: houses){
                house.increaseRenttime();
            }
        }, 0L, 20L);
    }
}
