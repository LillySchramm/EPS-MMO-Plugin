package de.epsdev.plugins.MMO.tools;

import org.bukkit.Location;

public class Vec2d {
    public double x = 0;
    public double y = 0;

    public Vec2d(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vec2d(){

    }

    public boolean equals(Vec2d pos){
        return this.x == pos.x && this.y == pos.y;
    }

    public String toString(){
        return "X: " + x + " Y: " + y;
    }
}
