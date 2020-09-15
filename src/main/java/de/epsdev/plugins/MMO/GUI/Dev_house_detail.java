package de.epsdev.plugins.MMO.GUI;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.events.OnChat;
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

    private final OnChat changeName = e -> {
        Player player = e.getPlayer();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        String msg = e.getMessage();

        if(msg.equalsIgnoreCase("null") || house.city.getHouseByName(msg) != null){
            Err.cityAlreadyExistsError(player);
        }else {
            house.name = msg;
            house.save(true);
        }

        user.onChat = null;
    };

    private final OnClick changeNameClick = (player, item, inventory) -> {
        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Please type the new name for the house.");
        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Type 'null' to not change.");

        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.onChat = changeName;

        player.closeInventory();
    };

    public void init(){
        gui.addItem(Material.NAME_TAG, 1, house.name, tt_clickToUpdate(), changeNameClick, 0,0);
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
