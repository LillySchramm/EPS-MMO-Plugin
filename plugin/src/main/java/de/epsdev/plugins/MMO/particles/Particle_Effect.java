package de.epsdev.plugins.MMO.particles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public abstract class Particle_Effect {

    public ParticleConfig config;
    public String name;

    public Particle_Effect(ParticleConfig config){
        this.config = config;
    }

    public void renderParticle(Location location){
        location.getWorld().spawnParticle(config.particle,location, config.density,config.getArg(0),config.getArg(1),config.getArg(2));
    }

    public void display(Location location){
    }

    public abstract String genData();
}
