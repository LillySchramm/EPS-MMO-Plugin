package de.epsdev.plugins.MMO.particles;

import java.util.List;

public class AnimationPlayer {
    private Animation animation;
    private int currentFrame = 0;

    private float delta = 0.0f;

    public AnimationPlayer(Animation animation){
        this.animation = animation;
    }

    public List<Particle_Effect> getCurrentFrame(){
        return this.animation.getFrame(currentFrame);
    }

    public boolean isAlive(){
        return currentFrame <= animation.fps;
    }

    public void update(float delta){
        this.delta =+ delta;

        if (this.delta >= animation.secPerFrame){
            this.delta = this.delta - animation.secPerFrame;

            this.currentFrame++;
        }
    }
}
