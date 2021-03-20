package de.epsdev.plugins.MMO.GUI.dev;

import de.epsdev.plugins.MMO.GUI.base.Base_Gui;
import de.epsdev.plugins.MMO.commands.Next;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.events.OnChat;
import de.epsdev.plugins.MMO.npc.NPC;
import de.epsdev.plugins.MMO.npc.NPC_Manager;
import de.epsdev.plugins.MMO.tools.Vec3i;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.UUID;

import static de.epsdev.plugins.MMO.tools.TooltipLore.tt_clickToUpdate;

public class Dev_NPC_GUI {
    public Base_Gui gui;
    public NPC npc;

    private final OnChat onChatRename = (e) ->{
        String massage = e.getMessage();
        Player player = e.getPlayer();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        if(massage.equals("null")){
            Out.printToPlayer(player, ChatColor.DARK_GREEN + "Canceled the rename process.");
            user.onChat = null;
            return;
        }

        if(massage.length() > 16){
            Out.printToPlayer(player, ChatColor.RED + "The name of an NPC must be shorter than 17 characters.");
            return;
        }

        npc.changeName(massage);
        npc.save(false);

        BukkitScheduler scheduler = Bukkit.getScheduler();

        NPC_Manager.reloadNPC(npc);

        user.onChat = null;
        Out.printToPlayer(player, ChatColor.DARK_GREEN + "NPC renamed successfully");
    };

    private final OnClick click_rename = (player, item, inventory) -> {
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.onChat = onChatRename;
        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Please type the new name for the npc.");
        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Type 'null' to not change.");
        player.closeInventory();

    };

    private final OnClick click_npc_delete = (player, item, inventory) -> {
        this.npc.delete();

        Out.printToPlayer(player,ChatColor.RED + "Deleted NPC Nr. "  + this.npc.npc_id);
        player.closeInventory();

        NPC_Manager.unloadNPC(npc);
    };

    private final Next next_npc_pos = user -> {
        Out.printToPlayer(Bukkit.getPlayer(UUID.fromString(user.UUID)), ChatColor.DARK_GREEN + "NPC renamed successfully");
        npc.changePos(((CraftPlayer) Bukkit.getPlayer(UUID.fromString(user.UUID))).getHandle());
        npc.save(false);
        NPC_Manager.reloadNPC(npc);
        user.next = null;
    };


    private final OnClick click_change_pos = (player, item, inventory) -> {
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.next = next_npc_pos;
        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Please stand in the new position.");
        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Confirm with /next");
        player.closeInventory();

    };

    public Dev_NPC_GUI(NPC npc){
        this.npc = npc;

        gui = new Base_Gui(npc.name);

        /*gui.addItem(PlayerHeads.getHeadByName(this.npc.skin.Owner),
                1,
                npc.name + " ID: " + ChatColor.RESET + npc.npc_id,
                new ArrayList<>(),
                null,
                0,
                0
        );*/

        gui.addItem(Material.NAME_TAG,
                1,
                "Name: " + npc.name,
                tt_clickToUpdate(),
                click_rename,
                1,
                0
        );

        gui.addItem(Material.COMPASS,
                1,
                "Position: " + new Vec3i(this.npc.entityPlayer.getBukkitEntity().getLocation()).toString(),
                tt_clickToUpdate(),
                click_change_pos,
                2,
                0
        );

        gui.addItem(Material.BARRIER,
                1,
                ChatColor.RED + "" + ChatColor.BOLD + "Delete NPC",
                new ArrayList<>(),
                click_npc_delete,
                8,
                0
        );
    }



    public void show(Player player){
        gui.show(player);
    }
}
