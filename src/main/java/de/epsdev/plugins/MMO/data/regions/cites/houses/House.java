package de.epsdev.plugins.MMO.data.regions.cites.houses;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.money.Money;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.regions.cites.City;
import de.epsdev.plugins.MMO.tools.Vec3i;
import de.epsdev.plugins.MMO.tools.signs.ISign;
import org.bukkit.ChatColor;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class House {
    public Money costs;
    public int id;
    public String currentOwner_UUID = "0";
    public String name = "";

    public List<Vec3i> blocksInside;
    public List<Vec3i> doors;
    public ISign shield;
    public Vec3i spawnPossition;

    public City city;


    public House(City city){
        this.city = city;
        this.blocksInside = new ArrayList<>();
        this.doors = new ArrayList<>();
        this.costs = new Money(0);
    }

    public House(Money costs, int id, String currentOwner_UUID, String name, List<Vec3i> blocksInside, List<Vec3i> doors, Vec3i shield, Vec3i spawnPossition) {
        this.costs = costs;
        this.id = id;
        this.currentOwner_UUID = currentOwner_UUID;
        this.name = name;
        this.blocksInside = blocksInside;
        this.doors = doors;
        this.shield = new ISign(shield);
        this.spawnPossition = spawnPossition;
    }

    public void save(){
        try {
            Path path = Paths.get("plugins/eps/regions/cities/houses/"+ id +".txt");
            if(!Files.exists(path)){
                Files.createFile(path);
            }
            FileWriter writer = new FileWriter("plugins/eps/regions/cities/houses/"+ id +".txt");
            //HouseName;HouseCost;CurrentOwnerUUID;BlocksInside;Doors;Spawnpoint;Shield;

            writer.write(this.name + ";;");
            writer.write(this.costs.amount + ";;");
            writer.write(this.currentOwner_UUID + ";;");

            String temp = "";

            for(Vec3i vec : this.blocksInside){
                temp += vec.x + ">>" + vec.y + ">>" + vec.z + ">>";
            }

            writer.write(temp + ";;");

            temp = "";

            for(Vec3i vec : this.doors){
                temp += vec.x + ">>" + vec.y + ">>" + vec.z + ">>";
            }

            writer.write(temp + ";;");

            writer.write(this.spawnPossition.x + ">>" + this.spawnPossition.y + ">>" + this.spawnPossition.z + ";;");

            writer.write(this.shield.pos.x + ">>" + this.shield.pos.y + ">>" + this.shield.pos.z + ";;");

            writer.close();

            DataManager.reloadRegions();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void processArray(Vec3i pos, boolean deleted, List<Vec3i> list){
        boolean found = false;
        for (Vec3i vec3i : list) {
            if(vec3i != null) {
                if (vec3i.x == pos.x && vec3i.y == pos.y && vec3i.z == pos.z) {
                    found = true;
                    list.remove(vec3i);
                    break;
                }
            }
        }
        if (!found && !deleted){
            list.add(pos);
        }
    }

    public void processBlock(Vec3i pos, boolean deleted){ processArray(pos,deleted,this.blocksInside); }
    public void processDoor(Vec3i pos, boolean deleted){ processArray(pos,deleted,this.doors); }

    public void updateSign(){
        shield.lines[0] = city.name;
        shield.lines[1] = name;

        if(currentOwner_UUID.equals("0")){
            shield.lines[2] = ChatColor.GREEN + "For rent";
        }

        shield.lines[3] = costs.formatString();

        shield.run();
    }
}
