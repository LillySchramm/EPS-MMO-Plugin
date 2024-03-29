package de.epsdev.plugins.MMO.combat.basetypes;

import de.epsdev.plugins.MMO.combat.Attack;
import de.epsdev.plugins.MMO.combat.Attackable;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class Base_AOE extends Attack {

    public Base_AOE(String name, long reloadTime, float baseDmg, float baseHeal, float manaCastCost, float liveCastCost, String description) {
        super(name, reloadTime, baseDmg, baseHeal, manaCastCost, liveCastCost, description);
    }

    @Override
    public List<Attackable> getTargets(Attackable p) {
        return null;
    }

    @Override
    public List<Attackable> getHealTargets(Attackable p) {
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
    public float calculateDamage(Attackable a1, Attackable a2) {
        return 0;
    }
}
