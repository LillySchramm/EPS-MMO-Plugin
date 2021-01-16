package de.epsdev.plugins.MMO.tools;

import de.epsdev.plugins.MMO.data.output.Out;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class WorldTools {

    public static void fillBlocks(ArrayList<Vec3i> block, Material material){
        for(Vec3i vec3i : block){
            Location location = new Location(Bukkit.getServer().getWorlds().get(0),vec3i.x,vec3i.y,vec3i.z);
            location.getBlock().setType(material);

        }
    }
}
