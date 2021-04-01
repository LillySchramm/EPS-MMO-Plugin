package de.epsdev.plugins.MMO.tools;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.lang.Math;

public class Vec3f {
    public float x = 0;
    public float y = 0;
    public float z = 0;

    public Vec3f(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3f(Vector v){
        this.x = (float) v.getX();
        this.y = (float) v.getY();
        this.z = (float) v.getZ();
    }

    public Vec3f(){

    }

    public Vec3f(Location location){
        this.x = (float) location.getX();
        this.y = (float) location.getY();
        this.z = (float) location.getZ();
    }

    public float distance2d(Vec3f arg){
        float distance = (float) Math.sqrt((this.x-arg.x)*(this.x-arg.x) + (this.z-arg.z)*(this.z-arg.z));
        return distance;
    }

    public float distance3d(Vec3f arg){
        float _x = arg.x;
        float _y = arg.y;
        float _z = arg.z;
        return (float) Math.sqrt((x-_x)*(x-_x)+(y-_y)*(y-_y)+(z-_z)*(z-_z));
    }

    public void normalize(){
        float length = (float) Math.sqrt(x*x + y*y + z*z);
        x = x/length;
        y = y/length;
        z = z/length;
    }

    public static Vec3f add(Vec3f v1, Vec3f v2){
        Vec3f v = new Vec3f();

        v.x = v1.x + v2.x;
        v.y = v1.y + v2.y;
        v.z = v1.z + v2.z;

        return v;
    }

    public static Vec3f subtract(Vec3f v1, Vec3f v2){
        Vec3f v = new Vec3f();

        v.x = v1.x - v2.x;
        v.y = v1.y - v2.y;
        v.z = v1.z - v2.z;

        return v;
    }

    public static Vec3f multiply(Vec3f v1, Vec3f v2){
        Vec3f v = new Vec3f();

        v.x = v1.x * v2.x;
        v.y = v1.y * v2.y;
        v.z = v1.z * v2.z;

        return v;
    }

    public static Vec3f multiply(Vec3f v1, float v2){
        Vec3f v = new Vec3f();

        v.x = v1.x * v2;
        v.y = v1.y * v2;
        v.z = v1.z * v2;

        return v;
    }

    public static Vec3f getDirectionVec(Vec3f startingPoint, Vec3f target){
        Vec3f d = Vec3f.subtract(target, startingPoint);
        d.normalize();
        return d;
    }

    public boolean equals(Vec3f pos){
        return this.x == pos.x && this.y == pos.y && this.z == pos.z;
    }

    public String toString(){
        return "X: " + x + " Y: " + y + " Z: " + z;
    }

    public Location toLocation(){
        return new Location(Bukkit.getWorld("world"), this.x, this.y, this.z);
    }
}
