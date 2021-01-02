package de.epsdev.plugins.MMO.data.player;

import de.epsdev.plugins.MMO.GUI.player.PlayerHouses_GUI;
import de.epsdev.plugins.MMO.commands.Next;
import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.events.OnBreak;
import de.epsdev.plugins.MMO.events.OnChat;
import de.epsdev.plugins.MMO.data.money.Money;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.events.OnPlace;
import de.epsdev.plugins.MMO.events.OnRightObj;
import de.epsdev.plugins.MMO.ranks.Rank;
import de.epsdev.plugins.MMO.ranks.Ranks;
import de.epsdev.plugins.MMO.scoreboards.DefaultScroreboard;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import javax.sql.RowSet;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    public String displayName;
    public String UUID;
    public Rank rank;
    public Money money;

    public List<String> temp_strings = new ArrayList<>();

    public OnChat onChat = null;
    public OnBreak onBreak = null;
    public OnPlace onPlace = null;
    public Next next = null;
    public OnRightObj onRightObj = null;
    public House temp_house = null;

    public Chunk lastChunk = null;

    public ArrayList<Integer> loadedNPC = new ArrayList<>();

    public User(String uuid) throws SQLException {
        ResultSet rs = mysql.query("SELECT * FROM `eps_users`.`players` WHERE UUID = '" + uuid + "'");

        this.UUID = uuid;

        if(rs.next()){
            rs.absolute(1);
            this.money = new Money(rs.getInt("MONEY"));
            this.rank = Ranks.getRank(rs.getString("RANK"));

        }
    }

    public User(Player player, boolean online) throws SQLException {
        displayName = player.getDisplayName();
        UUID = player.getUniqueId().toString();

        ResultSet rs = mysql.query("SELECT * FROM `eps_users`.`players` WHERE UUID = '" + UUID + "'");

        if(!rs.next()){
            money = new Money(0);
            this.rank = Ranks.Player;

            mysql.query("INSERT INTO `eps_users`.`players` (`ID`, `UUID`, `RANK`, `MONEY`) VALUES (NULL, '" + UUID + "', 'player', '0') ");

        }else {
            rs.absolute(1);
            money = new Money(rs.getInt("MONEY"));
            this.rank = Ranks.getRank(rs.getString("RANK"));
        }

        if(online) DefaultScroreboard.refresh(this);
    }

    public static void saveUser(User user){
        user.save();
    }

    public static boolean decreaseMoneyOfOfflinePlayer(String uuid, int amount) {
        try{
            User user = new User(uuid);

            boolean ret = user.decreaseMoney(amount, false);

            User.saveUser(user);

            return ret;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean decreaseMoney(int a, boolean online) {
        boolean ret = this.money.decreaseMoney(a);
        if(ret) {
            if(online){
                this.refreshScoreboard();
                this.save();
            }
        }


        return ret;
    }

    public void refreshScoreboard(){
        DefaultScroreboard.refresh(this);
    }

    public void save() {
        mysql.query("UPDATE `eps_users`.`players` SET `RANK` = '" + this.rank.name + "', `MONEY` = '"+ this.money.amount +"' WHERE `players`.`UUID` = '" + this.UUID + "' ");
    }
    public void print(){
        Out.printToConsole("Display Name: " + displayName);
        Out.printToConsole("UUID: " + UUID);
        Out.printToConsole("Money: " + money.amount);
        Out.printToConsole("Rank: " + rank.name);
    }


}
