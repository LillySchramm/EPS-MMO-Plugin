package de.epsdev.plugins.MMO.tools;

import org.bukkit.ChatColor;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class TooltipLore {

    public static ArrayList<String> toTT(String string){
        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.WHITE + string);

        return lore;
    }

    public static ArrayList<String> tt_clickToUpdate(){
        return toTT(ChatColor.GREEN + "Click to update");
    }

    public static ArrayList<String> tt_clickToDelete(){
        return toTT(ChatColor.GREEN + "Click to delete");
    }
}
