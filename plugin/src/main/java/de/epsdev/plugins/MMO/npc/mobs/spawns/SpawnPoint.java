package de.epsdev.plugins.MMO.npc.mobs.spawns;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.combat.Attackable;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.npc.mobs.Mob_Manager;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.Bukkit;

import java.util.UUID;

public class SpawnPoint {
    public static final long TRACKING_INTERVAL = 5L;

    public final Vec3f point;
    public boolean inUse = false;

    private int trackScheduler;
    private UUID tracked;

    public SpawnPoint(Vec3f point){
        this.point = point;
    }

    public void start_track(UUID uuid){
        this.tracked = uuid;
        this.inUse = true;

        this.trackScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, this::check, 0L, TRACKING_INTERVAL);
    }

    private void check(){
        if(!Attackable.attackables.containsKey(this.tracked)){
            stop_track();
        }
    }

    private void stop_track(){
        this.inUse = false;
        Bukkit.getScheduler().cancelTask(trackScheduler);
    }
}
