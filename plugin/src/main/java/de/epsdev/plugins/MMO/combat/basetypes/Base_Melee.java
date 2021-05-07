package de.epsdev.plugins.MMO.combat.basetypes;

import de.epsdev.plugins.MMO.combat.Attack;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import org.bukkit.entity.Entity;

import java.util.List;

public abstract class Base_Melee extends Attack {
    public Base_Melee(String name, long reloadTime, float baseDmg, float baseHeal, float manaCastCost, float liveCastCost, String description) {
        super(name, reloadTime, baseDmg, baseHeal, manaCastCost, liveCastCost, description);
    }

    @Override
    public List<Base_Mob> getTargets() {
        return null;
    }

    @Override
    public List<User> getHealTargets() {
        return null;
    }

    @Override
    public void playAnimation(Entity e) {
        pAnimation(e);
    }
    public abstract void pAnimation(Entity e);

    @Override
    public void playHitAnimation(Entity e) {
        pHitAnimation(e);
    }
    public abstract void pHitAnimation(Entity e);

    @Override
    public float calculateDamage(User user, Base_Mob mob) {
        return 0;
    }
}
