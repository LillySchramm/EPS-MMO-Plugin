package de.epsdev.plugins.MMO.combat.basetypes.attacks;

import de.epsdev.plugins.MMO.combat.basetypes.Base_Self_Attack;
import org.bukkit.ChatColor;

public class Test_Attack extends Base_Self_Attack {

    public Test_Attack() {
        super(ChatColor.DARK_GREEN + "Test_Self_Attack",
                400L,
                1,
                1,
                30,
                10,
                "An extremely powerful attack. Just as good as lorem ipsum");
    }
}
