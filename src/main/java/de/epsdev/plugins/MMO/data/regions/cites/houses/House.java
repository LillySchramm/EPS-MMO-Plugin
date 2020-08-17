package de.epsdev.plugins.MMO.data.regions.cites.houses;

import de.epsdev.plugins.MMO.tools.Vec3i;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class House {
    public int costs;
    public int id;
    public String currentOwner_UUID;
    public String name;

    public List<Vec3i> blocksInside;
    public List<Vec3i> doors;
    public Vec3i shield;
    public Vec3i spawnPossition;


    public House(int costs, int id, String currentOwner_UUID, String name, List<Vec3i> blocksInside, List<Vec3i> doors, Vec3i shield, Vec3i spawnPossition) {
        this.costs = costs;
        this.id = id;
        this.currentOwner_UUID = currentOwner_UUID;
        this.name = name;
        this.blocksInside = blocksInside;
        this.doors = doors;
        this.shield = shield;
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
            writer.write(this.costs + ";;");
            writer.write(this.currentOwner_UUID + ";;");

            String temp = "";

            for(Vec3i vec : this.blocksInside){
                temp += vec.x + ">>" + vec.y + ">>" + vec.z;
            }

            writer.write(temp + ";;");

            temp = "";

            for(Vec3i vec : this.doors){
                temp += vec.x + ">>" + vec.y + ">>" + vec.z;
            }

            writer.write(temp + ";;");

            writer.write(this.spawnPossition.x + ">>" + this.spawnPossition.y + ">>" + this.spawnPossition.z + ";;");

            writer.write(this.shield.x + ">>" + this.shield.y + ">>" + this.shield.z + ";;");

            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
