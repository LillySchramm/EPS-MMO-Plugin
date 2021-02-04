package de.epsdev.plugins.MMO.npc;

import de.epsdev.plugins.MMO.GUI.Base_Gui;
import de.epsdev.plugins.MMO.GUI.dev.Dev_NPC_GUI;
import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.tools.PlayerHeads;
import de.epsdev.plugins.MMO.tools.Vec2f;
import de.epsdev.plugins.MMO.tools.Vec3f;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NPC {

    public int npc_id;
    public String name;
    public EntityPlayer entityPlayer;
    public String script = "";
    public Skin skin = null;

    public Boolean deleted = false;
    public Dev_NPC_GUI gui;

    public NPC(int id, String name, EntityPlayer entityPlayer, Skin skin, String script){
        this.npc_id = id;
        this.entityPlayer = entityPlayer;
        this.name = name;
        this.skin = skin;
        this.script = script;

        this.gui = new Dev_NPC_GUI(this);
    }

    public void recreateEntity(){
        this.entityPlayer = NPC_Manager.createNPC_ENTITY(name, new Vec3f(this.entityPlayer.getBukkitEntity().getLocation()), new Vec2f(this.entityPlayer.yaw, this.entityPlayer.pitch), this.skin.Owner);
    }

    public void display(Player player){

        if(this.deleted) return;

        BukkitScheduler scheduler = main.plugin.getServer().getScheduler();

        scheduler.scheduleSyncDelayedTask(main.plugin, () -> {

            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
                    this.entityPlayer));

            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this.entityPlayer));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(this.entityPlayer, (byte) (this.entityPlayer.yaw * 256 / 360)));

            scheduler.scheduleSyncDelayedTask(main.plugin, () -> {
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
                        this.entityPlayer));
            }, 20L);

            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
            user.loadedNPC.add(this.npc_id);


        }, 1L);



    }

    public void showManageGUI(Player player){
        gui.show(player);
    }

    public void save(boolean n){
        Location loc = entityPlayer.getBukkitEntity().getLocation();
        Vec3f pos = new Vec3f(loc);
        Vec2f rot = new Vec2f(loc);
        Skin skin = this.skin;

        if(skin == null){
            skin = new Skin(new String[]{}, "0");
        }


        mysql.query("REPLACE INTO `eps_regions`.`npc` (`ID`, `NAME`, `SCRIPT`, `POS`, `ROTATION`, `SKIN`) VALUES (" + this.npc_id + "," +
                " '" + this.name + "'," +
                " '" + this.script + "'," +
                " '" + pos.x + ">> " + pos.y + " >> " + pos.z + "', " +
                " '" + entityPlayer.yaw + ">> " + entityPlayer.pitch + "', " +
                " '" + this.skin.Owner + "') ");

        this.gui = new Dev_NPC_GUI(this);

    }

    public void unload(Player player) {

        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityDestroy(new int[]{this.entityPlayer.getId()}));

        user.loadedNPC.remove((Object) this.npc_id);

    }

    public void changePos(EntityPlayer player){
        NPC_Manager.unloadNPC(this);
        this.entityPlayer = player;
        recreateEntity();
    }

    public void changeName(String newname){
        NPC_Manager.unloadNPC(this);
        this.name = newname;
        recreateEntity();
    }

    public void delete(){
        mysql.query("DELETE FROM `eps_regions`.`npc` WHERE ID = " + this.npc_id + ";");
        this.deleted = true;
        NPC_Manager.unloadNPC(this);
    }

    public void fullReload(){
        NPC_Manager.unloadNPC(this);
        ResultSet rs = mysql.query("SELECT * FROM `eps_regions`.`npc` WHERE ID = " + this.npc_id + ";");

        try {
            rs.next();
            this.name = rs.getString("NAME");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        recreateEntity();

    }
}