package de.epsdev.plugins.MMO.particles;

import de.epsdev.plugins.MMO.tools.D_RGB;
import de.epsdev.plugins.MMO.tools.Vec3i;
import org.bukkit.Particle;

public class ParticleConfig {
    public Particle particle;
    public D_RGB color;
    public int density = 0;

    public ParticleConfig(Particle particle){
        this.particle = particle;
    }

    public ParticleConfig(Particle particle, D_RGB color){
        this.particle = particle;
        this.color = color;
    }

    public double getArg(int n){
        if(this.color != null && (this.particle == Particle.REDSTONE || this.particle == Particle.SPELL_MOB || this.particle == Particle.SPELL)){
            return this.color.toArray()[n];
        }

        return 0;
    }
}
