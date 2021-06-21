package de.epsdev.plugins.MMO.npc.mobs.subtypes;

import de.epsdev.plugins.MMO.npc.Path;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import de.epsdev.plugins.MMO.npc.mobs.MobTargetAI;
import de.epsdev.plugins.MMO.npc.mobs.Mob_Types;
import de.epsdev.plugins.MMO.tools.Vec3f;

public class DummyMob extends Base_Mob {
    private Path path;

    public DummyMob(Vec3f pos, Mob_Types type) {
        super("Dummy" , type, pos, 0f, 20.0f, 500.0f, 1f, 50f ,new MobTargetAI(
                0f,
                0f,
                0f
        ));
    }

    @Override
    public float calculateDamage(float init_dmg) {
        return init_dmg;
    }

}
