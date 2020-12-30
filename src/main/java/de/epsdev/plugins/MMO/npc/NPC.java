package de.epsdev.plugins.MMO.npc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.epsdev.plugins.MMO.data.output.Out;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPC {
    public static List<EntityPlayer> NPC = new ArrayList<>();

    public static void createNPC(Player player, String skin){
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.DARK_AQUA + "NPC");

        Location playerLocation = player.getLocation();

        EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
        npc.setLocation(playerLocation.getX(),playerLocation.getY(),playerLocation.getZ(),
                playerLocation.getYaw(), playerLocation.getPitch());

        npc.getBukkitEntity().setRemoveWhenFarAway(false);

        if(skin != null){
            String[] skinValues = getSkin(skin);
            if(skinValues != null){
                gameProfile.getProperties().put("textures", new Property("textures", skinValues[0], skinValues[1]));

            }
        }




        addNPCPacket(npc);

    }

    private static String[] getSkin(String name){
        String[] res = null;

        try{
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();

            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader2 = new InputStreamReader(url2.openStream());

            JsonObject property = new JsonParser().parse(reader2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();

            res = new String[]{texture,signature};

        }catch(Exception e){
            e.printStackTrace();
        }

        return res;
    }

    public static void addNPCPacket(EntityPlayer npc){
        NPC.add(npc);
        for(Player player : Bukkit.getOnlinePlayers()){
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
                    npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
        }
    }

}
