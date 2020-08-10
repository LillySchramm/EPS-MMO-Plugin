package de.epsdev.plugins.MMO.data.output;

import de.epsdev.plugins.MMO.tools.Vec3i;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Out {
    public static void printToConsole(String msg){
        System.out.println("[EPS-MMO] " + msg);
    }

    public static void printToConsole(int msg){
        System.out.println("[EPS-MMO] " + msg);
    }

    public static void printToConsole(float msg){
        System.out.println("[EPS-MMO] " + msg);
    }

    public static void printToConsole(String[] msg){
        System.out.println("[EPS-MMO] " + msg.length);
        for(String s : msg){
            System.out.println("[EPS-MMO] " + s);
        }
    }

    public static void printToPlayer(Player player, String msg){
        player.sendMessage("[EPS-MMO] " + msg);
    }

    public static void printToBroadcast(String msg){
        Bukkit.broadcastMessage(msg);
    }

    public static void printToShield(String msg, Vec3i pos){

    }
}
