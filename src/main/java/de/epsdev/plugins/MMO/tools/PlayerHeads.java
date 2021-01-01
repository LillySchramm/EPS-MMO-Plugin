package de.epsdev.plugins.MMO.tools;

import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class PlayerHeads {
    public static ItemStack getHeadByUUID(String uuid){
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner(PlayerNames.playerNameByUUID(uuid));
        meta.setDisplayName(PlayerNames.playerNameByUUID(uuid));
        head.setItemMeta(meta);

        return head;
    }

    public static ItemStack getHeadByName(String name){

        String uuid = PlayerNames.getUUID(name);

        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner(PlayerNames.playerNameByUUID(uuid));
        meta.setDisplayName(PlayerNames.playerNameByUUID(uuid));
        head.setItemMeta(meta);

        return head;
    }


}
