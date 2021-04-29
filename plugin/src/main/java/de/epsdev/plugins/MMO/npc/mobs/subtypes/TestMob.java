package de.epsdev.plugins.MMO.npc.mobs.subtypes;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.npc.Path;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import de.epsdev.plugins.MMO.npc.mobs.Mob_Types;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.Bukkit;

public class TestMob extends Base_Mob {

    private Path path;

    public TestMob(Vec3f pos) {
        super("Test Mob" ,Mob_Types.ZOMBIE, pos, 2.5f, 100.0f);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, this::genAndShowPath, 0L, 10L);
    }

    public void genAndShowPath(){
        for (User user : DataManager.onlineUsers.values()) {
            this.path = new Path(super.getPos(), new Vec3f(user.getPlayer().getLocation()));
            this.path.draw();
        }
    }

    @Override
    public float calculateDamage(float init_dmg) {
        return init_dmg;
    }
}
