package de.epsdev.plugins.MMO.tools;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import org.bukkit.Bukkit;

import java.util.Date;

public class Delta {
    public float d = 0;

    private long lastTimestamp;

    public Delta(){
        lastTimestamp = new Date().getTime();
    }

    public void update(){
        long newStamp = new Date().getTime();
        long diff = newStamp - lastTimestamp;

        d = (float) diff / 1000;

        lastTimestamp = newStamp;
    }
}
