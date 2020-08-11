package de.epsdev.plugins.MMO.GUI;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.ranks.Rank;
import de.epsdev.plugins.MMO.ranks.Ranks;
import de.epsdev.plugins.MMO.scoreboards.DefaultScroreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CheatMenu_GUI {
    public static Base_Gui main_gui = new Base_Gui("Cheatmenu");
    public static Base_Gui money_gui = new Base_Gui("Money");
    public static Base_Gui rank_gui = new Base_Gui("Ranks");

    public static void init(){
        main_gui.rows = 1;
        main_gui.addItem(Material.EMERALD, 1, ChatColor.DARK_GREEN + "Money", new ArrayList<String>(), main_money_click, 0,0);
        main_gui.addItem(Material.ARMOR_STAND, 1, ChatColor.RED + "Ranks", new ArrayList<String>(),main_rank_click,1,0);

        money_gui.rows = 3;

        money_gui.addItem(Material.SPECTRAL_ARROW, 1, ChatColor.RED  + "Back to mainmenu", new ArrayList<String>(),backToMainMenu, 0,1);

        money_gui.addItem(Material.BARRIER, 1, ChatColor.RED  + "remove all", new ArrayList<String>(),handleMoney, 3,1);

        money_gui.addItem(Material.EMERALD_BLOCK, 1, "+10000", new ArrayList<String>(),handleMoney, 8,0);
        money_gui.addItem(Material.EMERALD_BLOCK, 1, "+1000", new ArrayList<String>(),handleMoney, 7,0);
        money_gui.addItem(Material.EMERALD_BLOCK, 1, "+100", new ArrayList<String>(),handleMoney, 6,0);
        money_gui.addItem(Material.EMERALD_BLOCK, 1, "+10", new ArrayList<String>(),handleMoney, 5,0);
        money_gui.addItem(Material.EMERALD_BLOCK, 1, "+1", new ArrayList<String>(),handleMoney, 4,0);

        money_gui.addItem(Material.REDSTONE_BLOCK, 1, "-10000", new ArrayList<String>(),handleMoney, 8,2);
        money_gui.addItem(Material.REDSTONE_BLOCK, 1, "-1000", new ArrayList<String>(),handleMoney, 7,2);
        money_gui.addItem(Material.REDSTONE_BLOCK, 1, "-100", new ArrayList<String>(),handleMoney, 6,2);
        money_gui.addItem(Material.REDSTONE_BLOCK, 1, "-10", new ArrayList<String>(),handleMoney, 5,2);
        money_gui.addItem(Material.REDSTONE_BLOCK, 1, "-1", new ArrayList<String>(),handleMoney, 4,2);

        rank_gui.rows = 1;

        rank_gui.addItem(Material.SPECTRAL_ARROW, 1, ChatColor.RED  + "Back to mainmenu", new ArrayList<String>(),backToMainMenu, 0,0);
        rank_gui.addItem(Material.WOOL, 1, "Admin", new ArrayList<String>(),handleRank, 8,0);
        rank_gui.addItem(Material.WOOL, 1, "Mod", new ArrayList<String>(),handleRank, 7,0);
        rank_gui.addItem(Material.WOOL, 1, "Builder", new ArrayList<String>(),handleRank, 6,0);
        rank_gui.addItem(Material.WOOL, 1, "VIP", new ArrayList<String>(),handleRank, 5,0);
        rank_gui.addItem(Material.WOOL, 1, "Player", new ArrayList<String>(),handleRank, 4,0);

    }

    public static OnClick handleRank = (player, item, inventory) -> {
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        String itemName = item.getItemMeta().getDisplayName();

        Rank rank = Ranks.getRank(itemName.toLowerCase());

        if(rank != null){
            user.rank = rank;
            user.save();
            DefaultScroreboard.refresh(user);
        }

    };

    public static OnClick handleMoney = (player, item, inventory) -> {
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        String itemname = item.getItemMeta().getDisplayName();

        if(itemname.equalsIgnoreCase( ChatColor.RED +"remove all")){
            user.money.amount -= user.money.amount;
        }else {
            user.money.amount += Integer.parseInt(itemname);
        }
        user.save();
        DefaultScroreboard.refresh(user);

    };

    public static OnClick backToMainMenu = (player, item, inventory) -> main_gui.show(player);

    public static OnClick main_money_click = (player, item, inventory) -> money_gui.show(player);

    public static OnClick main_rank_click = (player, item, inventory) -> rank_gui.show(player);


}
