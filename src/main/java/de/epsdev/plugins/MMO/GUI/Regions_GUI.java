package de.epsdev.plugins.MMO.GUI;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.regions.Region;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Regions_GUI {
    public static List<Base_Gui> sites = new ArrayList<>();

    public static void init(){

        createNewSite();

        int x = 0;
        int y = 0;

        for(Region region : DataManager.regions){

            sites.get(sites.size() - 1).addItem(Material.WOOD, 1, region.name, new ArrayList<>(), null,x,y);

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

    private static void createNewSite(){
        Base_Gui gui = new Base_Gui("Regions");
        gui.rows = 6;
        sites.add(gui);
    }

}
