package de.epsdev.plugins.MMO.data.output;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Err {
    public static void rankError(Player player){
        Out.printToPlayer(player, ChatColor.RED + "We are sorry but your rank doesn't allow to do that.");
    }

    public static void chat_mutedError(Player player){
        Out.printToPlayer(player, ChatColor.RED + "We are sorry but you can't send messages while the chat is muted.");
    }

    public static void funNotRegisteredError(){
        Out.printToBroadcast(ChatColor.RED + "We are sorry but we can't find the function. Please contact Staff if this happens multiple times.");
    }
}
