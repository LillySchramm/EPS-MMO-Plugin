package de.epsdev.plugins.MMO.data.regions.cites.houses;

import de.epsdev.plugins.MMO.GUI.Base_Gui;
import de.epsdev.plugins.MMO.GUI.dev.Dev_house_detail;
import de.epsdev.plugins.MMO.GUI.dev.OnClick;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.money.Money;
import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.cites.City;
import de.epsdev.plugins.MMO.ranks.Rank;
import de.epsdev.plugins.MMO.tools.*;
import de.epsdev.plugins.MMO.tools.signs.ISign;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


import java.util.ArrayList;
import java.util.List;

public class House {
    public Money costs;
    public int id;
    public int renttime;
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
                 ArrayList<Vec3i> doors, Vec3i shield, Vec3i spawnPosition, City city, int renttime) {
        this.costs = costs;
        this.id = id;
        this.currentOwner_UUID = currentOwner_UUID;
        this.name = name;
        this.blocksInside = blocksInside;
        this.doors = doors;
        this.shield = new ISign(shield);
        this.spawnPosition = spawnPosition;
        this.city = city;
        this.renttime = renttime;

        this.detail_gui = new Dev_house_detail(this);

    }

    public void fillInside(Material material){
        WorldTools.fillBlocks(this.blocksInside,material);
    }

    public void save(boolean rl, boolean reloadSign){
        String b_inside = "";

        for(Vec3i vec : this.blocksInside){
            b_inside += vec.x + ">>" + vec.y + ">>" + vec.z + ">>";
        }

        String s_doors = "";
        for(Vec3i vec : this.doors){
            s_doors += vec.x + ">>" + vec.y + ">>" + vec.z + ">>";
        }

        mysql.query("REPLACE INTO `eps_regions`.`houses` (`ID`, `NAME`, `COSTS`, `OWNER_UUID`, `BLOCKS_INSIDE`, `DOORS`, `SPAWN_POS`, `SHIELD_POS`, `CITY_ID`, `RENTTIME`) " +
                "VALUES ("+this.id+"," +
                "'" + this.name +"'" +
                ", '"+this.costs.amount+"'," +
                " '"+this.currentOwner_UUID+"'," +
                " '"+b_inside+"'," +
                " '"+s_doors+"'," +
                " '"+this.spawnPosition.x + ">>" + this.spawnPosition.y + ">>" + this.spawnPosition.z+"'," +
                " '"+this.shield.pos.x + ">>" + this.shield.pos.y + ">>" + this.shield.pos.z+"'," +
                " '"+this.city.id+"'," +
                "  "+ this.renttime +");");
        if(rl){
            DataManager.reloadRegions();
        }

        if(reloadSign) this.updateSign();
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
        }else{
            shield.lines[2] = ChatColor.ITALIC + "" +ChatColor.RED + "SOLD";
        }

        shield.lines[3] = costs.formatString();

        shield.run();



    }

    public void delete(){
            mysql.query("DELETE FROM `eps_regions`.`houses` WHERE `ID` = "+ this.id + ";");
            DataManager.reloadRegions();
        DataManager.reloadRegions();
    }

    public Vec3i getDoorByPosition(Vec3i pos){
        for(Vec3i vec3i : this.doors){
            if(vec3i.equals(pos) || pos.equals(new Vec3i(vec3i.x,vec3i.y + 1, vec3i.z))){
                return vec3i;
            }
        }
        return null;
    }

    public OnClick rentHouse = (player, item, inventory) -> {
        if (this.currentOwner_UUID.equals("0")){
            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
            Rank user_rank = user.rank;

            ArrayList<House> housesOwnedByPlayer = DataManager.getHousesOwnedByPlayer(player);

            if(!(housesOwnedByPlayer.size() >= user_rank.maxHousesForRent)){
                if(user.decreaseMoney(this.costs.amount, true)){
                    player.closeInventory();
                    this.currentOwner_UUID = player.getUniqueId().toString();
                    this.save(false, true);
                    this.updateSign();
                }else{
                    Out.printToPlayer(player,ChatColor.RED + "" + ChatColor.BOLD + "Sorry but you cant afford this house.");
                }
            }else{
                Out.printToPlayer(player,ChatColor.RED + "" + ChatColor.BOLD + "Sorry but a player with the '" + user_rank.name +
                        "' rank can only rent " + user_rank.maxHousesForRent + " houses at the same time.");
            }
        }else if(player.getUniqueId().toString().equalsIgnoreCase(this.currentOwner_UUID)){
            player.closeInventory();
            this.currentOwner_UUID = "0";
            this.save(false,true);
            Out.printToPlayer(player, ChatColor.RED + "" + ChatColor.BOLD + "Revoked your ownership");

        }
    };

    public void increaseRenttime(){
        this.renttime++;

        if(this.renttime >= DataManager.SECS_PER_RENT){
            User user = DataManager.onlineUsers.get(this.currentOwner_UUID);

            if(user != null){
                if(user.decreaseMoney(this.costs.amount, true)){
                    Out.printToConsole("Player was able to pay.");
                }else{
                    this.currentOwner_UUID = "0";
                    this.save(false, true);
                    Out.printToConsole("Player wasnt able to pay");
                }
            }else{
                if(User.decreaseMoneyOfOfflinePlayer(this.currentOwner_UUID, this.costs.amount)){
                    Out.printToConsole("Offline-Player was able to pay.");
                }else{
                    this.currentOwner_UUID = "0";
                    this.save(false, true);

                    Out.printToConsole("Offline-Player wasnt able to pay");
                }
            }

            this.renttime = 0;
        }

    }

    public void showRentMenu(Player player){
        Base_Gui rGui = new Base_Gui(this.name);
        short color;
        ArrayList<String> lore = new ArrayList<>();
        String middle_name = " ";
        ItemStack middleItem = new ItemStack(Material.BIRCH_DOOR_ITEM, 1);

        if(this.currentOwner_UUID.equals("0")){
            color = Colors.GREEN;
            lore.add(ChatColor.GREEN + "Click to rent for:");
            lore.add(this.costs.formatString());
        }else {
            color = Colors.RED;

            if(player.getUniqueId().toString().equalsIgnoreCase(this.currentOwner_UUID)){
                lore.add(ChatColor.RED + "" + ChatColor.BOLD +"Click to revoke ownership");
            }

            String ownerName = PlayerNames.playerNameByUUID(this.currentOwner_UUID);
            middleItem = PlayerHeads.getHead(this.currentOwner_UUID);
            middle_name = ownerName;
        }

        for(int i = 0; i < 9; i++){
            if(i != 4){
                rGui.addItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, color),
                        1,
                        this.name,
                        lore,
                        rentHouse,
                        i,0);
            }else{
                rGui.addItem(middleItem, 1, middle_name, new ArrayList<>(), null, i,0);
            }
        }

        rGui.show(player);
    }

}
