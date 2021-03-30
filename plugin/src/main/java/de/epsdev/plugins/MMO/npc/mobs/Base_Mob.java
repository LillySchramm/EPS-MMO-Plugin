package de.epsdev.plugins.MMO.npc.mobs;

import com.mojang.authlib.GameProfile;
import de.epsdev.plugins.MMO.tools.Vec3f;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Base_Mob {

    public EntityZombie createZombieEntity(Vec3f pos){
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getServer().getWorld("world")).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "Hollo");

        EntityZombie e = new EntityZombie(world);
        e.setPosition(pos.x,pos.y,pos.z);
        e.setHeadRotation(90.0f);
        e.setCustomName(ChatColor.DARK_AQUA + "Base_Mob");
        e.setCustomNameVisible(true);
        e.setOnFire(1);
        return e;
    }

    public void display(Player player){
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        EntityZombie e = createZombieEntity(new Vec3f(player.getLocation()));

        //connection.sendPacket(new PacketPlayOutSpawnEntity( createZombieEntity(new Vec3f(player.getLocation())),1));
        connection.sendPacket(new PacketPlayOutSpawnEntityLiving(e));
        connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutRelEntityMove(e.getId(),
                (long) ((e.locX + 5)  * 32 - e.locX * 32) * 128,
                (long) ((e.locY)  * 32 - e.locY * 32) * 128,
                (long) ((e.locZ)  * 32 - e.locZ * 32) * 128,
                true));
        //connection.sendPacket(new PacketPlayOutEntityVelocity(e.getId(), 0.0,1.0,0.0));

    }

}
