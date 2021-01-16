package de.epsdev.plugins.MMO.GUI.dev;

import de.epsdev.plugins.MMO.GUI.Base_Gui;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.regions.cites.City;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Dev_Houses_Gui {
    public List<Base_Gui> sites = new ArrayList<>();
    public City city;


    public Dev_Houses_Gui(City city){
        this.city = city;
        init();
    }

    public void init(){

        sites = new ArrayList<>();

        createNewSite();

        int x = 0;
        int y = 0;

        Out.printToConsole("Count houses: " + city.houses.size());

        for(House house : city.houses){

            sites.get(sites.size() - 1).addItem(Material.IRON_DOOR, 1, house.name, new ArrayList<>(), showHouseGui,x,y);

            x++;
            if(x >= 8){
                x = 0;
                y++;
            }
            if(y >= 5 && x >= 6){
                createNewSite();
            }
        }


    }

    private final OnClick showHouseGui = (player, item, inventory) -> {
        String houseName = item.getItemMeta().getDisplayName();

        this.city.getHouseByName(houseName).detail_gui.gui.show(player);
    };

    private final OnClick changeSite = (player, item, inventory) -> {
        String itemname = item.getItemMeta().getDisplayName();
        int actSite = Integer.parseInt(inventory.getTitle().split(": ")[1]) - 1;

        if(itemname.equalsIgnoreCase("+")){
            if(!(actSite == 0)){
                sites.get(actSite - 1).show(player);
            }
        }else {
            if (!(actSite == sites.size() - 1)){
                sites.get(actSite + 1).show(player);
            }
        }

    };


    private final OnClick backtocitymenu = (player, item, inventory) -> {
      city.gui.gui.show(player);
    };

    private void createNewSite(){
        Base_Gui gui = new Base_Gui("Houses Site: " + sites.size() + 1);
        gui.rows = 6;
        gui.addItem(Material.ARROW, 1, "-",new ArrayList<>(), changeSite, 6,5);
        gui.addItem(Material.SPECTRAL_ARROW, 1, "+",new ArrayList<>(), changeSite, 7,5);
        gui.addItem(Material.BARRIER, 1, "Back",new ArrayList<>(), backtocitymenu, 8,5);
        sites.add(gui);
    }

    public void show(Player player){
        sites.get(0).show(player);
    }



}
