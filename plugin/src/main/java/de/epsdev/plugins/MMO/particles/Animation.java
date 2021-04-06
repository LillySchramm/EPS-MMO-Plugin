package de.epsdev.plugins.MMO.particles;

import java.util.List;

public class Animation {
    public int fps;
    public float secPerFrame;

    private List<List<Particle_Effect>> frames;

    public Animation(int fps){
        this.fps = fps;
        this.secPerFrame = (float) 1 / fps;
    }

    public void addFrame(List<Particle_Effect> frame){
        frames.add(frame);
    }

    public List<Particle_Effect> getFrame(int i){
        return frames.get(i);
    }
}
