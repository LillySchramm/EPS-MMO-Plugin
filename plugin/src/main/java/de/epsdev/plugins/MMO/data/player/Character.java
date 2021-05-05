package de.epsdev.plugins.MMO.data.player;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.combat.Attack;
import de.epsdev.plugins.MMO.combat.basetypes.attacks.Test_Attack;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.npc.NPC_Manager;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import de.epsdev.plugins.MMO.particles.Animation;
import de.epsdev.plugins.MMO.schedulers.Delta_Scheduler;
import de.epsdev.plugins.MMO.schedulers.Static_Effect_Scheduler;
import de.epsdev.plugins.MMO.tools.Colors;
import de.epsdev.plugins.MMO.tools.Vec3f;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Character {

    public int exp;
    public int level;
    public int id;

    public String name;
    public String OwnerUUID;

    private Animation animation;
    private List<Attack> attacks;

    public Vec3f pos;

    public Character(String name, int exp, int level, int id, Vec3f pos, String OwnerUUID){
        this.name = name;
        this.exp = exp;
        this.level = level;
        this.id = id;
        this.OwnerUUID = OwnerUUID;
        this.pos = pos;

        this.attacks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            this.attacks.add(new Test_Attack());
        }
    }

    public void save() {
        if(!this.name.equals("")){

            Player player = Bukkit.getPlayer(UUID.fromString(this.OwnerUUID));
            Location location = player.getLocation();

            mysql.query("REPLACE INTO `eps_users`.`characters` (`ID`, `UUID`, `STATS`, `LAST_POS`, `NAME`, `EXP`, `LEVEL`) VALUES " +
                    "(" + this.id + "," +
                    " '" + this.OwnerUUID +"'," +
                    " '', " +
                    "'" + location.getX() + ">>" + location.getY() + ">>" + location.getZ() +"'," +
                    " '" + this.name + "'," +
                    " '"+ this.exp +"', " +
                    "'"+ this.level +"'); ");

            if(this.id == 0){
                ResultSet rs = mysql.query("SELECT * FROM `eps_users`.`characters` WHERE NAME = '" + this.name + "';");
                try {
                    rs.next();
                    this.id = rs.getInt("ID");
                    Out.printToConsole(this.id);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
            ArrayList<Character> characters = new ArrayList<>();

            for (Character character : user.characters){
                if(character.name.equals(this.name)){
                    characters.add(this);
                    continue;
                }

                characters.add(character);
            }

            user.characters = characters;
        }
    }

    public void setPlayerListName(Player player, User user){
        player.setPlayerListName("[" +user.rank.prefix + ChatColor.RESET + "] " + user.currentCharacter.name);
    }

    public void delete(){
        mysql.query("DELETE FROM `eps_users`.`characters` WHERE ID=" + this.id + ";");

        Player player = Bukkit.getPlayer(UUID.fromString(this.OwnerUUID));

        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        ArrayList<Character> characters = new ArrayList<>();

        for (Character character : user.characters){
            if(character.name.equals(this.name)){
                continue;
            }

            characters.add(character);
        }

        user.characters = characters;

        Out.printToPlayer(player, ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "'" + this.name + "' deleted!");

    }

    public void updateHotbar(Player player){
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        for (int i = 0; i < 9; i++){
            ItemStack stack = null;

            if(i < 4){
                stack = this.attacks.get(i).getItem();
            }else if(i > 4){
                stack = this.attacks.get(i).getItem();
            }

            if (this.attacks.get(i) == null) stack = new ItemStack(Material.BARRIER);

            player.getInventory().setItem(i, stack);
        }

        user.playerInventory = player.getInventory().getStorageContents();

    }

    public void load(Player player){
        BukkitScheduler scheduler = main.plugin.getServer().getScheduler();
        player.getInventory().clear();

        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        scheduler.scheduleSyncDelayedTask(main.plugin, () -> {
            player.setDisplayName(this.name);
            player.setFlying(false);

            updateHotbar(player);

            setPlayerListName(player, user);
            player.setLevel(this.level);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.teleport(new Location(player.getWorld(), this.pos.x, this.pos.y, this.pos.z));
            user.refreshScoreboard();

            user.loadedNPC = new ArrayList<>();
            NPC_Manager.loadAllNPC(player);

        }, 1L);
    }

    public void handleSkill(Player player, int slot){
        if(slot <= 4){

            setAnimation(Static_Effect_Scheduler.a);
            User u = DataManager.onlineUsers.get(this.OwnerUUID);
            this.attacks.get(slot).executeAttack(u);
            Out.printToPlayer(player, ChatColor.RED + "Offensive Skill NR." + slot + " activated." );

        }else {
            Out.printToPlayer(player, ChatColor.BLUE + "Support Skill NR." + (slot - 4) + " activated." );
        }
    }

    public void setAnimation(Animation animation){
        if(this.animation == null) this.animation = animation.clone();
    }

    public void playAnimationFrame(){
        Player player = DataManager.onlineUsers.get(this.OwnerUUID).getPlayer();
        if(this.animation != null) this.animation.playAt(player.getLocation());
    }

    public void updateAnimation(){
        if(this.animation != null){
            this.animation.update(DataManager.delta.d);
            if(!this.animation.isAlive()) this.animation = null;
        }
    }
}
