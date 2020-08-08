package de.epsdev.plugins.MMO.data.output;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Err {
    public static void rankError(Player player){
        Out.printToPlayer(player, ChatColor.RED + "We are sorry but your rank doesn't allow to do that.");
    }
}
