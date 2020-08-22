package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Door;


public class e_PlayerInteract implements Listener {

    @EventHandler
    void onInteract(PlayerInteractEvent e){

        Player player = e.getPlayer();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        Block block = e.getClickedBlock();

        Material blockMaterial = null;

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){

            if(user.onRightObj == null){

                if(block.getType().toString().contains("DOOR")) {

                    if (!(player.getGameMode() == GameMode.CREATIVE && user.rank.canManageHouses)) {
                        e.setCancelled(true);
                    }

                }

            }else {
                user.onRightObj.interact(e);
            }
        }
    }
}
