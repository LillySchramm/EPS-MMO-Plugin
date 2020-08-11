package de.epsdev.plugins.MMO.events;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public interface OnChat {
    void onchat(AsyncPlayerChatEvent e);
}
