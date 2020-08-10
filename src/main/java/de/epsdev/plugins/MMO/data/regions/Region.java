package de.epsdev.plugins.MMO.data.regions;

import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.regions.cites.City;

import java.util.ArrayList;
import java.util.List;

public class Region {
    public String name;
    public int level;
    public int id;

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

}
