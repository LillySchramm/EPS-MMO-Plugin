package de.epsdev.plugins.MMO.npc.mobs.subtypes;

import de.epsdev.plugins.MMO.npc.Path;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import de.epsdev.plugins.MMO.npc.mobs.MobTargetAI;
import de.epsdev.plugins.MMO.npc.mobs.Mob_Types;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TestMob extends Base_Mob {

    private Path path;

    public TestMob(Vec3f pos, Mob_Types type) {
        super("Test Mob" , type, pos, 10f, 123.0f, 321.0f, 5f, 50f ,new MobTargetAI(
                20f,
                10f,
                30f
        ));
    }

    @Override
    public float calculateDamage(float init_dmg) {
        return init_dmg;
    }

}
