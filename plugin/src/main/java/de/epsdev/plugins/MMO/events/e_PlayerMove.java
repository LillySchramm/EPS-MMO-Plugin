package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.npc.NPC_Manager;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class e_PlayerMove implements Listener {
    @EventHandler
    void PlayerMove(PlayerMoveEvent e){
        Player player = e.getPlayer();

        Chunk chunk = player.getLocation().getChunk();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        if(player.getGameMode() == GameMode.CREATIVE){
            player.setAllowFlight(true);
        }else {
            player.setAllowFlight(false);
        }

        if(user.currentCharacter != null && !user.currentCharacter.name.equals("")) {

            Vec3f pos = new Vec3f(player.getLocation());
            if (chunk != user.lastChunk) {
                user.lastChunk = chunk;
                NPC_Manager.loadAllNPC(player);
            }
        }else {
            e.setCancelled(true);
        }

    }

}
