package de.epsdev.plugins.MMO.particles;

import org.bukkit.Location;
import org.bukkit.Particle;

public class EF_Single_Particle extends Particle_Effect {

    EF_Single_Particle(Particle type, int density){
        super(type, density);
    }

    @Override
    public void display(Location location) {
        super.display(location);
    }
}
