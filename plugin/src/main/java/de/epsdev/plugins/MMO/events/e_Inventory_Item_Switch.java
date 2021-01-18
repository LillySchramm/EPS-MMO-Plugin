package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.output.Out;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;

public class e_Inventory_Item_Switch implements Listener {

    @EventHandler
    void onItemSwitch(InventoryMoveItemEvent e){

        Player player = (Player) e.getDestination().getHolder();
        ItemStack itemStack = e.getItem();
        Out.printToConsole(1);


        if(player.getGameMode() != GameMode.CREATIVE){
            if(itemStack.getItemMeta().getDisplayName().contains("[SKILL]")){
                e.setCancelled(true);
            }
        }

    }

}
