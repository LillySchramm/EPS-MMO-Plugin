package de.epsdev.plugins.MMO.data.player;

import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Character {

    public int exp;
    public int level;
    public int id;

    public String name;

    public Vec3f pos;

    public Character(String name, int exp, int level, int id, Vec3f pos){
        this.name = name;
        this.exp = exp;
        this.level = level;
        this.id = id;

        this.pos = pos;
    }

    public void save(){

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
