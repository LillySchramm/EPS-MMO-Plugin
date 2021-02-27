package de.epsdev.plugins.MMO.particles;

import de.epsdev.plugins.MMO.tools.Vec3f;
import net.minecraft.server.v1_12_R1.Vec3D;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class EF_Particle_Pillar extends Particle_Effect{
    public double radius;
    public int ringPoints;
    public float height;
    public int rings;

    public EF_Particle_Ring ef_ring;

    public EF_Particle_Pillar(ParticleConfig config, double radius, int ringPoints, float height, int rings) {
        super(config);
        super.name = "pillar";

        this.radius = radius;
        this.ringPoints = ringPoints;
        this.height = height;
        this.rings = rings;
        ef_ring = new EF_Particle_Ring(config,radius,ringPoints);

    }

    @Override
    public void display(Location location) {
        Vec3f loc = new Vec3f(location);
        float step_size = height/rings;

        for (int i = 0; i < rings; i++){
            ef_ring.display(loc.toLocation());
            loc.y += step_size;
        }
    }

    @Override
    public String genData() {
        String ret = super.name + ">>" + super.config.particle.name() + ">>" + this.radius + ">>" + this.ringPoints +
                ">>" + this.height + ">>" + this.rings;

        if(super.config.color != null){
            ret += ">>" + super.config.color.o_r;
            ret += ">>" + super.config.color.o_g;
            ret += ">>" + super.config.color.o_b;
        }

        return ret;
    }
}
