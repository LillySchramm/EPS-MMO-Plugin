package de.epsdev.plugins.MMO.schedulers;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.mysql.DatabaseManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;

public class MAIN_UPDATE_SCHEDULER {
    public static void run() {
        BukkitScheduler scheduler = main.plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(main.plugin, new Runnable() {
            @Override
            public void run() {
                ArrayList<House> houses = DataManager.getSoldHouses();

                for(House house: houses){
                    User user = DataManager.onlineUsers.get(house.currentOwner_UUID);
                    if(user != null){
                        Out.printToConsole("yay");
                    }else{
                        Out.printToConsole("nay");
                    }
                }
            }
        }, 0L, 20L);
    }
}
