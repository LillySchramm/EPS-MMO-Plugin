package de.epsdev.plugins.MMO.npc;

import de.epsdev.plugins.MMO.GUI.Base_Gui;
import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.tools.PlayerHeads;
import de.epsdev.plugins.MMO.tools.Vec2f;
import de.epsdev.plugins.MMO.tools.Vec3f;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;

public class NPC {

    public int npc_id;
    public String name;
    public EntityPlayer entityPlayer;
    public String script = "";
    public Skin skin = null;

    public NPC(int id, String name, EntityPlayer entityPlayer, Skin skin, String script){
        this.npc_id = id;
        this.entityPlayer = entityPlayer;
        this.name = name;
        this.skin = skin;
        this.script = script;
    }

    public void display(Player player){
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        entityPlayer.getBukkitEntity().setPlayerListName("");

        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
                this.entityPlayer));

        Out.printToConsole(this.entityPlayer.yaw * 256 / 360);

        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this.entityPlayer));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(this.entityPlayer, (byte) (this.entityPlayer.yaw * 256 / 360)));
        BukkitScheduler scheduler = main.plugin.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(main.plugin, () -> {
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
                    this.entityPlayer));
        }, 20L);

    }

    public void showManageGUI(Player player){
        Base_Gui gui = new Base_Gui(this.name);

        gui.addItem(PlayerHeads.getHeadByName(this.skin.Owner),
                1,
                this.name,
                new ArrayList<>(),
                null,
                0,
                0
                );

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
    }

    public void unload(Player player) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityDestroy(new int[]{this.entityPlayer.getId()}));
    }
}