package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.npc.NPC;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RightClickNPC extends Event implements Cancellable {

    public final Player player;
    public final User user;
    public final NPC npc;
    private boolean isCancelled;

    private static final HandlerList HANDLERS = new HandlerList();

    public RightClickNPC(Player player, NPC npc){
        this.player = player;
        this.npc = npc;

        this.user = DataManager.onlineUsers.get(player.getUniqueId().toString());
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCancelled = b;
    }
}
