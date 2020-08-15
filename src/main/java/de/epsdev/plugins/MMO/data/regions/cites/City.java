package de.epsdev.plugins.MMO.data.regions.cites;

import de.epsdev.plugins.MMO.GUI.City_Detail_GUI;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.regions.Region;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class City {
    public String name;
    public int id;

    public List<House> houses;

    public Region region;
    public City_Detail_GUI gui;

    public City(int id, String name){
        this.name = name;
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void initGui(Region region){
        this.region = region;
        this.gui = new City_Detail_GUI(this,region);
    }

    public void save(){

            try {
                Path path = Paths.get("plugins/eps/regions/cities/"+ id +".txt");
                if(!Files.exists(path)){
                    Files.createFile(path);
                }
                FileWriter writer = new FileWriter("plugins/eps/regions/cities/"+ id +".txt");
                writer.write(name + ";;");
                writer.write(region.id + ";;");
                writer.close();
                DataManager.reloadRegions();

            }catch (IOException e){
                e.printStackTrace();
            }

    }

    public void delete(){
        try {
            Path path = Paths.get("plugins/eps/regions/cities/"+ id +".txt");
            Files.delete(path);
            DataManager.reloadRegions();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
