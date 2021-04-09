package de.epsdev.plugins.MMO.particles.animations;

import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.particles.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ANIM_TestRotImg extends Animation {

    private EF_Image image;

    public ANIM_TestRotImg(int fps, EF_Image image) {
        super(fps);

        this.image = image;
        generate();
    }

    @Override
    public void generate() {
        TreeMap<Integer, List<List<ParticleConfig>>> rot = image.cache.get("rot_self");

        rot.forEach((integer, lists) -> {
            List<Particle_Effect> frame = new ArrayList<>();
            for (List<ParticleConfig> r : lists){
                for (ParticleConfig c : r) {
                    frame.add(new EF_Single_Particle(c));
                }
            }

            addFrame(frame);
        });
    }
}
