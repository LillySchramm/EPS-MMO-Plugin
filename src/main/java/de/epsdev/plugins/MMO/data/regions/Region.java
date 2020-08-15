package de.epsdev.plugins.MMO.data.regions;

import de.epsdev.plugins.MMO.GUI.City_GUI;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.regions.cites.City;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Region {
    public String name;
    public int level;
    public int id;
    public int index;

    public City_GUI city_gui;

    public List<City> cities = new ArrayList<>();

    public Region(String name, int level){
        this.name = name;
        this.level = level;
    }


    public void print(){
        Out.printToConsole("Name: "+name);
        Out.printToConsole("Level: " + level);
        Out.printToConsole("ID: " + id);
    }

    public int getId(){
     return id;
    }

    public void save(){

            try {
                Path path = Paths.get("plugins/eps/regions/"+ id +".txt");
                if(!Files.exists(path)){
                    Files.createFile(path);
                }
                FileWriter writer = new FileWriter("plugins/eps/regions/"+ id +".txt");
                writer.write(name + ";;");
                writer.write(level + ";;");
                writer.close();

            }catch (IOException e){
                e.printStackTrace();
            }


    }

}
