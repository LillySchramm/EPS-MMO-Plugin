package de.epsdev.plugins.MMO.data.player;

import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

    public void save(){
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
    }

    public void setPlayerListName(Player player, User user){
        player.setPlayerListName("[" +user.rank.prefix + ChatColor.RESET + "] " + user.currentCharacter.name);
    }

    public void load(Player player, User user){
        player.setDisplayName(this.name);
        player.setFlying(false);
        setPlayerListName(player, user);
        player.setLevel(this.level);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.teleport(new Location(player.getWorld(), this.pos.x, this.pos.y, this.pos.z));
        user.refreshScoreboard();
    }
}
