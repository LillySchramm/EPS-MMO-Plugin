package de.epsdev.plugins.MMO.tools;

import org.bukkit.Location;

public class Vec3f {
    public float x = 0;
    public float y = 0;
    public float z = 0;

    public Vec3f(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3f(){

    }

    public Vec3f(Location location){
        this.x = (float) location.getX();
        this.y = (float) location.getY();
        this.z = (float) location.getZ();
    }

    public boolean equals(Vec3f pos){
        return this.x == pos.x && this.y == pos.y && this.z == pos.z;
    }

    public String toString(){
        return "X: " + x + " Y: " + y + " Z: " + z;
    }
}
