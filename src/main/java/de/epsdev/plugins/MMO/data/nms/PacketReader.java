package de.epsdev.plugins.MMO.data.nms;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.events.RightClickNPC;
import de.epsdev.plugins.MMO.npc.NPC;
import de.epsdev.plugins.MMO.npc.NPC_Manager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayInUseEntity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PacketReader {

    Channel channel;
    public static Map<UUID, Channel> channels = new HashMap<>();

    public void inject(Player player){
        CraftPlayer craftPlayer = (CraftPlayer) player;

        channel = craftPlayer.getHandle().playerConnection.networkManager.channel;
        channels.put(craftPlayer.getUniqueId(), channel);

        if(channel.pipeline().get("PacketInjector") != null) return;

        channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<PacketPlayInUseEntity>() {
            @Override
            protected void decode(ChannelHandlerContext channelHandlerContext, PacketPlayInUseEntity packet, List<Object> arg) throws Exception {
                readPacket(player,packet);
                arg.add(packet);
            }
        });

    }

    public void uninject(Player player){
        channel = channels.get(player.getUniqueId());
        if(channel.pipeline().get("PacketInjector") != null) {
            channel.pipeline().remove("PacketInjector");
        }
    }

    public void readPacket(Player player, PacketPlayInUseEntity packet){

        String val_action = getValue(packet, "action").toString();

        if(!val_action.equals("INTERACT")) return;

        if(getValue(packet, "d").toString().equals("OFF_HAND")) return;

        int id = (int) getValue(packet, "a");

            for (NPC npc : NPC_Manager.NPCs){
                if(npc.entityPlayer.getId() == id){
                    Bukkit.getScheduler().scheduleSyncDelayedTask(main.plugin, () -> Bukkit.getPluginManager().callEvent(new RightClickNPC(player, npc.entityPlayer)), 0);
                }
            }

        }

    private Object getValue(Object instance, String name){

        Object result = null;

        try{
            Field field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);

            result = field.get(instance);

            field.setAccessible(false);
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

}
