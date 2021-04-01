package de.epsdev.plugins.MMO.npc.mobs;

import com.mojang.authlib.GameProfile;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.tools.Math;
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

public abstract class Base_Mob {

    private Entity e;
    private Mob_Types mobType;
    private Vec3f curPos;
    private Vec3f targetPos = null;
    private double angle;
    private float speed;
    
    private float max_live;
    private float cur_live;

    private String name;

    private boolean doesAdjustRotation = true;

    public Base_Mob(String name ,Mob_Types type, Vec3f pos, float speed, float max_live){
        this.mobType = type;
        this.name = name;
        this.curPos = pos;
        this.speed = speed;
        //Random angle
        this.angle = Math.randomDoubleBetween(0,360);
        
        this.max_live = max_live;
        this.cur_live = max_live;
        
        this.e = createEntity(mobType, curPos);
    }

    private Entity createEntity(Mob_Types type, Vec3f pos){
        WorldServer world = ((CraftWorld) Bukkit.getServer().getWorld("world")).getHandle();
        Entity e = Mob_Types.get(type, world);

        e.setPosition(pos.x,pos.y,pos.z);
        e.setCustomName(ChatColor.DARK_AQUA + name + ChatColor.RED + " " + this.cur_live + "/" + this.max_live);
        e.setCustomNameVisible(true);
        e.setPositionRotation(e.getChunkCoordinates(),0,0);

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

    private void updateMetadata(){
        sendPacketToAllPlayers(new PacketPlayOutEntityMetadata(e.getId(), e.getDataWatcher(), true));
    }

    private void updateName(){
        String n = ChatColor.DARK_AQUA + name + ChatColor.RED + " " + this.cur_live + "/" + this.max_live;
        e.setCustomName(n);

        updateMetadata();
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

    public void setAngle(double angle){
        if(this.angle != angle){
            this.angle = angle;
            this.e.setHeadRotation((float) angle);
            sendPacketToAllPlayers(new PacketPlayOutEntityHeadRotation(
                    this.e,
                    (byte) ((int) (angle * 256.0F / 360.0F))
            ));
        }

    }

    public void playHit(){
        e.damageEntity(DamageSource.GENERIC, 1);
    }

    private void moveTo(Vec3f newPos){
        setAngle(this.curPos.getAngleTowards(newPos));

        sendPacketToAllPlayers(new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(
                this.e.getId(),
                (long) (newPos.x * 32 - curPos.x * 32) * 128,
                (long) (newPos.y * 32 - curPos.y * 32) * 128,
                (long) (newPos.z * 32 - curPos.z * 32) * 128,
                (byte) ((int) (angle * 256.0F / 360.0F)),
                (byte) 0, //TODO: What is this arg? Why do I need it? What is its reason to live?
                true
        ));

        this.curPos = newPos;
    }

    public void doDamage(float amount){
        amount = calculateDamage(amount);

        this.cur_live -= amount;

        updateName();
    }

    public abstract float calculateDamage(float init_dmg);


}
