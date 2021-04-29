package de.epsdev.plugins.MMO.npc;

import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.particles.EF_Line;
import de.epsdev.plugins.MMO.particles.ParticleConfig;
import de.epsdev.plugins.MMO.tools.D_RGB;
import de.epsdev.plugins.MMO.tools.Vec3f;
import de.epsdev.plugins.MMO.tools.Vec3i;
import org.bukkit.Particle;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private Vec3f start;
    private Vec3f goal;

    private List<Vec3f> waypoints;

    private final int maxJumpHeight = 1;

    public Path(Vec3f start, Vec3f goal){
        this.start = start;
        this.goal = goal;

        recalculate();
    }

    public void recalculate(){
        this.waypoints = new ArrayList<>();
        List<Vec3f> line = Vec3f.generatePointsBetween(start, goal, 2);

        this.waypoints.add(start);

        Vec3f last_point = start;
        for (Vec3f point : line){
            if(point.toLocation().getBlock().getType().isSolid()){
                this.waypoints.add(last_point);
            }

            last_point = point;
        }

        this.waypoints.add(goal);

    }

    public void draw(){
        for (int i = 1; i < waypoints.size(); i++) {
            new EF_Line(new ParticleConfig(Particle.REDSTONE, new D_RGB(0,0,0)), waypoints.get(i), waypoints.get(i-1)).display(null);
        }
    }
}
