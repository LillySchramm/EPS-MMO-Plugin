package de.epsdev.plugins.MMO.combat;

import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.tools.Vec3f;
import net.minecraft.server.v1_12_R1.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Attackable {

    public AttackCollection attackCollection;
    public final UUID uuid;

    public static List<Attackable> attackables = new ArrayList<>();

    public float max_health;
    private float cur_health;

    public float max_mana;
    private float cur_mana;

    public SIDE side;

    public Attackable(float max_health, float max_mana, AttackCollection attackCollection, SIDE side){
        this.max_health = max_health;
        this.cur_health = max_health;

        this.cur_mana = max_mana;
        this.max_mana = max_mana;

        this.attackCollection = attackCollection;

        this.side = side;

        this.uuid = UUID.randomUUID();

        Attackable.attackables.add(this);
    }

    public void removeFromList(){
        attackables.removeIf((s) -> s.uuid == this.uuid);
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
