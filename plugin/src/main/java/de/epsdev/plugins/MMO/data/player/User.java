package de.epsdev.plugins.MMO.data.player;

import de.epsdev.plugins.MMO.GUI.player.PlayerCharacterSelectionGUI;
import de.epsdev.plugins.MMO.GUI.player.PlayerHouses_GUI;
import de.epsdev.plugins.MMO.GUI.player.PlayerInGameGUI;
import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.commands.Next;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.data.sessions.Session;
import de.epsdev.plugins.MMO.events.OnBreak;
import de.epsdev.plugins.MMO.events.OnChat;
import de.epsdev.plugins.MMO.data.money.Money;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.events.OnPlace;
import de.epsdev.plugins.MMO.events.OnRightObj;
import de.epsdev.plugins.MMO.particles.EF_Single_Particle;
import de.epsdev.plugins.MMO.particles.ParticleConfig;
import de.epsdev.plugins.MMO.ranks.Rank;
import de.epsdev.plugins.MMO.ranks.Ranks;
import de.epsdev.plugins.MMO.scoreboards.DefaultScroreboard;
import de.epsdev.plugins.MMO.scoreboards.Healthbar;
import de.epsdev.plugins.MMO.tools.D_RGB;
import de.epsdev.plugins.MMO.tools.Vec2i;
import de.epsdev.plugins.MMO.tools.Vec3f;
import de.epsdev.plugins.MMO.tools.Vec3i;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

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
import java.util.UUID;

public class User {
    public String displayName;
    public String UUID;
    public Rank rank;
    public Money money;

    public float max_health = 100.0f;
    public float cur_health = 70.0f;

    public float max_mana = 100.0f;
    public float cur_mana = 10.0f;

    public List<Character> characters = new ArrayList<>();
    public Character currentCharacter = null;

    public List<String> temp_strings = new ArrayList<>();

    public OnChat onChat = null;
    public OnBreak onBreak = null;
    public OnPlace onPlace = null;
    public Next next = null;
    public OnRightObj onRightObj = null;
    public House temp_house = null;

    public ItemStack[] playerInventory;

    public Session session;

    public Chunk lastChunk = null;

    public ArrayList<Integer> loadedNPC = new ArrayList<>();

    private int status_bar_scheduler = 0;

    public User(String uuid) throws SQLException {
        ResultSet rs = mysql.query("SELECT * FROM `eps_users`.`players` WHERE UUID = '" + uuid + "'");

        this.UUID = uuid;

        if(rs.next()){
            rs.absolute(1);
            this.money = new Money(rs.getInt("MONEY"));
            this.rank = Ranks.getRank(rs.getString("RANK"));
        }

        this.characters = DataManager.getPlayerCharacters(UUID);

        this.session = new Session(UUID);

    }

    public User(Player player, boolean online) throws SQLException {
        displayName = player.getDisplayName();
        UUID = player.getUniqueId().toString();

        this.session = new Session(UUID);

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

        this.characters = DataManager.getPlayerCharacters(UUID);
    }

    public static void saveUser(User user){
        Bukkit.getScheduler().cancelTask(user.status_bar_scheduler);

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

    public void giveMana(float amount){
        this.cur_mana = this.cur_mana + amount < this.max_mana ? this.cur_mana + amount : this.max_mana;
        this.cur_mana = this.cur_mana >= 0 ? this.cur_mana : 0.0f;
    }

    public void giveHealth(float amount){
        this.cur_health = this.cur_health + amount < this.max_health ? this.cur_health + amount : this.max_health;
        this.cur_health = this.cur_health >= 0 ? this.cur_health : 0.0f;
    }

    public void refreshScoreboard(){
        DefaultScroreboard.refresh(this);

        if(this.status_bar_scheduler == 0){
            this.status_bar_scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, () -> {
                this.giveMana(2.0f);
                Healthbar.refresh(this);
            }, 0L, 8L);
        }

        Healthbar.refresh(this);
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

    public void showCharacterSelectionMenu(Player player){
        try {
            Bukkit.getScheduler().scheduleSyncDelayedTask(main.plugin, () -> {
                this.currentCharacter = null;

                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 200, true));
                player.getInventory().setHeldItemSlot(4);

                new PlayerCharacterSelectionGUI(this).show(player);

                player.setAllowFlight(true);
                player.setFlying(true);
                player.setGameMode(GameMode.SURVIVAL);
                Location location = new Location(player.getWorld(), 1000.0f,200.0f,1000.0f);
                player.teleport(location);
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void showInGameMenu(Player player){
        PlayerInGameGUI ig_gui = new PlayerInGameGUI(this);
        ig_gui.show(player);
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(java.util.UUID.fromString(this.UUID));
    }

    public Location getLOS_Block(){
        Player player = getPlayer();

        Location loc = player.getEyeLocation();
        Location last = null;
        Vector dir = player.getEyeLocation().getDirection().toBlockVector();
        for (double i = 0.1; i < DataManager.MAX_LOS_DISTANCE; i += 0.5) {
            dir.multiply(i);
            loc.add(dir);

            if(loc.getBlock().getType().isSolid()){
                break;
            }

            last = loc.clone();
            loc.subtract(dir);
            dir.normalize();
        }

        return last;
    }
}
