package de.epsdev.plugins.MMO.GUI.base;

import de.epsdev.plugins.MMO.items.Base_Item;

import java.util.List;

public class Base_Collection_Category {
    public List<Base_Item> items;
    public String categoryName;
    public Base_Item collection_sym;

    public Base_Collection_Category(String categoryName, Base_Item collection_sym,List<Base_Item> items){
        this.items = items;
        this.collection_sym = collection_sym;
        this.categoryName = categoryName;
    }
}
