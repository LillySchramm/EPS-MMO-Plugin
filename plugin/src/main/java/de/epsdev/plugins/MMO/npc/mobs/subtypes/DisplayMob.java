package de.epsdev.plugins.MMO.npc.mobs.subtypes;

import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import de.epsdev.plugins.MMO.npc.mobs.Mob_Types;
import de.epsdev.plugins.MMO.tools.Math;
import de.epsdev.plugins.MMO.tools.Vec3f;

public class DisplayMob extends Base_Mob {

    public DisplayMob(Vec3f pos, Mob_Types type) {
        super(type.name(), type, pos, 1f,10f);
    }

    @Override
    public float calculateDamage(float init_dmg) {
        return init_dmg;
    }

    @Override
    public Vec3f getNextTarget() {
        return null;
    }
}
