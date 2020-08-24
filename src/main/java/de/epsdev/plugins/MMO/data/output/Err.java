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

    public static void notEnoughArgumentsError(Player player){
        Out.printToPlayer(player, ChatColor.RED + "This commands needs more arguments.");
    }

    public static void regionAlreadyExistsError(Player player){
        Out.printToPlayer(player, ChatColor.RED + "A region with this name already exists.");
    }

    public static void cityAlreadyExistsError(Player player){
        Out.printToPlayer(player, ChatColor.RED + "A city with this name already exists.");
    }

    public static void regionNotFoundError(Player player){
        Out.printToPlayer(player, ChatColor.RED + "A region with this name couldn't be found.");
    }

    public static void cityNotFoundError(Player player){
        Out.printToPlayer(player, ChatColor.RED + "A city with this name couldn't be found.");
    }

    public static void notANumberError(Player player){
        Out.printToPlayer(player, ChatColor.RED + "This isn't a number.");
    }

    public static void correctUsage(Player player, String[] args){

        String s = "";

        for (String string : args){
            s += "<" + string + "> ";
        }

        Out.printToPlayer(player, ChatColor.RED + "The following arguments are required: " + s);
    }
}
