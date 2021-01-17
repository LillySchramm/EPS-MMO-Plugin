package de.epsdev.plugins.MMO.data.player;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.tools.Colors;
import de.epsdev.plugins.MMO.tools.Vec3f;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;


public class Character {

    public int exp;
    public int level;
    public int id;

    public String name;
    public String OwnerUUID;

    public Vec3f pos;

    public Character(String name, int exp, int level, int id, Vec3f pos, String OwnerUUID){
        this.name = name;
        this.exp = exp;
        this.level = level;
        this.id = id;
        this.OwnerUUID = OwnerUUID;
        this.pos = pos;
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
        for (int i = 0; i < 9; i++){
            ItemStack stack = null;

            if(i < 4){
                stack = new ItemStack(Material.STAINED_GLASS_PANE,1 , Colors.RED);
            }else if(i > 4){
                stack = new ItemStack(Material.STAINED_GLASS_PANE,1 , Colors.BLUE);
            }

            player.getInventory().setItem(i, stack);
        }
    }

    public void load(Player player, User user){

        BukkitScheduler scheduler = main.plugin.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(main.plugin, () -> {
            player.setDisplayName(this.name);
            player.setFlying(false);

            updateHotbar(player);

            setPlayerListName(player, user);
            player.setLevel(this.level);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.teleport(new Location(player.getWorld(), this.pos.x, this.pos.y, this.pos.z));
            user.refreshScoreboard();
        }, 1L);
    }

    public void handleSkill(Player player, int slot){
        if(slot <= 4){
            Out.printToPlayer(player, ChatColor.RED + "Offensive Skill NR." + slot + " activated." );
        }else {
            Out.printToPlayer(player, ChatColor.BLUE + "Support Skill NR." + (slot - 4) + " activated." );
        }
    }
}
