package de.epsdev.plugins.MMO.tools;

import org.bukkit.Location;

public class Vec3i {
    public int x = 0;
    public int y = 0;
    public int z = 0;

    public Vec3i(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3i(){

    }

    public Vec3i(Location location){
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public boolean equals(Vec3i pos){
        return this.x == pos.x && this.y == pos.y && this.z == pos.z;
    }

    public String toString(){
        return "X: " + x + " Y: " + y + " Z: " + z;
    }
}
