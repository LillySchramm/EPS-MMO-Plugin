package de.epsdev.plugins.MMO.combat.basetypes.attacks;

import de.epsdev.plugins.MMO.combat.Attackable;
import de.epsdev.plugins.MMO.combat.basetypes.Base_Self_Attack;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class Test_Self_Attack extends Base_Self_Attack {
    private int anim_scheduler;

    public Test_Self_Attack() {
        super(ChatColor.DARK_GREEN + "Test_Self_Attack",
                50L,
                1,
                1,
                30,
                10,
                "An extremely powerful attack. Just as good as lorem ipsum");
    }

    // TODO: I need to create a better way to do stuff like this
    private void cancelPAnimation(){
        Bukkit.getScheduler().cancelTask(anim_scheduler);
    }

    @Override
    public void pAnimation(Entity e) {
    }

    @Override
    public void pHitAnimation(Entity e) {

    }

    @Override
    public List<Attackable> getTargets(Attackable p) {
        return null;
    }
}
