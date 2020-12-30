package de.epsdev.plugins.MMO.events;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RightClickNPC extends Event implements Cancellable {

    public final Player player;
    public final EntityPlayer npc;
    private boolean isCancelled;

    private static final HandlerList HANDLERS = new HandlerList();

    public RightClickNPC(Player player, EntityPlayer npc){
        this.player = player;
        this.npc = npc;
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
