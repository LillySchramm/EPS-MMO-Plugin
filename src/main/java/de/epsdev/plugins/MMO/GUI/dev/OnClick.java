package de.epsdev.plugins.MMO.GUI.dev;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface OnClick {
    void click(Player player, ItemStack item, Inventory inventory);
}
