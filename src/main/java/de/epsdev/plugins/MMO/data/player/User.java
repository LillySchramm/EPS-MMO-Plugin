package de.epsdev.plugins.MMO.data.player;

import de.epsdev.plugins.MMO.data.money.Money;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.ranks.Rank;
import de.epsdev.plugins.MMO.ranks.Ranks;
import de.epsdev.plugins.MMO.scoreboards.DefaultScroreboard;
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
    public Rank rank;

    public Money money;

    public User(Player player){
        displayName = player.getDisplayName();
        UUID = player.getUniqueId().toString();
        level = 1;
        money = new Money(0);
        this.rank = Ranks.Player;
    }

    public User(String displayName, String UUID, float xp, int level, int money, Rank rank){
        this.displayName = displayName;
        this.UUID = UUID;
        this.xp = xp;
        this.level = level;
        this.money = new Money(money);
        this.rank = rank;

        DefaultScroreboard.refresh(this);

    }

    public void save(){
        try {
            Path path = Paths.get("plugins/eps/players/"+ UUID +".txt");
            if(!Files.exists(path)){
                Files.createFile(path);
            }
            FileWriter writer = new FileWriter("plugins/eps/players/"+ UUID +".txt");
            writer.write( displayName + ";;");
            writer.write(UUID + ";;");
            writer.write(xp + ";;");
            writer.write(level + ";;");
            writer.write(money.amount + ";;");
            writer.write(rank.name + ";;");
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
        Out.printToConsole("Money: " + money.amount);
        Out.printToConsole("Rank: " + rank.name);
    }


}
