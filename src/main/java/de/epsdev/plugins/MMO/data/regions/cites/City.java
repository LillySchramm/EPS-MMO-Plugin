package de.epsdev.plugins.MMO.data.regions.cites;

import de.epsdev.plugins.MMO.data.regions.cites.houses.House;

import java.util.List;

public class City {
    public String name;
    public int id;

    public List<House> houses;

    public City(String name){
        this.name = name;
    }

}
