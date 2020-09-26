package de.epsdev.plugins.MMO.GUI;

import de.epsdev.plugins.MMO.commands.Next;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.events.OnBreak;
import de.epsdev.plugins.MMO.events.OnChat;
import de.epsdev.plugins.MMO.events.OnPlace;
import de.epsdev.plugins.MMO.tools.Math;
import de.epsdev.plugins.MMO.tools.Vec3i;
import de.epsdev.plugins.MMO.tools.WorldTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

import static de.epsdev.plugins.MMO.tools.TooltipLore.*;

public class Dev_house_detail {
    public Base_Gui gui;
    public House house;


    public Dev_house_detail(House house){
        this.house = house;

        this.gui = new Base_Gui(house.name);



        init();



    }

    private final OnChat changePrice = e -> {
        Player player = e.getPlayer();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        String msg = e.getMessage();

        if(Math.isNumeric(msg)){
            house.costs.amount = Integer.parseInt(msg);
            house.save(true);
        }else {
            if(msg.equalsIgnoreCase("null")){
                Out.printToPlayer(player, ChatColor.GREEN + "Cost revamp process canceled.");
            }else Err.notANumberError(player);
        }

        user.onChat = null;

    };

    private final OnChat changeName = e -> {
        Player player = e.getPlayer();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        String msg = e.getMessage();

        if( house.city.getHouseByName(msg) != null){
            Err.houseAlreadyExistsError(player);
        }else if(msg.equalsIgnoreCase("null")){
            Out.printToPlayer(player, ChatColor.GREEN + "Renaming process canceled.");
        }else {
            house.name = msg;
            house.save(true);
        }

        user.onChat = null;
    };

    private final OnClick changePriceClick = (player, item, inventory) -> {
        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Please type the new price for the house.");
        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Type 'null' to not change.");

        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.onChat = changePrice;

        player.closeInventory();
    };

    private final OnClick changeNameClick = (player, item, inventory) -> {
        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Please type the new name for the house.");
        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Type 'null' to not change.");

        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.onChat = changeName;

        player.closeInventory();
    };

    private final OnClick revokeOwnership = (player, item, inventory) -> {
        Out.printToPlayer(player, "Revoked the ownership!");
        house.currentOwner_UUID = "0";
        house.save(false);
    };

    private final Next setSpNext = user -> {
        Player player = Bukkit.getPlayer(user.displayName);

        house.spawnPosition.x = player.getLocation().getBlockX();
        house.spawnPosition.y = player.getLocation().getBlockY();
        house.spawnPosition.z = player.getLocation().getBlockZ();

        house.save(true);

        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Set the spawn.");

        user.next = null;

    };



    private final OnClick setSpawnpoint = (player, item, inventory) -> {
        player.closeInventory();

        Location location = player.getLocation();

        location.setX(house.spawnPosition.x);
        location.setY(house.spawnPosition.y);
        location.setZ(house.spawnPosition.z);

        player.teleport(location);

        Out.printToPlayer(player, ChatColor.DARK_GREEN + "This is the current Spawn.");
        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Type /next to set the spawnpoint to your current position.");

        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        user.next = setSpNext;

    };

    private final OnBreak interiorBreak = e -> {
        Location location = e.getBlock().getLocation();

        house.processBlock(new Vec3i(location.getBlockX(),location.getBlockY(),location.getBlockZ()), true);
    };

    private final OnPlace interiorPlace = e -> {
        Location location = e.getBlock().getLocation();

        house.processBlock(new Vec3i(location.getBlockX(),location.getBlockY(),location.getBlockZ()), false);
    };

    private final Next interior_next = user -> {
      user.onPlace = null;
      user.onBreak = null;
      user.next = null;

      house.save(true);

      Out.printToPlayer(Bukkit.getPlayer(user.displayName), ChatColor.DARK_GREEN + "Saved the blocks");

    };

    private OnClick updateInterior = new OnClick() {
        @Override
        public void click(Player player, ItemStack item, Inventory inventory) {
            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

            user.onPlace = interiorPlace;
            user.onBreak = interiorBreak;
            user.next = interior_next;

            Out.printToPlayer(player , ChatColor.DARK_GREEN + "Please redefine the space.");
            Out.printToPlayer(player ,ChatColor.DARK_GREEN + "Commit with /next");

            house.fillInside(Material.EMERALD_BLOCK);

            Inventory inv = player.getInventory();
            inv.clear();
            ItemStack greenConcrete = new ItemStack(Material.CONCRETE, 1, (byte) 5);
            ItemStack[] items = new ItemStack[]{greenConcrete};
            inv.addItem(items);
            player.getInventory().setContents(items);

        }
    };

    public void init(){
        gui.addItem(Material.NAME_TAG, 1, house.name, tt_clickToUpdate(), changeNameClick, 0,0);
        gui.addItem(Material.EMERALD, 1, house.costs.formatString(), tt_clickToUpdate(), changePriceClick, 1,0);

        ArrayList<String> lore = new ArrayList<>();

        if(house.currentOwner_UUID.equalsIgnoreCase("0")){
            lore.add(ChatColor.GREEN + "Current owner: NO NONE");
        }else {
            UUID uuid = UUID.fromString(house.currentOwner_UUID);
            lore.add(ChatColor.GREEN + "Current owner: " + Bukkit.getOfflinePlayer(uuid).getName());
        }

        gui.addItem(Material.BED, 1, ChatColor.RED + "Revoke ownership", lore, revokeOwnership, 2,0);
        gui.addItem(Material.COMPASS, 1, "Spawnpossition: " + house.spawnPosition.toString(), tt_clickToUpdate(), setSpawnpoint, 3,0);
        gui.addItem(Material.CONCRETE_POWDER, 1, "Inner Blocks", tt_clickToUpdate(), updateInterior, 4,0);
        gui.addItem(Material.BARRIER, 1, "Delete House", tt_clickToDelete(), null, 8,0);



    }

    private void show(Player player){
        gui.show(player);
    }
}
