package de.epsdev.plugins.MMO.npc.mobs;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.combat.Attackable;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mob_Manager {

    public static Map<Integer, Base_Mob> enemies = new HashMap<>();

    public static final float MAX_DISPLAY_DISTANCE = 50f;

    public static List<Attackable> getAllEnemiesInRange(Vec3f center, float range, int max){
        List<Attackable> mobs = new ArrayList<>();

        for (Attackable m : Attackable.attackables){
            if(mobs.size() == max) break;

            if(m.getPosition().distance2d(center) <= range){
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

    public static List<Attackable> getAllEnemiesInRange(Vec3f center, float range){
        return getAllEnemiesInRange(center, range,9999999);
    }

    public static void showAllMobs(Player p){
        for(Base_Mob m : enemies.values()){
            m.display(p);
        }
    }

    public static void updateDisplay(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, () -> {
            for(Player player : Bukkit.getOnlinePlayers()){
                User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

                for (Base_Mob mob : enemies.values()){
                    float distance = mob.getPos().distance3d(new Vec3f(player.getLocation()));

                    if(distance <= MAX_DISPLAY_DISTANCE && !user.loadedMOB.contains(mob.getEntity().getId())){
                        user.loadedMOB.add(mob.getEntity().getId());
                        mob.display(player);
                    }else if (distance > MAX_DISPLAY_DISTANCE && user.loadedMOB.contains(mob.getEntity().getId())){
                        user.loadedMOB.remove((Object) mob.getEntity().getId());
                        mob.remove(player);
                    }
                }

            }
        }, 0L,5L);
    }
}
