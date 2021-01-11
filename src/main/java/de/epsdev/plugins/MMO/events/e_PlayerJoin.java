package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.nms.PacketReader;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.npc.NPC_Manager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.SQLException;

public class e_PlayerJoin implements Listener {

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e){

        PacketReader reader = new PacketReader();
        reader.inject(e.getPlayer());

        User user = null;
        try {
            user = DataManager.getUser(e.getPlayer(), true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        user.print();
        DataManager.onlineUsers.put(user.UUID,user);
        user.showCharacterSelectionMenu(e.getPlayer());

        NPC_Manager.loadAllNPC(e.getPlayer());
    }

}
