package de.epsdev.plugins.MMO.data.player;

import de.epsdev.plugins.MMO.GUI.player.PlayerCharacterSelectionGUI;
import de.epsdev.plugins.MMO.GUI.player.PlayerHouses_GUI;
import de.epsdev.plugins.MMO.GUI.player.PlayerInGameGUI;
import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.combat.Attack;
import de.epsdev.plugins.MMO.combat.AttackCollection;
import de.epsdev.plugins.MMO.combat.Attackable;
import de.epsdev.plugins.MMO.combat.basetypes.attacks.Test_Melee_Attack;
import de.epsdev.plugins.MMO.combat.basetypes.attacks.Test_Self_Attack;
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
import de.epsdev.plugins.MMO.npc.mobs.Mob_Manager;
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
import net.minecraft.server.v1_12_R1.Entity;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
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

public class User extends Attackable {
    public String displayName;
    public String UUID;
    public Rank rank;
    public Money money;

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
    public ArrayList<Integer> loadedMOB = new ArrayList<>();

    private int status_bar_scheduler = 0;

    public User(String uuid) throws SQLException {

        super(100.0f,200.0f, 2f, 10f,new AttackCollection(new Attack[]{new Test_Melee_Attack(),new Test_Melee_Attack()}
                ,new Attack[]{new Test_Self_Attack()}),SIDE.PLAYER);

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

    public void disableSchedulers(){
        Bukkit.getScheduler().cancelTask(status_bar_scheduler);
    }

    public User(Player player, boolean online) throws SQLException {
        super(100.0f,200.0f, 2f, 10f,new AttackCollection(new Attack[]{new Test_Melee_Attack()}
                ,new Attack[]{new Test_Self_Attack()}),SIDE.PLAYER);

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

    @Override
    public Vec3f getPosition() {
        return new Vec3f(this.getPlayer().getLocation());
    }

    @Override
    public float calculateDamage(float org_damage) {
        return org_damage;
    }

    @Override
    public float calculateHeal(float org_heal) {
        return org_heal;
    }

    @Override
    public float calculateManaLoss(float org_loss) {
        return org_loss;
    }

    @Override
    public float calculateManaGain(float org_gain) {
        return org_gain;
    }

    @Override
    public void onChange() {

    }

    @Override
    public Entity getEntity() {
        return  ((CraftPlayer) this.getPlayer()).getHandle();
    }

    @Override
    public void kill(){
        Player player = getPlayer();
        player.setHealth(0);
    }


    public void refreshScoreboard(){
        DefaultScroreboard.refresh(this);

        if(this.status_bar_scheduler == 0){
            this.status_bar_scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, () -> Healthbar.refresh(this), 0L, 4L);
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

    public void setSlot(ItemStack item, int slot){
        this.playerInventory[slot] = item;
        this.getPlayer().getInventory().setItem(slot, item);
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
