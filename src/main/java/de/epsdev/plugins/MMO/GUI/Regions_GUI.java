package de.epsdev.plugins.MMO.GUI;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.Region;
import de.epsdev.plugins.MMO.events.OnChat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;


import java.util.ArrayList;
import java.util.List;

public class Regions_GUI {
    public static List<Base_Gui> sites = new ArrayList<>();



    public static void init(){
        sites = new ArrayList<>();

        createNewSite();

        int x = 0;
        int y = 0;

        for(Region region : DataManager.regions){

            sites.get(sites.size() - 1).addItem(Material.WOOD, 1, region.name, new ArrayList<>(), showRegionDetails,x,y);

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
        Base_Gui gui = new Base_Gui("Regions Site: " + sites.size() + 1);
        gui.rows = 6;
        gui.addItem(Material.ARROW, 1, "-",new ArrayList<>(), changeSite, 7,5);
        gui.addItem(Material.SPECTRAL_ARROW, 1, "+",new ArrayList<>(), changeSite, 8,5);
        sites.add(gui);
    }

    private static final OnChat renameRegionDialog = e -> {
        String message = e.getMessage();
        Player player = e.getPlayer();
        if (!message.equalsIgnoreCase("null")){
            if(!DataManager.isRegionAlreadyExisting(message)){
                Region region = DataManager.getRegionByName(DataManager.onlineUsers.get(player.getUniqueId().toString()).temp_strings.get(0));
                region.name = message;
                region.save();

                DataManager.regions = new ArrayList<>();
                DataManager.loadAllRegions();
                Regions_GUI.init();
                  Out.printToPlayer(player,ChatColor.DARK_GREEN + "Renamed '" +
                        DataManager.onlineUsers.get(player.getUniqueId().toString()).temp_strings.get(0) +
                        "' to '" + message + "'!");
            }else {
                Err.regionAlreadyExistsError(player);
            }
        }else {
            Out.printToPlayer(player, ChatColor.DARK_GREEN + "Renaming process stopped.");
        }
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.onChat = null;
    };

    private static final OnChat levelRegionDialog = e -> {
        String message = e.getMessage();
        int level = Integer.parseInt(message);
        Player player = e.getPlayer();
        if (level != 0){
            if(!DataManager.isRegionAlreadyExisting(message)){
                Region region = DataManager.getRegionByName(DataManager.onlineUsers.get(player.getUniqueId().toString()).temp_strings.get(0));
                region.level = level;
                region.save();

                DataManager.regions = new ArrayList<>();
                DataManager.loadAllRegions();
                Regions_GUI.init();
                Out.printToPlayer(player,ChatColor.DARK_GREEN + "Set '" +
                        DataManager.onlineUsers.get(player.getUniqueId().toString()).temp_strings.get(0) +
                        ".level' to '" + level + "'!");
            }else {
                Err.regionAlreadyExistsError(player);
            }
        }else {
            Out.printToPlayer(player, ChatColor.DARK_GREEN + "Set level process stopped.");
        }
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.onChat = null;
    };

    private static final OnClick deleteRegion = ((player, item, inventory) -> {
        String regionname = item.getItemMeta().getDisplayName().split("DELETE REGION: ")[1];
        player.closeInventory();
        DataManager.deleteRegion(DataManager.getRegionByName(regionname));
        DataManager.regions = new ArrayList<>();
        DataManager.loadAllRegions();
        Regions_GUI.init();
    });

    private static final OnClick backtomainmenu = (player, item, inventory) -> {
        sites.get(0).show(player);
    };

    private static final OnClick renameRegion = ((player, item, inventory) -> {
        player.closeInventory();
        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Please type the new name. If you don't want to rename this region type 'null'.");
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.onChat = renameRegionDialog;
    });

    private static final OnClick setLevelRegion = ((player, item, inventory) -> {
        player.closeInventory();
        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Please type the new level. If you don't want to set a new level for this region type '0'.");
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.onChat = levelRegionDialog;
    });

    private static final OnClick showCities = (player, item, inventory) -> {
        Out.printToPlayer(player,inventory.getTitle());
        DataManager.getRegionByName(inventory.getTitle().split(" ")[1]).city_gui.show(player);
    };

    private static final OnClick showRegionDetails = ((player, item, inventory) -> {
        String itemName = item.getItemMeta().getDisplayName();
        Region region = DataManager.getRegionByName(itemName);
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        if(user.temp_strings.isEmpty()){
            user.temp_strings.add(0, itemName);
        }else {
            user.temp_strings.set(0, itemName);
        }
        Base_Gui gui = new Base_Gui(itemName);

        gui.rows = 1;

        gui.addItem(Material.NAME_TAG, 1, "Name: " + region.name, new ArrayList<>(), renameRegion, 0,0);
        gui.addItem(Material.REDSTONE_COMPARATOR, 1, "Regionlevel: " + region.level, new ArrayList<>(), setLevelRegion, 1,0);
        gui.addItem(Material.IRON_DOOR, 1, "Cities", new ArrayList<>(), showCities, 2,0);
        gui.addItem(Material.SPECTRAL_ARROW, 1, "Back to main menu",new ArrayList<>(), backtomainmenu, 8,0);
        gui.addItem(Material.BARRIER, 1, "DELETE REGION: " + region.name , new ArrayList<>(), deleteRegion, 7,0);

        gui.show(player);

    });




    private static final OnClick changeSite = (player, item, inventory) -> {
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

}
