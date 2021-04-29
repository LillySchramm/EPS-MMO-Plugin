package de.epsdev.plugins.MMO.particles;

import de.epsdev.plugins.MMO.data.output.Out;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public abstract class Animation {
    public int fps;

    private float secPerFrame;
    private int currentFrame = 0;
    private float delta = 0.0f;

    private List<List<Particle_Effect>> frames;

    public Animation(int fps){
        this.fps = fps;
        this.secPerFrame = (float) 1 / fps;

        this.frames = new ArrayList<>();
    }

    public Animation(int fps, List<List<Particle_Effect>> frames){
        this.fps = fps;
        this.secPerFrame = (float) 1 / fps;

        this.frames = frames;
    }

    public void addFrame(List<Particle_Effect> frame){
        frames.add(frame);
    }

    public List<Particle_Effect> getFrame(int i){
        return frames.get(i);
    }

    public abstract void generate();

    public void update(float delta){
        this.delta =+ delta;

        while (this.delta >= secPerFrame){
            this.delta = this.delta - secPerFrame;

            this.currentFrame++;
        }
    }

    public boolean isAlive(){
        return currentFrame < frames.size();
    }

    public void playAt(Location location){
        for(Particle_Effect e : getFrame(currentFrame)){
            e.display(location);
        }
    }

    public Animation clone(){
        Animation c = new Animation(this.fps, this.frames) {
            @Override
            public void generate() {

            }
        };
        return c;
    }
}
