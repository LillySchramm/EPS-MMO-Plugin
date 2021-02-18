package de.epsdev.plugins.MMO.tools;

import de.epsdev.plugins.MMO.data.output.Out;
import net.minecraft.server.v1_12_R1.EntityArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

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
