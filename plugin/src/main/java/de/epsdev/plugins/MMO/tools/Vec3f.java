package de.epsdev.plugins.MMO.tools;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PathPoint;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

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

    public Vec3f(Vec3f v){
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public Vec3f(PathPoint v){
        this.x = (float) v.a;
        this.y = (float) v.b;
        this.z = (float) v.c;
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
        float distance = (float) Math.sqrt((this.x-arg.x)*(this.x-arg.x) + (this.z-arg.z)*(this.z-arg.z) + (this.y-arg.y)*(this.y-arg.y));
        return distance;
    }

    public void rotateAroundPointX(Vec3f origin, float amount) {
        double z1 = z - origin.z;
        double y1 = y - origin.y;

        double z2 = z1 * Math.cos(Math.toRadians(amount)) - y1 * Math.sin(Math.toRadians(amount));
        double y2 = z1 * Math.sin(Math.toRadians(amount)) + y1 * Math.cos(Math.toRadians(amount));

        y = (float) y2 + origin.y;
        z = (float) z2 + origin.z;
    }

    public void rotateAroundPointY(Vec3f origin, float amount) {
        double x1 = x - origin.x;
        double z1 = z - origin.z;

        double x2 = x1 * Math.cos(Math.toRadians(amount)) - z1 * Math.sin(Math.toRadians(amount));
        double z2 = x1 * Math.sin(Math.toRadians(amount)) + z1 * Math.cos(Math.toRadians(amount));

        x = (float) x2 + origin.x;
        z = (float) z2 + origin.z;
    }

    public static List<Vec3f> generatePointsBetween(Vec3f from, Vec3f to, float amountPerBlock){
        List<Vec3f> points = new ArrayList<>();

        Vec3f dir = Vec3f.getDirectionVec(from, to);
        Vec3f loc = new Vec3f(from);
        for (float i = 0.1f; i < from.distance3d(to) && i < DataManager.MAX_LOS_DISTANCE; i += 1f/amountPerBlock) {
            dir = Vec3f.multiply(dir, i);
            loc = Vec3f.add(dir, loc);

            points.add(loc);

            loc = Vec3f.subtract(loc, dir);
            dir.normalize();
        }

        return points;
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

    public double getAngleTowards(Vec3f target){
        double angle = Math.toDegrees(Math.atan2(target.x - x, target.z - z));
        //NOTE: Not doing target.y - y due to the way minecrafts coords work. Y is the height.

        angle = angle + Math.ceil( -angle / 360 ) * 360;
        return -angle;
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

    public BlockPosition toBlockposition(){
        return new BlockPosition(this.x,this.y, this.z);
    }
}
