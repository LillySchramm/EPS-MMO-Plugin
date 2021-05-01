package de.epsdev.plugins.MMO.npc;

import de.epsdev.plugins.MMO.data.DataManager;
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
    private final Vec3f start;
    private final Vec3f goal;
    private final World world;

    private final boolean interpolate;

    private List<Vec3f> waypoints;

    public Path(Vec3f start, Vec3f goal, World world, boolean interpolate){
        this.start = start;
        this.goal = goal;
        this.world = world;
        this.interpolate = interpolate;

        recalculate();
    }

    public void recalculate(){
        this.waypoints = new ArrayList<>();

        PathfinderNormal normal = new PathfinderNormal();
        normal.a(true);
        Pathfinder pathfinder = new Pathfinder(normal);

        int k = (int) (DataManager.MAX_DETECTION_RANGE + 8.0);
        ChunkCache chunkCache = new ChunkCache(((CraftWorld) Bukkit.getWorld("world")).getHandle() , this.start.toBlockposition().a(-k, -k, -k),  this.goal.toBlockposition().a(k, k, k), 3);

        EntityInsentient e = new EntityZombie(world);
        e.setPosition(start.x,(int) start.y,start.z);
        PathEntity path = pathfinder.a(chunkCache, e, goal.toBlockposition(), (float) DataManager.MAX_DETECTION_RANGE);

        waypoints.add(start);

        Vec3f last = new Vec3f();
        Vec3f _last = new Vec3f();
        if(path != null){
            for (int i = 0; i < path.d(); i++) {
                Vec3f p = new Vec3f(path.a(i));
                p.x += 0.5f;
                p.z += 0.5f;
                if(!Vec3f.getDirectionVec(last, p).equals(new Vec3f())){
                    if(!interpolate || !isClearPath(_last, p)){
                        if(_last.y != p.y && !_last.equals(new Vec3f())) this.waypoints.add(_last);
                        this.waypoints.add(p);
                    }
                }
                _last = p;
            }

            this.waypoints.add(_last);
        }
    }

    private boolean isClearPath(Vec3f from, Vec3f to){
        if(from.y != to.y) return false;
        List<Vec3f> line = Vec3f.generatePointsBetween(from,to,0.5f);

        for(Vec3f v : line){
            boolean isSolid = world.getWorld().getBlockAt(v.toLocation()).getType().isSolid();
            boolean isGroundSolid = world.getWorld().getBlockAt(new Vec3f(v.x, v.y - 1, v.z).toLocation()).getType().isSolid();

            if(isSolid || !isGroundSolid) return false;
        }

        return true;
    };

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
