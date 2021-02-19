package de.epsdev.plugins.MMO.particles;

import org.bukkit.Location;


public class EF_Single_Particle extends Particle_Effect {

    public EF_Single_Particle(ParticleConfig config){
        super(config);

        super.name = "single";
    }

    @Override
    public void display(Location location) {
        super.renderParticle(location);
    }

    @Override
    public String genData() {
        String ret = super.name + ">>" + super.config.particle.name();

        if(super.config.color != null){
            ret += ">>" + super.config.color.o_r;
            ret += ">>" + super.config.color.o_g;
            ret += ">>" + super.config.color.o_b;
        }

        return ret;
    }
}
