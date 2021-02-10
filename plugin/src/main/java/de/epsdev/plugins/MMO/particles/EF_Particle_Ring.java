package de.epsdev.plugins.MMO.particles;

import de.epsdev.plugins.MMO.tools.Vec2d;
import de.epsdev.plugins.MMO.tools.Vec2f;
import org.bukkit.Location;
import org.bukkit.Particle;

import java.util.ArrayList;
import java.util.List;

public class EF_Particle_Ring extends Particle_Effect{

    public double radius;
    public int ringPoints;

    public EF_Particle_Ring(Particle type, int density, double radius, int points){
        super(type, density);

        this.radius = radius;
        this.ringPoints = points;
    }

    private List<Vec2d> calcOffsets(){
        List<Vec2d> offsets = new ArrayList<>();

        for (float i = 0; i < 360; i+=(360 / this.ringPoints)) {
            double x = radius * Math.cos(Math.toRadians(i));
            double y = radius * Math.sin(Math.toRadians(i));

            offsets.add(new Vec2d(x,y));
        }

        return offsets;
    }

    @Override
    public void display(Location location) {

        List<Vec2d> offsets = this.calcOffsets();

        for (Vec2d offset : offsets){
            Location l = new Location(location.getWorld(), location.getX() + offset.x, location.getY(), location.getZ() + offset.y);
            super.renderParticle(l);
        }

    }
}
