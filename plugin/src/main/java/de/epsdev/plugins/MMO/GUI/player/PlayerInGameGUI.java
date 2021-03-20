package de.epsdev.plugins.MMO.GUI.player;

import de.epsdev.plugins.MMO.GUI.base.Base_Gui;
import de.epsdev.plugins.MMO.GUI.dev.OnClick;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.events.OnChat;
import de.epsdev.plugins.MMO.tools.Colors;
import de.epsdev.plugins.MMO.tools.PlayerHeads;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static de.epsdev.plugins.MMO.tools.TooltipLore.*;

public class PlayerInGameGUI {

    private Base_Gui gui;

    private final OnClick onClick_change_characters = (player, item, inventory) -> {
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.save();
        user.currentCharacter.save();

        player.closeInventory();

        user.showCharacterSelectionMenu(player);
    };

    private final OnClick click_disconnect = (player, item, inventory) -> {
        player.kickPlayer("Disconnect");
    };

    private final OnChat delete_on_chat = e -> {
        Player player = e.getPlayer();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        String message = e.getMessage();

        if(message.equals(user.currentCharacter.name)){
            user.currentCharacter.delete();
            user.showCharacterSelectionMenu(player);
        }else {
            Out.printToPlayer(player, ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Character deletion cancelled");
        }

        user.onChat = null;
    };

    private final OnClick click_delete_character = (player, item, inventory) -> {
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        player.closeInventory();

        Out.printToPlayer(player , ChatColor.DARK_GREEN + "-----------------------" );
        Out.printToPlayer(player , ChatColor.DARK_GREEN + "Please type the name of your current character to confirm that you want to delete it." );
        Out.printToPlayer(player , ChatColor.BOLD + "" + ChatColor.RED + "WARNING: ONCE YOUR CHARACTER IS DELETED ITS GONE FOREVER" );
        Out.printToPlayer(player , ChatColor.DARK_GREEN + "-----------------------" );

        user.onChat = delete_on_chat;

    };

    public PlayerInGameGUI(User user){
        this.gui = new Base_Gui("Menu");

        gui.addItem(PlayerHeads.getHeadByName(user.displayName),
                1,
                "Change character",
                tt_changeChar(),
                onClick_change_characters,
                0,
                0
        );

        gui.addItem(new ItemStack(Material.WOOL, 1, Colors.RED),
                1,
                "Delete character",
                tt_deleteChar(),
                click_delete_character,
                7,
                0
        );

        gui.addItem(Material.BARRIER,
                1,
                "Disconnect",
                tt_clickToDisconnect(),
                click_disconnect,
                8,
                0
        );

    }

    public void show(Player player){
        this.gui.show(player);
    }

}
