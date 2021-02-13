package de.epsdev.plugins.MMO.npc.eNpc;

import de.epsdev.plugins.MMO.GUI.dev.DEV_eNpc_GUI;
import de.epsdev.plugins.MMO.GUI.dev.Dev_NPC_GUI;
import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.npc.NPC_Manager;
import de.epsdev.plugins.MMO.npc.Skin;
import de.epsdev.plugins.MMO.particles.Particle_Effect;
import de.epsdev.plugins.MMO.schedulers.Static_Effect_Scheduler;
import de.epsdev.plugins.MMO.tools.Vec2f;
import de.epsdev.plugins.MMO.tools.Vec3f;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.ResultSet;

public class eNpc {
    public int eNpc_id;
    public Particle_Effect particle_effect;
    public Vec3f pos;

    public DEV_eNpc_GUI gui;

    private String data;

    public boolean deleted = false;

    public eNpc(int id, Particle_Effect particle_effect, Vec3f position, String data){
        this.eNpc_id = id;
        this.particle_effect = particle_effect;
        this.pos = position;

        this.gui = new DEV_eNpc_GUI(this);

        Static_Effect_Scheduler.register(this);
    }

    public void display(){
        if(this.deleted) return;
        particle_effect.display(pos.toLocation());
    }

    public void showManageGUI(Player player){
        gui.show(player);
    }

    public void save(boolean n){
        mysql.query("REPLACE INTO `eps_regions`.`static_effects` (`ID`, `DATA`, `POS`) VALUES (" + this.eNpc_id + "," +
                " '" + this.data + "'," +
                " '" + pos.x + ">> " + pos.y + " >> " + pos.z + "'); ");

        this.gui = new DEV_eNpc_GUI(this);
    }

    public void delete(){
        mysql.query("DELETE FROM `eps_regions`.`static_effects` WHERE ID = " + this.eNpc_id + ";");
        this.deleted = true;
        Static_Effect_Scheduler.unload(this);
    }

    public void fullReload(){
    }
}
