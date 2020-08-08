package de.epsdev.plugins.MMO.ranks;

import org.bukkit.ChatColor;

public class Ranks {
    public static Rank Admin = new Rank("admin", ChatColor.DARK_RED + "Admin" ,new boolean[]{
            true, //CanBuild
            true, //CanHandleMoney
            true, //CanChangeRanks
            true, //CanMuteChat
            true  //CanSpeakWhenChatMuted
    });
    public static Rank Builder = new Rank("builder", ChatColor.BLUE + "Builder", new boolean[]{
            true, //CanBuild
            false,//CanHandleMoney
            false,//CanChangeRanks
            false,//CanMuteChat
            true  //CanSpeakWhenChatMuted
    });
    public static Rank Player = new Rank("player", "Player",new  boolean[]{
            false, //CanBuild
            false, //CanHandleMoney
            false, //CanChangeRanks
            false, //CanMuteChat
            false  //CanSpeakWhenChatMuted
    });

    public static Rank Mod = new Rank("mod", ChatColor.DARK_BLUE +"Mod",new  boolean[]{
            false, //CanBuild
            false, //CanHandleMoney
            false, //CanChangeRanks
            true,  //CanMuteChat
            true   //CanSpeakWhenChatMuted
    });

    public static Rank getRank(String name){
        switch (name){
            default:
                return null;
            case "player":
                return Player;
            case "admin":
                return Admin;
            case "builder":
                return Builder;
        }
    }

}
