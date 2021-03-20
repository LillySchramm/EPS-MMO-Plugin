package de.epsdev.plugins.MMO.GUI.player;

import de.epsdev.plugins.MMO.GUI.base.Base_Gui;
import de.epsdev.plugins.MMO.GUI.dev.OnClick;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.tools.Colors;
import de.epsdev.plugins.MMO.tools.TooltipLore;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;

public class PlayerHouses_GUI {
    public static OnClick click_teleport = (player, item, inventory) -> {
      String displayName = Colors.removeBukkitColorCodes(item.getItemMeta().getDisplayName());
      int id = Integer.parseInt(displayName.split("ID: ")[1]);

      House house = DataManager.getHouseByID(id);
      player.teleport(new Location(player.getWorld(), house.spawnPosition.x,house.spawnPosition.y,house.spawnPosition.z));

    };

    public static Base_Gui genHouseOverviewMenu(User user){
        Base_Gui base_gui = new Base_Gui("Your houses");

        ArrayList<House> houses = DataManager.getHousesOwnedByPlayer(user.UUID);

        int i = 0;

        for(House house : houses){
            base_gui.addItem(Material.DARK_OAK_DOOR_ITEM, 1, ChatColor.DARK_GREEN + house.name + " ID: " + house.id , TooltipLore.tt_clickToTeleport(), click_teleport, i,0);
            i++;
        }

        return base_gui;
    }
}
