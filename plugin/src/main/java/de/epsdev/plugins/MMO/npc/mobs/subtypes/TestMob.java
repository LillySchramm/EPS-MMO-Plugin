package de.epsdev.plugins.MMO.npc.mobs.subtypes;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.npc.Path;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import de.epsdev.plugins.MMO.npc.mobs.Mob_Types;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TestMob extends Base_Mob {

    private Path path;

    public TestMob(Vec3f pos) {
        super("Test Mob" ,Mob_Types.ZOMBIE, pos, 2.5f, 100.0f);
    }

    @Override
    public float calculateDamage(float init_dmg) {
        return init_dmg;
    }

    @Override
    public Vec3f getNextTarget() {
        for (Player player : Bukkit.getOnlinePlayers()){
            return new Vec3f(player.getLocation());
        }
        return null;
    }
}
