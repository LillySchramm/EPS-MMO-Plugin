package de.epsdev.plugins.MMO.particles;

import de.epsdev.plugins.MMO.tools.D_RGB;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public abstract class Particle_Effect {

    public ParticleConfig config;
    public String name;

    public Particle_Effect(ParticleConfig config){
        this.config = config;
    }

    public void renderParticle(Location location){
        location.getWorld().spawnParticle(config.particle,location, config.density,config.getArg(0),config.getArg(1),config.getArg(2));
    }

    public abstract void display(Location location);

    public abstract String genData();

    public static Particle_Effect genFromData(String data){
        Particle_Effect effect = null;
        ParticleConfig config = null;
        Particle particle = null;
        D_RGB color = null;

        String[] _data = data.split(">>");

        switch (_data[0]){
            case "circle":
                particle = Particle.valueOf(_data[1]);
                color = null;

                double radius = Double.parseDouble(_data[2]);
                int points = Integer.parseInt(_data[3]);

                if(_data.length > 4){
                    int r = Integer.parseInt(_data[4]);
                    int g = Integer.parseInt(_data[5]);
                    int b = Integer.parseInt(_data[6]);

                    color = new D_RGB(r, g, b);
                }

                config = new ParticleConfig(particle, color);
                effect = new EF_Particle_Ring(config, radius, points);

                break;
            case "single":
                particle = Particle.valueOf(_data[1]);
                color = null;

                if(_data.length > 2){
                    int r = Integer.parseInt(_data[2]);
                    int g = Integer.parseInt(_data[3]);
                    int b = Integer.parseInt(_data[4]);

                    color = new D_RGB(r, g, b);
                }

                config = new ParticleConfig(particle, color);
                effect = new EF_Single_Particle(config);

                break;
            case "pillar":
                particle = Particle.valueOf(_data[1]);
                color = null;

                double p_radius = Double.parseDouble(_data[2]);
                int p_points = Integer.parseInt(_data[3]);
                float p_height = Float.parseFloat(_data[4]);
                int p_rings = Integer.parseInt(_data[5]);

                if(_data.length > 6){
                    int r = Integer.parseInt(_data[6]);
                    int g = Integer.parseInt(_data[7]);
                    int b = Integer.parseInt(_data[8]);

                    color = new D_RGB(r, g, b);
                }

                config = new ParticleConfig(particle, color);
                effect = new EF_Particle_Pillar(config,p_radius,p_points,p_height,p_rings);

                break;
        }

        return effect;
    }
}
