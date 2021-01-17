package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class e_Inventory_Slot_Switch implements Listener {

    @EventHandler
    void onInventorySwitch(PlayerItemHeldEvent e){
        Player player = e.getPlayer();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        if(player.getGameMode() == GameMode.SURVIVAL){
            if (user.currentCharacter != null){
                int slot = e.getNewSlot();
                if (slot != 4){
                    if (slot <= 4) slot++;
                    user.currentCharacter.handleSkill(player, slot);
                }
            }
            e.setCancelled(true);
        }

    }

}
