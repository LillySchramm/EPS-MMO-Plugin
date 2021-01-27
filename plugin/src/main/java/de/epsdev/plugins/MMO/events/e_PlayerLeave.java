package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.nms.PacketReader;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class e_PlayerLeave implements Listener {
    @EventHandler
    void onPlayerLeave(PlayerQuitEvent e) {
        HandleDisconnect(e.getPlayer());
    }

    public static void HandleDisconnect(Player player){
        PacketReader reader = new PacketReader();
        reader.uninject(player);

        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        user.save();

        if(user.currentCharacter != null){
            user.currentCharacter.save();
        }
        user.session.unload();
        DataManager.onlineUsers.remove(player.getUniqueId().toString());
    }
}