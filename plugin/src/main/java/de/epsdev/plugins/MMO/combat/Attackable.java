package de.epsdev.plugins.MMO.combat;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.tools.Vec3f;
import net.minecraft.server.v1_12_R1.Entity;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class Attackable {

    public AttackCollection attackCollection;
    public final UUID uuid;

    public static HashMap<UUID,Attackable> attackables = new HashMap<>();

    private final int regenScheduler;

    public float max_health;
    private float cur_health;

    public float max_mana;
    private float cur_mana;

    private float health_regen;
    private float mana_regen;

    public SIDE side;

    /**
     *
     * @param max_health
     * @param max_mana
     * @param health_regen per second (20 Ticks)
     * @param mana_regen per second (20 Ticks)
     * @param attackCollection
     * @param side
     */
    public Attackable(float max_health, float max_mana, float health_regen, float mana_regen,AttackCollection attackCollection, SIDE side){
        this.max_health = max_health;
        this.cur_health = max_health;

        this.cur_mana = max_mana;
        this.max_mana = max_mana;

        this.health_regen = health_regen;
        this.mana_regen = mana_regen;

        this.attackCollection = attackCollection;

        this.side = side;
        this.uuid = UUID.randomUUID();

        this.regenScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, this::regenTick, 0L, 5L);

        Attackable.attackables.put(this.uuid,this);
    }

    private void regenTick(){
        this.heal(this.health_regen / 5);
        this.giveMana(this.mana_regen / 5);
    }

    public void removeFromList(){
        Bukkit.getScheduler().cancelTask(this.regenScheduler);
        attackables.remove(this.uuid);
    }

    public void dealDamage(float amount){
        amount = calculateDamage(amount);

        this.cur_health -= amount;
        this.cur_health = Math.max(0, this.cur_health);

        if(this.cur_health == 0) kill();
        else onChange();
    }

    public void heal(float amount){
        amount = calculateHeal(amount);
        this.cur_health += amount;
        this.cur_health = Math.min(this.cur_health, this.max_health);
        onChange();
    }

    public void reduceMana(float amount){
        amount = calculateManaLoss(amount);
        this.cur_mana -= amount;
        this.cur_mana = Math.max(0, this.cur_mana);
    }

    public void giveMana(float amount){
        amount = calculateManaGain(amount);
        this.cur_mana += amount;
        this.cur_mana = Math.min(this.cur_mana, this.max_mana);
    }

    // Only exists to avoid me influencing these values on any other way than using the methods above
    public float getCur_health(){
        return this.cur_health;
    }

    // Only exists to avoid me influencing these values on any other way than using the methods above
    public float getCur_mana(){
        return this.cur_mana;
    }

    public abstract Vec3f getPosition();

    public abstract float calculateDamage(float org_damage);
    public abstract float calculateHeal(float org_heal);

    public abstract float calculateManaLoss(float org_loss);
    public abstract float calculateManaGain(float org_gain);

    public abstract void onChange();

    public abstract Entity getEntity();

    public abstract void kill();

    public enum SIDE{
        PLAYER, MOB
    }
}
