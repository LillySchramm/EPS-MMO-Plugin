package de.epsdev.plugins.MMO.GUI.dev;

import de.epsdev.plugins.MMO.GUI.Base_Gui;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.Region;
import de.epsdev.plugins.MMO.data.regions.cites.City;
import de.epsdev.plugins.MMO.events.OnChat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class City_Detail_GUI {
    public City city;
    public Base_Gui gui;

    public Dev_Houses_Gui houses_gui;

    public final OnClick deleteCity = (player, item, inventory) -> {
        city.delete();
        Out.printToPlayer(player, ChatColor.GREEN + "Deleted " + city.name);
        player.closeInventory();
    };

    private final OnChat renameRegionDialog = e -> {
        String message = e.getMessage();
        Player player = e.getPlayer();
        if (!message.equalsIgnoreCase("null")){
            if(!DataManager.isCityExisting(message)){

                String oldName = city.name;

                city.name = message;
                city.save();

                Out.printToPlayer(player,ChatColor.DARK_GREEN + "Renamed '" +
                        oldName +
                        "' to '" + message + "'!");
            }else {
                Err.cityAlreadyExistsError(player);
            }
        }else {
            Out.printToPlayer(player, ChatColor.DARK_GREEN + "Renaming process stopped.");
        }
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.onChat = null;
    };

    private final OnClick showHouses = (player, item, inventory) -> {
        this.houses_gui.show(player);
    };

    private final OnClick renameCity = ((player, item, inventory) -> {
        player.closeInventory();
        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Please type the new name. If you don't want to rename this city type 'null'.");
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.onChat = renameRegionDialog;
    });
    public City_Detail_GUI(City city, Region region){
        this.gui = new Base_Gui("City: " + city.name);
        this.city = city;

        gui.rows = 1;

        gui.addItem(Material.NAME_TAG, 1, "Name: " + city.name, new ArrayList<>(),renameCity,0,0);
        gui.addItem(Material.WOOD_DOOR, 1, "Houses", new ArrayList<>(),showHouses,1,0);
        gui.addItem(Material.BARRIER,1, ChatColor.RED + "Delete City",new ArrayList<>(), deleteCity, 7,0);

        this.houses_gui = new Dev_Houses_Gui(city);

    }

    public void load_houses(){
        this.houses_gui = new Dev_Houses_Gui(city);
    }

    public void show(Player player){
        gui.show(player);
    }

}
