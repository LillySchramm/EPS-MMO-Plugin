package de.epsdev.plugins.MMO.npc.mobs.spawns;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class SpawnGroup {

    private List<SpawnPoint> spawnPoints;
    private List<I_SPAWN> possibleSpawns;

    private int maxSpawns = 1;
    private long respawn_ticks;

    public interface I_SPAWN{
        UUID spawn(Vec3f loc);
    }

    //Bug:  When a to fast respawn_ticks (<10~15 Ticks) is used, it sometimes occurs that more than the desired number of
    //      mobs spawn due to the time that it takes to initialize and spawn one of them in.

    public SpawnGroup(List<SpawnPoint> spawnPoints, List<I_SPAWN> i_spawns, int maxSpawns, long respawn_ticks){
        this.spawnPoints = spawnPoints;

        this.possibleSpawns = i_spawns;

        this.maxSpawns = maxSpawns;
        this.respawn_ticks = respawn_ticks;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, this::spawn_tick, 40L, this.respawn_ticks);
    }

    private void spawn_tick(){
        if(getUsedSpawns().size() < maxSpawns && getOpenSpawns().size() != 0){
            List<Integer> openSpawns = getOpenSpawns();

            Out.printToConsole(getUsedSpawns().size());

            int spawn_index = ThreadLocalRandom.current().nextInt(0, openSpawns.size());
            int ispawn_index = ThreadLocalRandom.current().nextInt(0, possibleSpawns.size());

            // Hotfix for the bug mentioned in line 27-28
            spawnPoints.get(spawn_index).inUse = true;

            UUID uuid = possibleSpawns.get(ispawn_index).spawn(
                    spawnPoints.get(spawn_index).point
            );

            spawnPoints.get(spawn_index).start_track(uuid);
        }
    }

    private List<Integer> getOpenSpawns(){
        int i = 0;
        List<Integer> ints = new ArrayList<>();

        for(SpawnPoint s : spawnPoints){
            if(!s.inUse){
                ints.add(i);
            }
            i++;
        }

        return ints;
    }

    private List<SpawnPoint> getUsedSpawns(){
        List<SpawnPoint> points = new ArrayList<>();

        for(SpawnPoint sp : spawnPoints){
            if(sp.inUse){
                points.add(sp);
            }
        }

        return points;
    }

}
