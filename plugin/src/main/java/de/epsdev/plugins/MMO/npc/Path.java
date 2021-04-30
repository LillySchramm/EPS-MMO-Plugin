package de.epsdev.plugins.MMO.npc;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.particles.EF_Line;
import de.epsdev.plugins.MMO.particles.ParticleConfig;
import de.epsdev.plugins.MMO.tools.D_RGB;
import de.epsdev.plugins.MMO.tools.Vec3f;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private Vec3f start;
    private Vec3f goal;
    private Entity entity;

    private List<Vec3f> waypoints;

    public Path(Vec3f start, Vec3f goal, Entity entity){
        this.start = start;
        this.goal = goal;
        this.entity = entity;

        recalculate();
    }

    public void recalculate(){
        this.waypoints = new ArrayList<>();

        PathfinderNormal normal = new PathfinderNormal();
        normal.a(true);
        Pathfinder pathfinder = new Pathfinder(normal);

        int k = (int) (DataManager.MAX_DETECTION_RANGE + 8.0);
        ChunkCache chunkCache = new ChunkCache(((CraftWorld) Bukkit.getWorld("world")).getHandle() , this.start.toBlockposition().a(-k, -k, -k),  this.goal.toBlockposition().a(k, k, k), 0);

        PathEntity path = pathfinder.a(chunkCache, (EntityInsentient) entity, goal.toBlockposition(), (float) DataManager.MAX_DETECTION_RANGE);

        if(path != null){
            for (int i = 0; i < path.d(); i++) {
                PathPoint p = path.a(i);
                this.waypoints.add(new Vec3f(p));
            }
        }
    }

    public Vec3f getCurrentWayPoint(){
        if(this.waypoints.isEmpty()) return null;
        return this.waypoints.get(0);
    }

    public boolean next(){
        if(this.waypoints.isEmpty()){
            return false;
        }
        this.waypoints.remove(0);
        return true;
    }

    public void draw(){
        for (int i = 1; i < waypoints.size(); i++) {
            new EF_Line(new ParticleConfig(Particle.REDSTONE, new D_RGB(0,0,0)), waypoints.get(i), waypoints.get(i-1)).display(null);
        }
    }
}
