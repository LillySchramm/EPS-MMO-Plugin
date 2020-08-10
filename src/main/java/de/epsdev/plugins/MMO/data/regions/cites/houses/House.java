package de.epsdev.plugins.MMO.data.regions.cites.houses;

import de.epsdev.plugins.MMO.tools.Vec3i;

import java.util.List;

public class House {
    public int costs;
    public int id;
    public String currentOwner_UUID;

    public List<Vec3i> blocksInside;
    public List<Vec3i> doors;
    public Vec3i shield;

    public House(){

    }

}
