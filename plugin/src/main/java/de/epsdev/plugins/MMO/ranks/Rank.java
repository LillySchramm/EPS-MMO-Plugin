package de.epsdev.plugins.MMO.ranks;

public class Rank {
    public boolean canBuild = false;
    public boolean canHandleMoney = false;
    public boolean canChangeRanks = false;
    public boolean canMuteChat = false;
    public boolean canSpeakWhenChatMuted = false;
    public boolean canUseCheatMenu = false;
    public boolean canReloadServer = false;
    public boolean canChangeGamemode = false;
    public boolean canManageRegions = false;
    public boolean canManageHouses = false;
    public boolean canManageNPC = false;
    public boolean canManageStaticEffects = false;
    public boolean canManageItems = false;

    public String name;
    public String prefix;

    public int maxHousesForRent = 1;
    public int maxCharacters = 1;

    public Rank(String name, String prefix ,boolean[] args, int[] int_args){
        canBuild = args[0];
        canHandleMoney = args[1];
        canChangeRanks = args[2];
        canMuteChat = args[3];
        canSpeakWhenChatMuted = args[4];
        canUseCheatMenu = args[5];
        canReloadServer = args[6];
        canChangeGamemode = args[7];
        canManageRegions = args[8];
        canManageHouses = args[9];
        canManageNPC = args[10];
        canManageStaticEffects = args[11];
        canManageItems = args[12];

        this.maxHousesForRent = int_args[0];
        this.maxCharacters = int_args[1];

        this.name = name;
        this.prefix = prefix;
    }

}
