package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.nms.PacketReader;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class e_PlayerLeave implements Listener {
    @EventHandler
    void onPlayerJoin(PlayerQuitEvent e) {
        PacketReader reader = new PacketReader();
        reader.uninject(e.getPlayer());
        DataManager.onlineUsers.get(e.getPlayer().getUniqueId().toString()).save();
        DataManager.onlineUsers.remove(e.getPlayer().getUniqueId().toString());
    }
}