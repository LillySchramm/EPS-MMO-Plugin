package de.epsdev.plugins.MMO.combat.basetypes;

import de.epsdev.plugins.MMO.combat.Attack;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;

import java.util.List;

public class Base_AOE extends Attack {

    public Base_AOE(String name, long reloadTime, float baseDmg, float baseHeal, float manaCastCost, float liveCastCost, String description) {
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
    public void playAnimation() {

    }

    @Override
    public float calculateDamage(User user, Base_Mob mob) {
        return 0;
    }
}
