package de.epsdev.plugins.MMO.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class e_PlayerItemDrop implements Listener {

    @EventHandler
    void onPlayerItemDrop(PlayerDropItemEvent e){
        Player player = e.getPlayer();

        if(player.getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
    }

}
