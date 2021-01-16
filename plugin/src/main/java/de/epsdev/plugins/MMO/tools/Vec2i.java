package de.epsdev.plugins.MMO.tools;

import org.bukkit.Location;

public class Vec2i {
    public int x = 0;
    public int y = 0;

    public Vec2i(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Vec2i(){

    }

    public boolean equals(Vec2i pos){
        return this.x == pos.x && this.y == pos.y;
    }

    public String toString(){
        return "X: " + x + " Y: " + y;
    }

    public void increase2D(int max_X){
        x++;

        if(x > max_X){
            x = 0;
            y ++;
        }
    }
}
