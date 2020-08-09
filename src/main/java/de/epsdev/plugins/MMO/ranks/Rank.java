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

    public String name;
    public String prefix;

    public int maxHousesForRent = 1;

    public Rank(String name, String prefix ,boolean[] args, int[] int_args){
        canBuild = args[0];
        canHandleMoney = args[1];
        canChangeRanks = args[2];
        canMuteChat = args[3];
        canSpeakWhenChatMuted = args[4];
        canUseCheatMenu = args[5];
        canReloadServer = args[6];
        canChangeGamemode = args[7];

        this.maxHousesForRent = int_args[0];

        this.name = name;
        this.prefix = prefix;
    }

}
