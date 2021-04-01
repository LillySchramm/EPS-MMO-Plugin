package de.epsdev.plugins.MMO.npc.mobs;

import net.minecraft.server.v1_12_R1.*;

public enum Mob_Types {
    ZOMBIE,
    SKELETON,
    PIG;

    public static Entity get(Mob_Types t, World w){
        switch (t){
            case ZOMBIE:
                return new EntityZombie(w);
            case SKELETON:
                return new EntitySkeleton(w);
            case PIG:
                return new EntityPig(w);
        }
        return null;
    }

}
