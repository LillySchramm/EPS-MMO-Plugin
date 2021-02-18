package de.epsdev.plugins.MMO.npc.eNpc;

import de.epsdev.plugins.MMO.GUI.dev.DEV_eNpc_GUI;
import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.particles.Particle_Effect;
import de.epsdev.plugins.MMO.schedulers.Static_Effect_Scheduler;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class eNpc {
    public int eNpc_id;
    public Particle_Effect particle_effect;
    public Vec3f pos;

    public DEV_eNpc_GUI gui;

    private String data;

    public ArmorStand armorStand;
    public boolean deleted = false;

    public eNpc(int id, Particle_Effect particle_effect, Vec3f position, String data){
        this.eNpc_id = id;
        this.particle_effect = particle_effect;
        this.pos = position;
        this.data = data;

        this.gui = new DEV_eNpc_GUI(this);

        Static_Effect_Scheduler.register(this);
    }

    public void spawnArmorStand(){
        if(this.armorStand == null){
            armorStand = (ArmorStand) this.pos.toLocation().getWorld().spawnEntity(this.pos.toLocation(), EntityType.ARMOR_STAND);
            armorStand.setCustomName("ST_" + this.eNpc_id);
            armorStand.setCustomNameVisible(true);
            armorStand.setGravity(false);
            armorStand.setInvulnerable(true);
        }
    }

    public void removeArmorStand(){
        if(this.armorStand != null){
            this.armorStand.remove();
            this.armorStand = null;
        }
    }

    public void display(){
        if(this.deleted) return;
        particle_effect.display(pos.toLocation());
    }

    public void showManageGUI(Player player){
        gui.show(player);
    }

    public void save(){
        removeArmorStand();

        mysql.query("REPLACE INTO `eps_regions`.`static_effects` (`ID`, `DATA`, `POS`) VALUES (" + this.eNpc_id + "," +
                " '" + this.data + "'," +
                " '" + pos.x + ">> " + pos.y + " >> " + pos.z + "'); ");

        this.gui = new DEV_eNpc_GUI(this);

        spawnArmorStand();
    }

    public void delete(){
        mysql.query("DELETE FROM `eps_regions`.`static_effects` WHERE ID = " + this.eNpc_id + ";");
        this.deleted = true;
        Static_Effect_Scheduler.unload(this);
    }

    public void fullReload(){
    }

    public int getArmorStandID(){
        if (armorStand == null) return -1;
        return armorStand.getEntityId();
    }
}
