package de.epsdev.plugins.MMO.tools.signs;

import de.epsdev.plugins.MMO.tools.Vec3i;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class ISign {

    public Vec3i pos;
    public Location location;

    public String[] lines = new String[]{"","","",""};

    public ISign(Vec3i pos){
        this.pos = pos;
        location = new Location(Bukkit.getServer().getWorlds().get(0),pos.x,pos.y,pos.z);
    }

    public void refresh(){
        Block block = location.getBlock();
        Sign s = (Sign) block.getState();
        for(int i = 0; i<4; i++){
            s.setLine(i,lines[i]);
        }
        s.update();
    }
}
