package de.epsdev.plugins.MMO.GUI.base;

import de.epsdev.plugins.MMO.GUI.dev.OnClick;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.items.Base_Item;
import de.epsdev.plugins.MMO.tools.Vec2i;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Base_Collection_GUI{
    public List<Base_Collection_Category> collections = new ArrayList<>();
    public List<List<Base_Gui>> pages;

    public String name;

    public Base_Collection_GUI(String name) {
        this.name = name;
    }

    public void addCollection(Base_Collection_Category c){
        this.collections.add(c);
    }

    private final OnClick switch_site = (player, item, inventory) -> {
        DataManager.item_overview_gui.showPage(player, item.getAmount() - 1, 0);
    };

    private Vec2i getTitleMeta(Inventory inventory){
        String title = inventory.getTitle();
        String[] _title = title.split(":");

        String spn = _title[1].replace("]", "");
        _title = _title[0].split("\\[");
        String cn = _title[_title.length - 1];

        int category_num = 0;

        for (Base_Collection_Category collection_category : collections){
            if(collection_category.categoryName.equalsIgnoreCase(cn)) break;
            category_num++;
        }

        int sub_page_num = Integer.parseInt(spn);

        return new Vec2i(category_num, sub_page_num);
    }

    private final OnClick increase_subsite = (player, item, inventory) -> {
        Vec2i meta = getTitleMeta(inventory);
        showPage(player, meta.x, meta.y + 1);
    };

    private final OnClick decrease_subsite = (player, item, inventory) -> {
        Vec2i meta = getTitleMeta(inventory);
        showPage(player, meta.x, meta.y - 1);
    };

    public void create(){
        this.pages = new ArrayList<>();

        for (Base_Collection_Category c : collections){

            //Create subpages
            boolean finished = false;
            List<Base_Gui> subsites = new ArrayList<>();
            List<Base_Item> _items = new ArrayList<>(c.items);
            int site_num = 0;
            while (!finished){
                Base_Gui page = new Base_Gui( this.name + "[" +  c.categoryName + ":" + site_num + "]");
                page.rows = 6;

                //Adding Base Elements
                int i = 0;
                for (Base_Collection_Category cc: collections){
                    page.addItem(cc.collection_sym.mat, i+1, cc.categoryName, new ArrayList<>(), switch_site, i+1,5);
                    i++;
                }

                if(site_num == 0){
                    page.addItem(DataManager.GUI_FILLER.getType(), 1, "No Previous Site", new ArrayList<>(), null, 0, 5);
                }else {
                    page.addItem(DataManager.GUI_FILLER.getType(), 1, "Previous Site", new ArrayList<>(), decrease_subsite, 0, 5);
                }

                page.addItem(DataManager.GUI_FILLER.getType(), 1, "Next Site", new ArrayList<>(), increase_subsite, 8, 5);

                //Adding the items

                Vec2i pos = new Vec2i();

                i = 0;
                List<Base_Item> toRemove = new ArrayList<>();
                for(Base_Item item : _items){
                    toRemove.add(item);

                    page.addItem(item.mat, 1, item.name, new ArrayList<>(), null, pos.x, pos.y);
                    pos.increase2D(8);

                    if(i == 44) break;
                    i++;
                }

                for(Base_Item item : toRemove){
                    _items.remove(item);
                }

                finished = i != 44;

                if(finished) page.addItem(DataManager.GUI_FILLER.getType(), 1, "No Next Site", new ArrayList<>(), null, 8, 5);

                site_num++;
                subsites.add(page);
            }

            pages.add(subsites);

        }
    }

    public void showPage(Player player, int page, int subite){
        player.closeInventory();
        this.pages.get(page).get(subite).show(player);
    }
}
