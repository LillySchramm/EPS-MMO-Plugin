package de.epsdev.plugins.MMO.particles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class Particle_Effect {

    public Particle type;
    public int density;

    public Particle_Effect(Particle type, int density){
        this.type = type;
        this.density = density;
    }

    public void renderParticle(Location location){
        location.getWorld().spawnParticle(type,location,density,0,0,0);
    }

    public void display(Location location){

        /*

               */

    }
}
