package de.epsdev.plugins.MMO.data.regions.cites;

import de.epsdev.plugins.MMO.GUI.City_Detail_GUI;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.data.regions.Region;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
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

        this.houses = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void initGui(Region region){
        this.region = region;
        this.gui = new City_Detail_GUI(this,region);
    }

    public House getHouseByName(String name){
        for(House house : this.houses){
            if (house.name.equalsIgnoreCase(name)) return house;
        }
        return null;
    }

    public void save(){
            mysql.query("REPLACE INTO `eps_regions`.`cities` SET `ID` = "+ this.id + "," +
                    " `NAME` = '" + this.name + "', `REGION_ID` = " + this.region.id + ";");
            DataManager.reloadRegions();
    }

    public void delete(){
            mysql.query("DELETE FROM `eps_regions`.`cities` WHERE `ID` = "+ this.id + ";");
            DataManager.reloadRegions();
    }
}
