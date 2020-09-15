package de.epsdev.plugins.MMO.GUI;

import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static de.epsdev.plugins.MMO.tools.TooltipLore.*;

public class Dev_house_detail {
    public Base_Gui gui;
    public House house;


    public Dev_house_detail(House house){
        this.house = house;
        this.gui = new Base_Gui(house.name);
        init();
    }

    public void init(){
        gui.addItem(Material.NAME_TAG, 1, house.name, tt_clickToUpdate(), null, 0,0);
        gui.addItem(Material.EMERALD, 1, house.costs.formatString(), tt_clickToUpdate(), null, 1,0);

        ArrayList<String> lore = new ArrayList<>();

        if(house.currentOwner_UUID.equalsIgnoreCase("0")){
            lore.add(ChatColor.GREEN + "Current owner: NO NONE");
        }else {
            UUID uuid = UUID.fromString(house.currentOwner_UUID);
            lore.add(ChatColor.GREEN + "Current owner: " + Bukkit.getOfflinePlayer(uuid).getName());
        }

        gui.addItem(Material.BED, 1, ChatColor.RED + "Revoke ownership", lore, null, 2,0);
        gui.addItem(Material.COMPASS, 1, "Spawnpossition: " + house.spawnPosition.toString(), tt_clickToUpdate(), null, 3,0);
        gui.addItem(Material.CONCRETE_POWDER, 1, "Inner Blocks", tt_clickToUpdate(), null, 4,0);
        gui.addItem(Material.BARRIER, 1, "Delete House", tt_clickToDelete(), null, 8,0);
    }

    private void show(Player player){
        gui.show(player);
    }
}
