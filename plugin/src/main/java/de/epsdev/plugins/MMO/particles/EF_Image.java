package de.epsdev.plugins.MMO.particles;

import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.tools.D_RGB;
import de.epsdev.plugins.MMO.tools.Math;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.Location;
import org.bukkit.Particle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class EF_Image extends Particle_Effect{

    private String image_data;
    private BufferedImage img;
    private float pixel_offset;
    private int rot_offset = 1;

    public List<List<EF_Single_Particle>> pixels;

    public EF_Image( String image_data, float pixel_offset) {
        super(new ParticleConfig(Particle.REDSTONE));

        this.image_data = image_data;
        this.img = Math.base64_toImage(image_data);
        this.pixel_offset = pixel_offset;
        this.pixels = new ArrayList<>();

        genPixels();
        genCache();
    }

    private void genPixels(){
        for (int collum = 0; collum < img.getHeight(); collum++) {

            pixels.add(new ArrayList<>());

            for (int row = 0; row < img.getWidth(); row++) {
                Color color = new Color(img.getRGB(row, collum), true);

                if(color.getAlpha() == 255){
                    D_RGB d_color = new D_RGB(color.getRed(), color.getGreen(), color.getBlue());
                    EF_Single_Particle pixel = new EF_Single_Particle(new ParticleConfig(Particle.REDSTONE, d_color));
                    pixels.get(collum).add(pixel);
                }else pixels.get(collum).add(null);
            }
        }
    }

    private void genCache(){
        Vec3f rot_point = new Vec3f();
        Out.printToConsole(rot_point.toString());
        this.cache.put("rot_self", new TreeMap<>());

        for (int i = 0; i < 360; i++) {

            int i_row = 0;
            int i_collum = 0;

            List<List<ParticleConfig>> tmp_collum = new ArrayList<>();
            for (List<EF_Single_Particle> row : pixels) {
                List<ParticleConfig> tmp_row = new ArrayList<>();
                for (EF_Single_Particle pixel : row) {
                    if (pixel != null) {
                        Vec3f p = new Vec3f();

                        p.z = i_row * pixel_offset - (float) img.getHeight() * pixel_offset / 2;
                        p.x = i_collum * pixel_offset - (float) img.getWidth() * pixel_offset / 2;

                        p.rotateAroundPointY(rot_point, i);

                        ParticleConfig config = new ParticleConfig(pixel.config.particle, pixel.config.color);
                        config.offset = p;

                        tmp_row.add(config);
                    }
                    i_collum += 1;
                }
                tmp_collum.add(tmp_row);

                i_collum = 0;
                i_row += 1;
            }

            this.cache.get("rot_self").put(i, tmp_collum);
        }
    }

    public void setRot_offset(int offset){
        if(offset < 0){
            offset = 360 + offset;
        }

        this.rot_offset = Math.minmax(0,360,offset);
    }

    @Override
    public void display(Location location) {
        List<List<ParticleConfig>> cc = this.cache.get("rot_self").get(this.rot_offset);

        for (List<ParticleConfig> c : cc){
            for (ParticleConfig p : c){
                Vec3f offset = Vec3f.add(p.offset, new Vec3f(location));
                new EF_Single_Particle(p).display(offset.toLocation());
            }
        }
    }

    @Override
    public String genData() {
        return null;
    }
}
