package de.epsdev.plugins.MMO.GUI.dev;

import de.epsdev.plugins.MMO.GUI.base.Base_Gui;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.regions.Region;
import de.epsdev.plugins.MMO.data.regions.cites.City;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class City_GUI {
    public List<Base_Gui> sites = new ArrayList<>();
    private Region region;

    public City_GUI(Region region){
        this.region = region;
        init();
    }

    public final void show(Player player){
        sites.get(0).show(player);
    }

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

    private OnClick backtoregionsmenu = (player, item, inventory) -> {
      Regions_GUI.sites.get(0).show(player);
    };

    private OnClick showCityDetails = (player, item, inventory) -> {
        City city = DataManager.getCityByName(item.getItemMeta().getDisplayName());
        city.gui.gui.show(player);
    };

    private void createNewSite(){
        Base_Gui gui = new Base_Gui("City Site: " + sites.size() + 1);
        gui.rows = 6;
        gui.addItem(Material.ARROW, 1, "-",new ArrayList<>(), changeSite, 6,5);
        gui.addItem(Material.SPECTRAL_ARROW, 1, "+",new ArrayList<>(), changeSite, 7,5);
        gui.addItem(Material.BARRIER, 1, "Back",new ArrayList<>(), backtoregionsmenu, 8,5);
        sites.add(gui);
    }

    private void init(){
        sites = new ArrayList<>();

        createNewSite();

        int x = 0;
        int y = 0;

        for(City city : region.cities){

            sites.get(sites.size() - 1).addItem(Material.IRON_DOOR, 1, city.name, new ArrayList<>(), showCityDetails,x,y);

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

}
