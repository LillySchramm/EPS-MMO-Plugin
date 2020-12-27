package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class e_PlayerJoin implements Listener {

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e){
        User user = null;
        try {
            user = DataManager.getUser(e.getPlayer(), true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        user.print();
        DataManager.onlineUsers.put(user.UUID,user);
    }

}
