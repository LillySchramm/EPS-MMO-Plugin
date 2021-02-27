package de.epsdev.plugins.MMO.GUI.dev;

import de.epsdev.plugins.MMO.GUI.Base_Gui;
import de.epsdev.plugins.MMO.commands.Next;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.npc.eNpc.eNpc;
import de.epsdev.plugins.MMO.schedulers.Static_Effect_Scheduler;
import de.epsdev.plugins.MMO.tools.TooltipLore;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class DEV_eNpc_GUI {

    public Base_Gui gui;
    public int id;

    private final Next next_position = user -> {
        Player player = Bukkit.getPlayer(user.displayName);
        Vec3f pos = new Vec3f(player.getLocation());

        Static_Effect_Scheduler.effects.get(id).pos = pos;
        Static_Effect_Scheduler.effects.get(id).save();

        Out.printToPlayer(player, ChatColor.GREEN + "Changed the position of effect " + id);

        user.next = null;
    };

    private final OnClick click_location_change = (player, item, inventory) -> {
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        player.closeInventory();
        Out.printToPlayer(player, ChatColor.GREEN + "Please stand in the new position and confirm with /next");

        user.next = next_position;
    };

    private final OnClick click_delete = (player, item, inventory) -> {
        player.closeInventory();
        Out.printToPlayer(player, ChatColor.RED + "Deleted the effect with ID "  + this.id);
        Static_Effect_Scheduler.deleteArmorStand(this.id);
    };

    public DEV_eNpc_GUI(eNpc enpc){
        this.gui = new Base_Gui("ID: " + enpc.eNpc_id);
        this.id = enpc.eNpc_id;

        gui.addItem(Material.COMPASS, 1, enpc.pos.toString(), TooltipLore.tt_clickToUpdate(), click_location_change, 0,0);
        gui.addItem(Material.BARRIER, 1,   ChatColor.RED + "" + ChatColor.BOLD + "DELETE", TooltipLore.tt_clickToDelete(), click_delete, 8,0);

    }

    public void show(Player player){
        gui.show(player);
    }

}
