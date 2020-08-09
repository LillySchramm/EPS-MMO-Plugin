package de.epsdev.plugins.MMO.GUI;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface OnClick {
    void click(Player player, ItemStack item);
}
