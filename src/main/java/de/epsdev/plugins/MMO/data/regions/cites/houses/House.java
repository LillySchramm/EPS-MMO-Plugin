package de.epsdev.plugins.MMO.data.regions.cites.houses;

import de.epsdev.plugins.MMO.GUI.Dev_house_detail;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.money.Money;
import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.regions.cites.City;
import de.epsdev.plugins.MMO.tools.Vec3i;
import de.epsdev.plugins.MMO.tools.WorldTools;
import de.epsdev.plugins.MMO.tools.signs.ISign;
import org.bukkit.ChatColor;
import org.bukkit.Material;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class House {
    public Money costs;
    public int id;
    public String currentOwner_UUID = "0";
    public String name = "";

    public ArrayList<Vec3i> blocksInside;
    public ArrayList<Vec3i> doors;
    public ISign shield;
    public Vec3i spawnPosition;

    public City city;

    public Dev_house_detail detail_gui;


    public House(City city){
        this.city = city;
        this.blocksInside = new ArrayList<>();
        this.doors = new ArrayList<>();
        this.costs = new Money(0);
    }

    public House(Money costs, int id, String currentOwner_UUID, String name, ArrayList<Vec3i> blocksInside,
                 ArrayList<Vec3i> doors, Vec3i shield, Vec3i spawnPosition, City city) {
        this.costs = costs;
        this.id = id;
        this.currentOwner_UUID = currentOwner_UUID;
        this.name = name;
        this.blocksInside = blocksInside;
        this.doors = doors;
        this.shield = new ISign(shield);
        this.spawnPosition = spawnPosition;
        this.city = city;

        this.detail_gui = new Dev_house_detail(this);

    }

    public void fillInside(Material material){
        WorldTools.fillBlocks(this.blocksInside,material);
    }

    public void save(boolean rl){
        String b_inside = "";

        for(Vec3i vec : this.blocksInside){
            b_inside += vec.x + ">>" + vec.y + ">>" + vec.z + ">>";
        }

        String s_doors = "";
        for(Vec3i vec : this.doors){
            s_doors += vec.x + ">>" + vec.y + ">>" + vec.z + ">>";
        }

        mysql.query("REPLACE INTO `eps_regions`.`houses` (`ID`, `NAME`, `COSTS`, `OWNER_UUID`, `BLOCKS_INSIDE`, `DOORS`, `SPAWN_POS`, `SHIELD_POS`, `CITY_ID`) " +
                "VALUES ("+this.id+"," +
                "'" + this.name +"'" +
                ", '"+this.costs.amount+"'," +
                " '"+this.currentOwner_UUID+"'," +
                " '"+b_inside+"'," +
                " '"+s_doors+"'," +
                " '"+this.spawnPosition.x + ">>" + this.spawnPosition.y + ">>" + this.spawnPosition.z+"'," +
                " '"+this.shield.pos.x + ">>" + this.shield.pos.y + ">>" + this.shield.pos.z+"'," +
                " '"+this.city.id+"');");
        if(rl){
            DataManager.reloadRegions();
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
        shield.lines[0] = city.name + " // ID: " + this.id;
        shield.lines[1] = name;

        if(currentOwner_UUID.equals("0")){
            shield.lines[2] = ChatColor.GREEN + "For rent";
        }

        shield.lines[3] = costs.formatString();

        shield.run();



    }

    public void delete(){
            mysql.query("DELETE FROM `eps_regions`.`houses` WHERE `ID` = "+ this.id + ";");
            DataManager.reloadRegions();
        DataManager.reloadRegions();
    }


}
