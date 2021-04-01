package de.epsdev.plugins.MMO.npc.mobs.subtypes;

import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import de.epsdev.plugins.MMO.npc.mobs.Mob_Types;
import de.epsdev.plugins.MMO.tools.Vec3f;

public class TestMob extends Base_Mob {
    public TestMob(Vec3f pos) {
        super(Mob_Types.ZOMBIE, pos, 2.5f);
    }
}
