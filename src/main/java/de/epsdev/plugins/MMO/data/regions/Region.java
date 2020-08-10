package de.epsdev.plugins.MMO.data.regions;

import de.epsdev.plugins.MMO.data.regions.cites.City;

import java.util.List;

public class Region {
    public String name;
    public int level;
    public int id;

    public List<City> cities;

    public Region(String name, int level){
        this.name = name;
        this.level = level;
    }

}
