package de.epsdev.plugins.MMO.npc.mobs;

import net.minecraft.server.v1_12_R1.*;

public enum Mob_Types {
    ZOMBIE,
    SKELETON,
    CREEPER,
    ENDERMAN,
    ENDER_MITE,
    SPIDER,
    PIG,
    VILLAGER,
    VEX,
    VINDICATOR,
    COW,
    MUSHROOM_COW,
    SHEEP,
    CHICKEN,
    ILLAGER,
    IRON_GOLEM,
    SNOW_GOLEM,
    MAGMA_CUBE,
    SLIME,
    PIGMAN,
    ENDERDRAGON,
    BLAZE,
    BAT,
    CAVE_SPIDER,
    EVOKER,
    GHAST,
    HORSE,
    HORSE_DONKEY,
    HORSE_ZOMBIE,
    HORSE_SKELETON,
    HORSE_MULE,
    GUARDIAN,
    GUARDIAN_ELDER,
    OCELOT,
    PARROT,
    ICE_BEAR,
    SILVERFISH,
    WITCH,
    WOLF,
    SKELETON_WITHER,
    WITHER;

    public static Entity get(Mob_Types t, World w){
        switch (t){
            case ZOMBIE:
                return new EntityZombie(w);
            case SKELETON:
                return new EntitySkeleton(w);
            case SPIDER:
                return new EntitySpider(w);
            case ENDERMAN:
                return new EntityEnderman(w);
            case ENDER_MITE:
                return new EntityEndermite(w);
            case PIG:
                return new EntityPig(w);
            case CREEPER:
                return new EntityCreeper(w);
            case VILLAGER:
                return new EntityVillager(w);
            case VEX:
                return new EntityVex(w);
            case VINDICATOR:
                return new EntityVindicator(w);
            case COW:
                return new EntityCow(w);
            case MUSHROOM_COW:
                return new EntityMushroomCow(w);
            case SHEEP:
                return new EntitySheep(w);
            case CHICKEN:
                return new EntityChicken(w);
            case ILLAGER:
                return new EntityIllagerIllusioner(w);
            case IRON_GOLEM:
                return new EntityIronGolem(w);
            case SNOW_GOLEM:
                return new EntitySnowman(w);
            case SLIME:
                return new EntitySlime(w);
            case PIGMAN:
                return new EntityPigZombie(w);
            case MAGMA_CUBE:
                return new EntityMagmaCube(w);
            case ENDERDRAGON:
                return new EntityEnderDragon(w);
            case BLAZE:
                return new EntityBlaze(w);
            case BAT:
                return new EntityBat(w);
            case WOLF:
                return new EntityWolf(w);
            case GHAST:
                return new EntityGhast(w);
            case HORSE:
                return new EntityHorse(w);
            case WITCH:
                return new EntityWitch(w);
            case EVOKER:
                return new EntityEvoker(w);
            case OCELOT:
                return new EntityOcelot(w);
            case PARROT:
                return new EntityParrot(w);
            case WITHER:
                return new EntityWither(w);
            case GUARDIAN:
                return new EntityGuardian(w);
            case GUARDIAN_ELDER:
                return new EntityGuardianElder(w);
            case ICE_BEAR:
                return new EntityPolarBear(w);
            case SILVERFISH:
                return new EntitySilverfish(w);
            case CAVE_SPIDER:
                return new EntityCaveSpider(w);
            case SKELETON_WITHER:
                return new EntitySkeletonWither(w);
            case HORSE_DONKEY:
                return new EntityHorseDonkey(w);
            case HORSE_MULE:
                return new EntityHorseMule(w);
            case HORSE_ZOMBIE:
                return new EntityHorseZombie(w);
            case HORSE_SKELETON:
                return new EntityHorseSkeleton(w);
        }
        return null;
    }

}
