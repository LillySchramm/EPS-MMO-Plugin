package de.epsdev.plugins.MMO.particles;

import de.epsdev.plugins.MMO.data.output.Out;
import org.bukkit.Location;
import org.bukkit.Particle;

public class EF_Single_Particle extends Particle_Effect {

    public EF_Single_Particle(ParticleConfig config){
        super(config);
    }

    @Override
    public void display(Location location) {
        super.renderParticle(location);
    }
}
