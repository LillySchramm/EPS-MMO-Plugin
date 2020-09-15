package de.epsdev.plugins.MMO.tools;

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

    public String toString(){
        return "X: " + x + " Y: " + y + " Z: " + z;
    }
}
