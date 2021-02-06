package de.epsdev.plugins.MMO.npc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.tools.PlayerNames;
import de.epsdev.plugins.MMO.tools.Vec2f;
import de.epsdev.plugins.MMO.tools.Vec3f;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
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

        public static EntityPlayer createNPC_ENTITY(String name ,Vec3f pos, Vec2f rotation, Skin skin){
            MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer world = ((CraftWorld) Bukkit.getServer().getWorld("world")).getHandle();
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);

            Location location = new Location(Bukkit.getServer().getWorld("world"), pos.x, pos.y, pos.z);

            EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));

            npc.setPositionRotation(location.getX(),location.getY(),location.getZ(), rotation.yaw, rotation.pitch);

            npc.getBukkitEntity().setRemoveWhenFarAway(false);

            gameProfile.getProperties().put("textures", new Property("textures", skin.texture_data, skin.texture_signature));


            return npc;
        }

        public static NPC createNPC(Player player, Skin skin){
            EntityPlayer player_ = ((CraftPlayer) player).getHandle();

            EntityPlayer npc = createNPC_ENTITY(player.getDisplayName() ,new Vec3f(player.getLocation()), new Vec2f(player_.yaw,player_.pitch), skin);
            return addNPCPacket(npc, skin, "", 0);
        }

        public static NPC addNPCPacket(EntityPlayer npc_e, Skin skin, String script, int id){
            NPC npc = new NPC(id,npc_e.getName(), npc_e, skin, script);
            NPCs.add(npc);
            for(Player player : Bukkit.getOnlinePlayers()){
                npc.display(player);
            }
            return npc;
        }

        public static void unloadAllNPC(Player player){
            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
            user.lastChunk = null;
            user.loadedNPC = new ArrayList<>();
            for(NPC npc : NPCs){
                npc.unload(player);
            }

            NPCs.clear();
        }

        public static void unloadNPC(NPC npc){
            for (Player player : Bukkit.getOnlinePlayers()){
                User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

                if(user.loadedNPC.contains(npc.npc_id)){
                    npc.unload(player);
                }
            }
        }

        public static void reloadNPC(NPC npc){
            for (NPC n : NPCs){
                if(n.npc_id == npc.npc_id){
                    NPCs.remove(n);
                    NPCs.add(npc);
                    break;
                }
            }

            for (Player player : Bukkit.getOnlinePlayers()){
                User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

                if(user.loadedNPC.contains(npc.npc_id))
                    npc.unload(player);
                    npc.display(player);
            }
        }

    public static void fullReloadNPC(NPC npc){
        for (NPC n : NPCs){
            if(n.npc_id == npc.npc_id){
                n.fullReload();
                NPCs.remove(n);
                NPCs.add(npc);
                break;
            }
        }

        for (Player player : Bukkit.getOnlinePlayers()){
            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

            if(user.loadedNPC.contains(npc.npc_id))
                npc.unload(player);
            npc.display(player);
        }
    }

        public static void loadAllNPC(Player player){
            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
            Vec3f pos = new Vec3f(player.getLocation());

            for(NPC npc : NPCs){
                Vec3f npc_pos = new Vec3f(npc.entityPlayer.getBukkitEntity().getLocation());

                if(pos.distance2d(npc_pos) < 100){
                    if(!user.loadedNPC.contains(npc.npc_id)){
                        npc.display(player);
                    }
                }else {
                    if(user.loadedNPC.contains(npc.npc_id)){
                        npc.unload(player);
                    }
                }
            }

        }

    }


