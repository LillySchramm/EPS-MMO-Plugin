package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

public class e_BlockDestroy implements Listener {
    @EventHandler
    void onBlockDestroyed(BlockBreakEvent e){

        Player player = e.getPlayer();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        if(user.onBreak == null) {
            if (!player.getGameMode().equals(GameMode.CREATIVE)
                    || !user.rank.canBuild) {
                e.setCancelled(true);
            }
        }else {
            user.onBreak.onBreak(e);
        }
    }
}
