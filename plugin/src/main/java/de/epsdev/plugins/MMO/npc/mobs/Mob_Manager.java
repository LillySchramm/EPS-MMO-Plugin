package de.epsdev.plugins.MMO.npc.mobs;

import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


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


    public static List<Player> getPlayersInRange(float radius, Vec3f center, List<Player> players_list){
        List<Player> players = new ArrayList<>();

        for (Player player : players_list){
            if(new Vec3f(player.getLocation()).distance3d(center) <= radius) players.add(player);
        }

        return players;
    }

    public static Player getClosestPlayer(List<Player> players, Vec3f center){
        Player player = null;
        float cur_dist = 99999999999f;

        for (Player _player : players){
            float dist = new Vec3f(_player.getLocation()).distance3d(center);
            if(dist <= cur_dist){
                cur_dist = dist;
                player = _player;
            }
        }

        return player;
    }

    public static List<Player> getPlayersInRange(float radius, Vec3f center){
        return getPlayersInRange(radius, center, new ArrayList<>(Bukkit.getOnlinePlayers()));
    }

    public static List<Base_Mob> getAllEnemiesInRange(Vec3f center, float range){
        return getAllEnemiesInRange(center, range,9999999);
    }

    public static void showAllMobs(Player p){
        for(Base_Mob m : enemies.values()){
            m.display(p);
        }
    }
}
