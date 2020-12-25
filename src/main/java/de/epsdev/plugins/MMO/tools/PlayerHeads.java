package de.epsdev.plugins.MMO.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerHeads {
    public static ItemStack getHead(String uuid){
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner(PlayerNames.playerNameByUUID(uuid));
        meta.setDisplayName(PlayerNames.playerNameByUUID(uuid));
        head.setItemMeta(meta);

        return head;
    }
}
