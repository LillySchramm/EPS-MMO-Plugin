package de.epsdev.plugins.MMO.npc.mobs;

import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.tools.Vec3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mob_Manager {

    public static Map<Integer, Base_Mob> enemies = new HashMap<>();
    
    public static List<Base_Mob> getAllEnemiesInRange(Vec3f center, float range, int max){
        List<Base_Mob> mobs = new ArrayList<>();

        for (Base_Mob m : enemies.values()){
            if(mobs.size() == max) break;

            if(m.getPos().distance2d(center) <= range){
                mobs.add(m);
            }
        }

        return mobs;
    }

    public static List<Base_Mob> getAllEnemiesInRange(Vec3f center, float range){
        return getAllEnemiesInRange(center, range,9999999);
    }
}
