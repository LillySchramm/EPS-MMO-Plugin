package de.epsdev.plugins.MMO.tools;

import org.bukkit.Location;

public class Vec2f {
    public float yaw = 0;
    public float pitch = 0;

    public Vec2f(float yaw, float pitch){
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Vec2f(){

    }

    public Vec2f(Location location){
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }

    public boolean equals(Vec2f pos){
        return this.yaw == pos.yaw && this.pitch == pos.pitch;
    }

    public String toString(){
        return "Yaw: " + yaw + " Pitch: " + pitch;
    }
}
