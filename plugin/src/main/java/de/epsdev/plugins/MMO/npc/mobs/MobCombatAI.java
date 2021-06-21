package de.epsdev.plugins.MMO.npc.mobs;

import de.epsdev.plugins.MMO.combat.Attack;
import de.epsdev.plugins.MMO.combat.basetypes.Base_Melee;
import de.epsdev.plugins.MMO.data.output.Out;

public class MobCombatAI {
    public static void chooseAttack(Base_Mob base_mob){
        for(Attack attack : base_mob.attackCollection.attacks){
            if(!attack.onCooldown){
                if(attack instanceof Base_Melee){
                    if(Mob_Manager.getAllEnemiesInRange(base_mob.getPos(),((Base_Melee) attack).RANGE).size() > 1){
                        attack.executeAttack(base_mob);
                    }
                }
            }
        }
    }
}
