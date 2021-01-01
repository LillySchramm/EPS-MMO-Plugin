package de.epsdev.plugins.MMO.npc;

import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.tools.Vec3i;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NPC {

    public int npc_id;
    public String name;
    public EntityPlayer entityPlayer;
    public String script = "";
    public Skin skin = null;

    public NPC(int id, String name, EntityPlayer entityPlayer, Skin skin){
        this.npc_id = id;
        this.entityPlayer = entityPlayer;
        this.name = name;
        this.skin = skin;
    }

    public void display(Player player){
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
                this.entityPlayer));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this.entityPlayer));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(this.entityPlayer, (byte) (this.entityPlayer.yaw * 256 / 360)));
    }

    public void save(boolean n){
        Location loc = entityPlayer.getBukkitEntity().getLocation();
        Vec3i pos = new Vec3i(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        Skin skin = this.skin;

        if(skin == null){
            skin = new Skin(new String[]{}, "0");
        }

        if(n){
            mysql.query("REPLACE INTO `eps_regions`.`npc` (`ID`, `NAME`, `SCRIPT`, `POS`, `SKIN`) VALUES (" + this.npc_id + "," +
                    " '" + this.name + "'," +
                    " '" + this.script + "'," +
                    " '" + pos.x + ">> " + pos.y + " >> " + pos.z + "', " +
                    " '" + this.skin.Owner + "') ");
            return;
        }

        mysql.query("REPLACE INTO `eps_regions`.`npc` (`ID`, `NAME`, `SCRIPT`, `POS`, `SKIN`) VALUES (" + this.npc_id + "," +
                " '" + this.name + "'," +
                " '" + this.script + "'," +
                " '" + pos.x + ">> " + pos.y + " >> " + pos.z + "', " +
                " '" + this.skin.Owner + "') ");

    }
}