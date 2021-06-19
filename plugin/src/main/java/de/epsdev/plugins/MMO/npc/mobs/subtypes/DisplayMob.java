package de.epsdev.plugins.MMO.npc.mobs.subtypes;

import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import de.epsdev.plugins.MMO.npc.mobs.MobTargetAI;
import de.epsdev.plugins.MMO.npc.mobs.Mob_Types;
import de.epsdev.plugins.MMO.tools.Vec3f;

public class DisplayMob extends Base_Mob {

    public DisplayMob(Vec3f pos, Mob_Types type) {
        super(type.name(), type, pos, 1f,10f, 10.0f, 0,0,new MobTargetAI(0f,0f,0f));
    }

    @Override
    public float calculateDamage(float init_dmg) {
        return init_dmg;
    }

}
