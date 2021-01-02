package de.epsdev.plugins.MMO.npc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.tools.PlayerNames;
import de.epsdev.plugins.MMO.tools.Vec2f;
import de.epsdev.plugins.MMO.tools.Vec3f;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class NPC_Manager {
        public static List<NPC> NPCs = new ArrayList<>();
        public static Map<String, Skin> textureHash = new HashMap<>();

        public static EntityPlayer createNPC_ENTITY(String name ,Vec3f pos, Vec2f rotation, String skin){
            MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer world = ((CraftWorld) Bukkit.getServer().getWorld("world")).getHandle();
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);

            Location location = new Location(Bukkit.getServer().getWorld("world"), pos.x, pos.y, pos.z);
            location.setYaw(rotation.yaw);
            location.setPitch(rotation.pitch);

            EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
            npc.setLocation(location.getX(),location.getY(),location.getZ(),
                    location.getYaw(), location.getPitch());

            npc.getBukkitEntity().setRemoveWhenFarAway(false);

            if(skin != null){
                Skin skinValues = getSkin(skin);
                if(skinValues != null){
                    gameProfile.getProperties().put("textures", new Property("textures", skinValues.texture[0], skinValues.texture[1]));
                }
            }

            return npc;
        }

        public static NPC createNPC(Player player, String skin){
            EntityPlayer npc = createNPC_ENTITY(player.getDisplayName() ,new Vec3f(player.getLocation()), new Vec2f(player.getLocation()), skin);
            return addNPCPacket(npc, getSkin(skin), "", 0);
        }

        public static Skin getSkin(String name){
            Skin res = null;

            if(textureHash.containsKey(name)){
                return textureHash.get(name);
            }

            try{
                String uuid = PlayerNames.getUUID(name);

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

        public static NPC addNPCPacket(EntityPlayer npc_e, Skin skin, String script, int id){
            NPC npc = new NPC(id,npc_e.getName(), npc_e, skin, script);
            NPCs.add(npc);
            for(Player player : Bukkit.getOnlinePlayers()){
                npc.display(player);
            }
            return npc;
        }

        public static void loadAllNPC(Player player){
            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
            Vec3f pos = new Vec3f(player.getLocation());

            for(NPC npc : NPCs){
                Vec3f npc_pos = new Vec3f(npc.entityPlayer.getBukkitEntity().getLocation());

                if(pos.distance2d(npc_pos) < 100){
                    if(!user.loadedNPC.contains(npc.npc_id)){
                        npc.display(player);
                        user.loadedNPC.add(npc.npc_id);
                    }
                }else {
                    if(user.loadedNPC.contains(npc.npc_id)){
                        user.loadedNPC.remove((Object) npc.npc_id);
                        npc.unload(player);
                    }
                }
            }

        }

    }


