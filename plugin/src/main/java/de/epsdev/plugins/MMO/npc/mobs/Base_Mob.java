package de.epsdev.plugins.MMO.npc.mobs;

import com.mojang.authlib.GameProfile;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.tools.Vec3f;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SkeletonHorse;

import java.util.UUID;

public class Base_Mob {

    private Entity e;
    private Mob_Types mobType;
    private Vec3f curPos;
    private Vec3f targetPos = null;
    private float speed;

    public Base_Mob(Mob_Types type, Vec3f pos, float speed){
        this.mobType = type;
        this.curPos = pos;
        this.e = createEntity(mobType, curPos);
        this.speed = speed;
    }

    private Entity createEntity(Mob_Types type, Vec3f pos){
        WorldServer world = ((CraftWorld) Bukkit.getServer().getWorld("world")).getHandle();
        Entity e = Mob_Types.get(type, world);

        e.setPosition(pos.x,pos.y,pos.z);
        e.setHeadRotation(90.0f);
        e.setCustomName(ChatColor.DARK_AQUA + "" + e.getId());
        e.setCustomNameVisible(true);

        return e;
    }

    /*
    TODO: Highly inefficient due to the fact that it gets send on a global scale.
     */
    private void sendPacketToAllPlayers(Packet p){
        for (Player player : Bukkit.getOnlinePlayers()){
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(p);
        }
    }

    public void display(){
        sendPacketToAllPlayers(new PacketPlayOutSpawnEntityLiving((EntityLiving) this.e));
    }

    public Vec3f getPos(){
        return this.curPos;
    }

    public void setTargetPos(Vec3f target){
        this.targetPos = target;
    }

    public void updatePos(){
        if(targetPos != null){
            if(!curPos.equals(targetPos)){

                float distance = curPos.distance3d(targetPos);
                float delta_distance = this.speed * DataManager.delta.d;

                Vec3f dir = Vec3f.getDirectionVec(curPos, targetPos);
                Vec3f newPos = Vec3f.add(curPos, Vec3f.multiply(dir, delta_distance));

                if(distance <= delta_distance){
                    moveTo(targetPos);
                }else {
                    moveTo(newPos);
                }

            }
        }
    }

    private void moveTo(Vec3f newPos){

        sendPacketToAllPlayers(new PacketPlayOutEntity.PacketPlayOutRelEntityMove(
                this.e.getId(),
                (long) (newPos.x * 32 - curPos.x * 32) * 128,
                (long) (newPos.y * 32 - curPos.y * 32) * 128,
                (long) (newPos.z * 32 - curPos.z * 32) * 128,
                true
        ));

        this.curPos = newPos;
    }

}
