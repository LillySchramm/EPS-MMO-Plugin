package de.epsdev.plugins.MMO.npc.mobs.subtypes;

import de.epsdev.plugins.MMO.npc.Path;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import de.epsdev.plugins.MMO.npc.mobs.Mob_Types;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TestMob extends Base_Mob {

    private Path path;

    public TestMob(Vec3f pos, Mob_Types type) {
        super("Test Mob" , type, pos, 10f, 100.0f);
    }

    @Override
    public float calculateDamage(float init_dmg) {
        return init_dmg;
    }

    @Override
    public Vec3f getNextTarget() {
        for (Player player : Bukkit.getOnlinePlayers()){
            Vec3f position = new Vec3f(player.getLocation());
            if(position.distance3d(super.getPos()) > 3)
                return position;
        }
        return null;
    }
}
