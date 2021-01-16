package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class e_PlayerKick implements Listener {
    @EventHandler
    void onPlayerKick(PlayerKickEvent e){
        Player player = e.getPlayer();

        if(e.getReason().equals("Flying is not enabled on this server")){
            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
            if(user.currentCharacter.name.equals("")){
                e.setCancelled(true);
            }
        }

    }
}
