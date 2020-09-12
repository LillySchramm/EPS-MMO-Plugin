package de.epsdev.plugins.MMO.GUI;

import de.epsdev.plugins.MMO.data.regions.cites.houses.House;

public class Dev_house_detail {
    public Base_Gui gui;
    public House house;


    public Dev_house_detail(House house){
        this.house = house;
        this.gui = new Base_Gui(house.name);
    }
}
