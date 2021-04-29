package de.epsdev.plugins.MMO.particles;

import de.epsdev.plugins.MMO.tools.Vec3f;
import de.epsdev.plugins.MMO.tools.Vec3i;
import org.bukkit.Location;

import java.util.List;

public class EF_Line extends Particle_Effect{

    private Vec3f from;
    private Vec3f to;

    public EF_Line(ParticleConfig config, Vec3f from, Vec3f to) {
        super(config);

        this.from = from;
        this.to = to;
    }

    @Override
    public void display(Location location) {
        List<Vec3f> points = Vec3f.generatePointsBetween(from, to, 5);

        for(Vec3f point : points){
            renderParticle(point.toLocation());
        }
    }

    @Override
    public String genData() {
        return "null";
    }
}
