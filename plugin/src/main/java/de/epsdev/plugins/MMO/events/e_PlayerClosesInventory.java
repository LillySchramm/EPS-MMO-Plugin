package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class e_PlayerClosesInventory implements Listener {
    @EventHandler
    void PlayerInventoryCloseEvent(InventoryCloseEvent e){
        HumanEntity player = e.getPlayer();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        if(user.currentCharacter == null){
            BukkitScheduler scheduler = main.plugin.getServer().getScheduler();
            scheduler.scheduleSyncDelayedTask(main.plugin, () -> {
                user.showCharacterSelectionMenu(Bukkit.getPlayer(player.getUniqueId()));
            }, 1L);
        }

    }
}
