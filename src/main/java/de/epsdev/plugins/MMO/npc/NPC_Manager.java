package de.epsdev.plugins.MMO.npc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
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
import java.util.*;

public class NPC_Manager {
        public static List<NPC> NPCs = new ArrayList<>();
        public static Map<String, Skin> textureHash = new HashMap<>();

        public static NPC createNPC(Player player, String skin){
            MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.DARK_AQUA + "NPC");

            Location playerLocation = player.getLocation();

            EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
            npc.setLocation(playerLocation.getX(),playerLocation.getY(),playerLocation.getZ(),
                    playerLocation.getYaw(), playerLocation.getPitch());

            npc.getBukkitEntity().setRemoveWhenFarAway(false);

            if(skin != null){
                Skin skinValues = getSkin(skin);
                if(skinValues != null){
                    gameProfile.getProperties().put("textures", new Property("textures", skinValues.texture[0], skinValues.texture[1]));

                }
                return addNPCPacket(npc, skinValues);
            }
            return addNPCPacket(npc, null);

        }

        private static Skin getSkin(String name){
            Skin res = null;

            if(textureHash.containsKey(name)){
                return textureHash.get(name);
            }

            try{
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
                InputStreamReader reader = new InputStreamReader(url.openStream());
                String uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();

                URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
                InputStreamReader reader2 = new InputStreamReader(url2.openStream());

                JsonObject property = new JsonParser().parse(reader2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
                String texture = property.get("value").getAsString();
                String signature = property.get("signature").getAsString();

                res = new Skin(new String[]{texture,signature}, name);

                textureHash.put(name, res);

            }catch(Exception e){
                e.printStackTrace();
            }

            return res;
        }

        public static NPC addNPCPacket(EntityPlayer npc_e, Skin skin){
            NPC npc = new NPC(0,npc_e.getName(), npc_e, skin);
            NPCs.add(npc);
            for(Player player : Bukkit.getOnlinePlayers()){
                npc.display(player);
            }
            return npc;
        }

        public static void loadAllNPC(Player player){

            for(NPC npc : NPCs){
                npc.display(player);
            }

        }

    }


