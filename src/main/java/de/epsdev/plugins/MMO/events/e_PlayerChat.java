package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class e_PlayerChat implements Listener {

    @EventHandler
    void onPlayerChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        e.setCancelled(true);

        if(user.onChat == null){
            if(!DataManager.chatMuted || user.rank.canSpeakWhenChatMuted) {
                Out.printToBroadcast("[" + user.rank.prefix + ChatColor.WHITE + "] " +
                        user.displayName +
                        " [" + ChatColor.GOLD + " user.level"  + ChatColor.WHITE + "] "
                        + e.getMessage());
            }else {
                Err.chat_mutedError(player);
            }
        }else {
            user.onChat.onchat(e);
        }



    }

}