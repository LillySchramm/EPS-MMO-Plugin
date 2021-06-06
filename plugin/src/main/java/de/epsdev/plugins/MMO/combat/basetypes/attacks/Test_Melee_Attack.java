package de.epsdev.plugins.MMO.combat.basetypes.attacks;

import de.epsdev.plugins.MMO.combat.basetypes.Base_Melee;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class Test_Melee_Attack extends Base_Melee {
    public Test_Melee_Attack() {
        super(  "Cool Attack",
                50L,
                30,
                1,
                10,
                0,
                "An extremely powerful attack. Just as good as lorem ipsum");
    }

    @Override
    public void pAnimation(Entity e) {

    }

    @Override
    public void pHitAnimation(Entity e) {

    }
}
