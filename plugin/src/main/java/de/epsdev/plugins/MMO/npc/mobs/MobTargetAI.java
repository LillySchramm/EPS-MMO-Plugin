package de.epsdev.plugins.MMO.npc.mobs;

import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class MobTargetAI {
    private String currentTarget = "";

    public Vec3f spawn = new Vec3f(0,0,0);

    private final float detection_radius;
    private final float keepAliveRadius;
    private final float maxDistanceFromSpawn;

    public MobTargetAI(float detection_radius, float keepAliveRadius, float maxDistanceFromSpawn){
        this.detection_radius = detection_radius;
        this.keepAliveRadius = keepAliveRadius;
        this.maxDistanceFromSpawn = maxDistanceFromSpawn;
    }

    public Vec3f getTarget(){
        if(this.currentTarget.equals("")) return spawn;

        return new Vec3f(Bukkit.getPlayer(UUID.fromString(currentTarget)).getLocation());
    }

    public void update(Vec3f curPos){
        if(this.currentTarget.equals("")){
            //Get all players in reach of mob spawn
            List<Player> players = Mob_Manager.getPlayersInRange(detection_radius, spawn);

            //Get players in reach of mob
            players = Mob_Manager.getPlayersInRange(detection_radius, curPos, players);

            //Get closest player
            Player player =  Mob_Manager.getClosestPlayer(players, curPos);
            if(player == null) return;

            this.currentTarget = player.getUniqueId().toString();

        }else {

            Player curTarget = Bukkit.getPlayer(UUID.fromString(currentTarget));

            //If player disconnected
            if(curTarget == null){
                this.currentTarget = "";
                update(curPos);
                return;
            }

            float distance_to_current_target = new Vec3f(curTarget.getLocation()).distance3d(curPos);

            //If player to far away
            if(distance_to_current_target > keepAliveRadius){
                this.currentTarget = "";
                update(curPos);
                return;
            }

            float distance_to_spawn = spawn.distance3d(curPos);

            //If mob to far away from spawn
            if(distance_to_spawn > maxDistanceFromSpawn){
                this.currentTarget = "";
                update(curPos);
            }
        }
    }
}
