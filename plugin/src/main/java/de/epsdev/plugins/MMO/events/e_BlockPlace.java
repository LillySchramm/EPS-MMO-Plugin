package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class e_BlockPlace implements Listener {
    @EventHandler
    void onBlockDestroyed(BlockPlaceEvent e){
        Player player = e.getPlayer();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        if(user.onPlace == null) {
            if (!player.getGameMode().equals(GameMode.CREATIVE)
                    || !user.rank.canBuild) {
                e.setCancelled(true);
            }
        }else {
            user.onPlace.onPlace(e);
        }
    }
}
