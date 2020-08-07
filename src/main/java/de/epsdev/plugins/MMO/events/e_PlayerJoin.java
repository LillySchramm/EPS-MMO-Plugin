package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class e_PlayerJoin implements Listener {

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e){
        User user = DataManager.getUser(e.getPlayer());
        user.print();
        DataManager.onlineUsers.put(user.UUID,user);
    }

}
