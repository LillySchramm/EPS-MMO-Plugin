package de.epsdev.plugins.MMO.data.player;

import de.epsdev.plugins.MMO.data.output.Out;
import org.bukkit.entity.Player;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class User {
    public String displayName;
    public String UUID;
    public float xp;
    public int level;

    public User(Player player){
        displayName = player.getDisplayName();
        UUID = player.getUniqueId().toString();
    }

    public User(String displayName, String UUID, float xp, int level){
        this.displayName = displayName;
        this.UUID = UUID;
        this.xp = xp;
        this.level = level;
    }

    public void save(){
        try {
            Path path = Paths.get("plugins/eps/players/"+ UUID +".txt");
            Files.createFile(path);
            FileWriter writer = new FileWriter("plugins/eps/players/"+ UUID +".txt");
            writer.write( displayName + ";;");
            writer.write(UUID + ";;");
            writer.write(xp + ";;");
            writer.write(level + ";;");
            writer.close();
        }catch (IOException e){
             e.printStackTrace();
        }

    }

    public void print(){
        Out.printToConsole("Display Name: " + displayName);
        Out.printToConsole("UUID: " + UUID);
        Out.printToConsole("XP: " + xp);
        Out.printToConsole("Level: " + level);
    }


}
