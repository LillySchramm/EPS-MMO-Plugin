package de.epsdev.plugins.MMO.combat.basetypes.attacks;

import de.epsdev.plugins.MMO.combat.basetypes.Base_Self_Attack;
import org.bukkit.ChatColor;

public class Test_Attack extends Base_Self_Attack {

    public Test_Attack(String name, long reloadTime, float baseDmg, float baseHeal, float manaCastCost, float liveCastCost, String description) {
        super(ChatColor.DARK_GREEN + "Test_Self_Attack",
                40L,
                1,
                1,
                20,
                2,
                "An extremely powerful attack. Just as good as lorem ipsum");
    }
}
