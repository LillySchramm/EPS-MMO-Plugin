package de.epsdev.plugins.MMO.GUI.player;

import de.epsdev.plugins.MMO.GUI.base.Base_Gui;
import de.epsdev.plugins.MMO.GUI.dev.OnClick;
import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.Character;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.events.OnChat;
import de.epsdev.plugins.MMO.tools.*;
import de.epsdev.plugins.MMO.tools.Math;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;

import static de.epsdev.plugins.MMO.tools.TooltipLore.*;

public class PlayerCharacterSelectionGUI {
    public Base_Gui gui;
    public HashMap<String, Character> characters = new HashMap<>();

    private final OnClick selectCharacter = (player, item, inventory) -> {
        Character c = characters.get(item.getItemMeta().getDisplayName());
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.currentCharacter = c;
        c.load(player);
    };

    private final OnChat onChat_character_name = e -> {
        Player player = e.getPlayer();
        String massage = e.getMessage();

        if(!Math.isAlphabetical(massage)){
            Err.justAlphabeticalCharactersError(player);
        }else if(DataManager.doesCharWithNameExist(massage)){
            Err.characterExistsError(player);
        }else if(massage.length() > 16){
            Err.characterNameLengthError(player);
        }

        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.currentCharacter.name = massage;
        user.currentCharacter.save();

        user.onChat = null;

        user.currentCharacter.load(player);

    };

    private final OnClick click_create_character = (player, item, inventory) ->  {
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.currentCharacter = new Character("",0,1,0, new Vec3f(DataManager.spawnLocation), user.UUID);
        player.closeInventory();

        Out.printToPlayer(player, ChatColor.DARK_GREEN + "Please type the name of your new character.");

        user.onChat = onChat_character_name;

    };

    private final OnClick click_disconnect = (player, item, inventory) -> {
        player.kickPlayer("Disconnect");
    };

    public PlayerCharacterSelectionGUI(User user){
        this.gui = new Base_Gui("Characters");
        this.gui.rows = 3;

        Vec2i cursor = new Vec2i(0,0);

        for (Character c : user.characters) {
            gui.addItem(PlayerHeads.getHeadByName(user.displayName),
                    1,
                    c.name,
                    tt_clickToSelect(),
                    selectCharacter,
                    cursor.x,
                    cursor.y
            );

            characters.put(c.name, c);
            cursor.increase2D(8);
        }

        while (!cursor.equals(new Vec2i(8,2))){
            int num = cursor.x + cursor.y * 8;

            if(user.rank.maxCharacters >= num){
                gui.addItem(Material.ARMOR_STAND,
                        1,
                        ChatColor.DARK_GREEN + "Empty Slot",
                        tt_clickToCreate(),
                        click_create_character,
                        cursor.x,
                        cursor.y
                );
            }else {
                gui.addItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, Colors.RED),
                        1,
                        ChatColor.RED + "Blocked Slot",
                        tt_buyCharSpace(),
                        null,
                        cursor.x,
                        cursor.y
                );
            }

            cursor.increase2D(8);

        }

        gui.addItem(Material.BARRIER,
                1,
                ChatColor.DARK_RED + "" + ChatColor.BOLD + "Disconnect",
                tt_clickToDisconnect(),
                click_disconnect,
                8,
                2
        );
    }

    public void show(Player player){
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncDelayedTask(main.plugin, () -> {
            gui.show(player);
        }, 2L);

    }
}
