package de.epsdev.plugins.MMO.ranks;

public class Rank {
    public boolean canBuild = false;
    public boolean canHandleMoney = false;
    public boolean canChangeRanks = false;

    public String name;
    public String prefix;

    public Rank(String name, String prefix ,boolean[] args){
        canBuild = args[0];
        canHandleMoney = args[1];
        canChangeRanks = args[2];
        this.name = name;
        this.prefix = prefix;

    }
}
