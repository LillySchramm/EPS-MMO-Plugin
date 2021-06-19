package de.epsdev.plugins.MMO.npc.mobs;

import com.mojang.authlib.GameProfile;
import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.combat.Attack;
import de.epsdev.plugins.MMO.combat.AttackCollection;
import de.epsdev.plugins.MMO.combat.Attackable;
import de.epsdev.plugins.MMO.combat.basetypes.attacks.Test_Melee_Attack;
import de.epsdev.plugins.MMO.combat.basetypes.attacks.Test_Self_Attack;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.npc.Path;
import de.epsdev.plugins.MMO.tools.Math;
import de.epsdev.plugins.MMO.tools.Vec3f;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SkeletonHorse;

import java.util.UUID;

public abstract class Base_Mob extends Attackable {

    private Entity e;
    private Mob_Types mobType;
    private Vec3f curPos;
    private double angle;
    private float speed;

    private Path path;
    private final String name;

    private boolean doesAdjustRotation = true;

    private MobTargetAI targetAI;

    private int[] schedulers = new int[4];

    public Base_Mob(String name ,Mob_Types type, Vec3f pos, float speed, float max_live, float max_mana, float health_regen,
                    float mana_regen, MobTargetAI ai){
        super(max_live, max_mana, health_regen, mana_regen,
                new AttackCollection(new Attack[]{new Test_Melee_Attack()},new Attack[]{}), SIDE.MOB);
        this.mobType = type;
        this.name = name;
        this.curPos = pos;
        this.speed = speed;
        //Random angle
        this.angle = Math.randomDoubleBetween(0,360);

        this.e = createEntity(mobType, curPos);

        this.targetAI = ai;
        this.targetAI.spawn = pos;

        Mob_Manager.enemies.put(this.e.getId(), this);
    }

    private Entity createEntity(Mob_Types type, Vec3f pos){
        WorldServer world = ((CraftWorld) Bukkit.getServer().getWorld("world")).getHandle();
        Entity e = Mob_Types.get(type, world);

        e.setPosition(pos.x,pos.y,pos.z);
        e.setCustomName(ChatColor.DARK_AQUA + name + ChatColor.RED + " " + this.getCur_health() + "/" + this.max_health);
        e.setCustomNameVisible(true);
        e.setPositionRotation(e.getChunkCoordinates(),0,0);
        e.setNoGravity(true);

        this.schedulers[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, this::updateTarget, 0L, 20L);
        this.schedulers[1] = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, this::updatePos, 0L, 1L);
        this.schedulers[2] = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, this::syncPosition, 0L, 40L);
        this.schedulers[3] = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, () -> MobCombatAI.chooseAttack(this), 0L, 5L);

        return e;
    }

    public void remove(Player player){
        sendToPlayer(player,
                new PacketPlayOutEntityDestroy(this.e.getId())
        );
    }

    private void sendToPlayer(Player p, Packet pp){
        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
        connection.sendPacket(pp);
    }

    /*
    TODO: Highly inefficient due to the fact that it gets send on a global scale.
     */
    private void sendPacketToAllPlayers(Packet p){
        for (Player player : Bukkit.getOnlinePlayers()){
            sendToPlayer(player,p);
        }
    }

    public void display(Player player){
        sendToPlayer(player, new PacketPlayOutSpawnEntityLiving((EntityLiving) this.e));
    }

    public void globalDisplay(){
        sendPacketToAllPlayers(new PacketPlayOutSpawnEntityLiving((EntityLiving) this.e));
    }

    public Vec3f getPos(){
        return this.curPos;
    }

    private void updateMetadata(){
        sendPacketToAllPlayers(new PacketPlayOutEntityMetadata(e.getId(), e.getDataWatcher(), true));
    }

    private void updateName(){
        String n = ChatColor.DARK_AQUA + name + ChatColor.RED + " " + this.getCur_health() + "/" + this.max_health;
        e.setCustomName(n);

        updateMetadata();
    }

    private void updateTarget(){
        Vec3f nextTarget = getNextTarget();
        if(nextTarget != null){
            this.e.setPosition(curPos.x,curPos.y,curPos.z);

            this.path = new Path(curPos, getNextTarget(), this.e.world, true);
            this.path.draw();
        }else {
            this.path = null;
        }
    }

    public void updatePos(){
        if(path != null){
            if(path.getCurrentWayPoint() != null){
                if(!curPos.equals(path.getCurrentWayPoint())){
                    float distance = curPos.distance3d(path.getCurrentWayPoint());
                    float delta_distance = this.speed * DataManager.delta.d;

                    Vec3f dir = Vec3f.getDirectionVec(curPos, path.getCurrentWayPoint());
                    Vec3f newPos = Vec3f.add(curPos, Vec3f.multiply(dir, delta_distance));

                    if(distance <= delta_distance){
                        moveTo(path.getCurrentWayPoint());
                    }else {
                        moveTo(newPos);
                    }
                }else{
                    if(!path.next()){
                        this.path = null;
                        moveTo(curPos);
                    }
                }
            }else {
                this.path = null;
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
        sendPacketToAllPlayers(
            new PacketPlayOutAnimation(
                    this.e,1
            )
        );

        for(Player p : Bukkit.getOnlinePlayers()){
            p.playSound(this.curPos.toLocation(), Sound.valueOf("entity_zombie_hurt".toUpperCase()), SoundCategory.HOSTILE, 1.0f, 1.0f);
        }
    }

    private void syncPosition(){
        sendPacketToAllPlayers(new PacketPlayOutEntityTeleport(this.e));
    }

    private void moveTo(Vec3f newPos){
        setAngle(this.curPos.getAngleTowards(newPos));
        e.setPosition(newPos.x, newPos.y, newPos.z);

        sendPacketToAllPlayers(new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(
                this.e.getId(),
                (long) (newPos.x * 32 - curPos.x * 32) * 128,
                (long) (newPos.y * 32 - curPos.y * 32) * 128,
                (long) (newPos.z * 32 - curPos.z * 32) * 128,
                (byte) ((int) (angle * 256.0F / 360.0F)),
                (byte) 0, //TODO: What is this arg? Why do I need it? What is its reason to live?
                e.onGround
        ));

        this.curPos = newPos;
    }

    public Entity getEntity(){
        return this.e;
    }

    private Vec3f getNextTarget(){
        targetAI.update(curPos);
        return targetAI.getTarget();
    }

    @Override
    public Vec3f getPosition() {
        return this.curPos;
    }

    @Override
    public float calculateDamage(float org_damage) {
        playHit();
        return org_damage;
    }

    @Override
    public float calculateHeal(float org_heal) {
        return org_heal;
    }

    @Override
    public float calculateManaLoss(float org_loss) {
        return org_loss;
    }

    @Override
    public float calculateManaGain(float org_gain) {
        return org_gain;
    }

    @Override
    public void onChange() {
        updateName();
    }

    @Override
    public void kill(){
        Bukkit.getScheduler().scheduleSyncDelayedTask(main.plugin, () -> {
            sendPacketToAllPlayers(
                    new PacketPlayOutEntityDestroy(this.e.getId())
            );
        }, 20L);

        sendPacketToAllPlayers(
                new PacketPlayOutAnimation(e,3)
        );

        Mob_Manager.enemies.remove(this.e.getId());
        this.removeFromList();

        Bukkit.getScheduler().cancelTask(this.schedulers[0]);
        Bukkit.getScheduler().cancelTask(this.schedulers[1]);
        Bukkit.getScheduler().cancelTask(this.schedulers[2]);
        Bukkit.getScheduler().cancelTask(this.schedulers[3]);
    }
}
