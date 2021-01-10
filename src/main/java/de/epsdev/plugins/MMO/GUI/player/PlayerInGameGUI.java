package de.epsdev.plugins.MMO.GUI.player;

import de.epsdev.plugins.MMO.GUI.Base_Gui;
import de.epsdev.plugins.MMO.GUI.dev.OnClick;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.tools.PlayerHeads;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static de.epsdev.plugins.MMO.tools.TooltipLore.tt_changeChar;
import static de.epsdev.plugins.MMO.tools.TooltipLore.tt_clickToSelect;

public class PlayerInGameGUI {

    private Base_Gui gui;

    public final OnClick onClick_change_characters = (player, item, inventory) -> {
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.save();
        user.currentCharacter.save();

        player.closeInventory();

        user.showCharacterSelectionMenu(player);
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

    }

    public void show(Player player){
        this.gui.show(player);
    }

}
