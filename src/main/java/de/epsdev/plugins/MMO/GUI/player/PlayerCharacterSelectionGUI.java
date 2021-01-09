package de.epsdev.plugins.MMO.GUI.player;

import de.epsdev.plugins.MMO.GUI.Base_Gui;
import de.epsdev.plugins.MMO.GUI.dev.OnClick;
import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.player.Character;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.tools.PlayerHeads;
import de.epsdev.plugins.MMO.tools.Vec2i;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;

import static de.epsdev.plugins.MMO.tools.TooltipLore.*;

public class PlayerCharacterSelectionGUI {
    public Base_Gui gui;
    public HashMap<String, Character> characters = new HashMap<>();

    private final OnClick selectCharacter = (player, item, inventory) -> {
        Character c = characters.get(item.getItemMeta().getDisplayName());
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.currentCharacter = c;
        c.load(player, user);
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

        while (!cursor.equals(new Vec2i(8,3))){
            int num = cursor.x + cursor.y * 8;

            if(user.rank.maxCharacters >= num){
                gui.addItem(Material.ARMOR_STAND,
                        1,
                        ChatColor.DARK_GREEN + "Empty Slot",
                        tt_clickToCreate(),
                        null,
                        cursor.x,
                        cursor.y
                );
            }else {
                gui.addItem(Material.BARRIER,
                        1,
                        ChatColor.DARK_GREEN + "Blocked Slot",
                        tt_buyCharSpace(),
                        null,
                        cursor.x,
                        cursor.y
                );
            }

            cursor.increase2D(8);
        }
    }

    public void show(Player player){
        BukkitScheduler scheduler = Bukkit.getScheduler();


        scheduler.scheduleSyncDelayedTask(main.plugin, () -> {
            gui.show(player);
        }, 2L);

    }
}
