package de.epsdev.plugins.MMO.schedulers;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.npc.eNpc.eNpc;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;

public class Static_Effect_Scheduler {

    private static HashMap<Integer, eNpc> effects = new HashMap<>();

    public static void register(eNpc e){
        effects.put(e.eNpc_id, e);
    }

    public static void unload(eNpc e){
        effects.remove(e.eNpc_id);
    }

    public static void run() {
        BukkitScheduler scheduler = main.plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(main.plugin, () -> {
            effects.forEach((integer, eNpc) -> {
                eNpc.display();
            });
        }, 0L, 8L);
    }
}
