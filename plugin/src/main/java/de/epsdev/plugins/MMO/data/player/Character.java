package de.epsdev.plugins.MMO.data.player;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.combat.Attack;
import de.epsdev.plugins.MMO.combat.AttackCollection;
import de.epsdev.plugins.MMO.combat.basetypes.attacks.Test_Melee_Attack;
import de.epsdev.plugins.MMO.combat.basetypes.attacks.Test_Self_Attack;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.npc.NPC_Manager;
import de.epsdev.plugins.MMO.particles.Animation;
import de.epsdev.plugins.MMO.schedulers.Static_Effect_Scheduler;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.ResultSet;
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
    private List<Integer> a_slotsOnCooldown = new ArrayList<>();
    private List<Integer> s_slotsOnCooldown = new ArrayList<>();

    private AttackCollection attackCollection;

    public Vec3f pos;

    public Character(String name, int exp, int level, int id, Vec3f pos, String OwnerUUID){
        this.name = name;
        this.exp = exp;
        this.level = level;
        this.id = id;
        this.OwnerUUID = OwnerUUID;
        this.pos = pos;

        this.attackCollection = new AttackCollection(new Attack[]{new Test_Melee_Attack(),new Test_Self_Attack(),new Test_Melee_Attack(),new Test_Self_Attack()}
                ,new Attack[]{new Test_Self_Attack(), new Test_Melee_Attack()});
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

            if(i < 4 && this.attackCollection.attacks.length >= i+1){
                stack = this.attackCollection.attacks[i].getItem();
            }else if(i > 4 && this.attackCollection.support.length >= i-4){
                stack = this.attackCollection.support[i-5].getItem();
            }else if(i != 4){
                stack = new ItemStack(Material.BARRIER);
            }
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
        User u = DataManager.onlineUsers.get(this.OwnerUUID);

        if(slot <= 4){

            if(slot - 1 >= this.attackCollection.attacks.length) return;

            this.attackCollection.attacks[slot - 1].executeAttack(u);
            u.setSlot(this.attackCollection.attacks[slot - 1].genCooldownItem(), slot - 1);
            a_slotsOnCooldown.add(slot - 1);

        }else {
            slot -= 4;

            if(slot - 1 >= this.attackCollection.support.length) return;

            this.attackCollection.support[slot - 1].executeAttack(u);
            u.setSlot(this.attackCollection.support[slot - 1].genCooldownItem(), slot + 4);
            s_slotsOnCooldown.add(slot - 1);

        }
    }

    public void updateCooldown(){
        User u = DataManager.onlineUsers.get(this.OwnerUUID);
        List<Integer> toBeRemoved = new ArrayList<>();
        for (int i : this.a_slotsOnCooldown){
            if(this.attackCollection.attacks[i].onCooldown){
                u.setSlot(this.attackCollection.attacks[i].genCooldownItem(), i);
            }else {
                u.setSlot(this.attackCollection.attacks[i].getItem(), i);
                toBeRemoved.add(i);
            }
        }

        for(Object o : toBeRemoved){
            this.a_slotsOnCooldown.remove(o);
        }

        toBeRemoved = new ArrayList<>();

        for (int i : this.s_slotsOnCooldown){
            if(this.attackCollection.support[i].onCooldown){
                u.setSlot(this.attackCollection.support[i].genCooldownItem(), i + 5);
            }else {
                u.setSlot(this.attackCollection.support[i].getItem(), i + 5);
                toBeRemoved.add(i);
            }
        }

        for(Object o : toBeRemoved){
            this.s_slotsOnCooldown.remove(o);
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
