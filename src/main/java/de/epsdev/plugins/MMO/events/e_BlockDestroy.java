package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

public class e_BlockDestroy implements Listener {
    @EventHandler
    void onBlockDestroyed(BlockDamageEvent e){
        if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            e.setCancelled(true);
        }
        if(!DataManager.onlineUsers.get(e.getPlayer().getUniqueId().toString()).rank.canBuild){
            e.setCancelled(true);
            Out.printToBroadcast("lol");
        }
    }
}
