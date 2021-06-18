package de.epsdev.plugins.MMO.scoreboards;

import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.tools.Math;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Set;

public class Healthbar {

    public static final String FILLER_SYMBOL = "#";

    public static final ChatColor COLOR_MANA_FULL = ChatColor.DARK_AQUA;
    public static final ChatColor COLOR_MANA_EMPTY = ChatColor.WHITE;

    public static final ChatColor COLOR_HEALTH_FULL = ChatColor.DARK_RED;
    public static final ChatColor COLOR_HEALTH_EMPTY = ChatColor.WHITE;

    public static final int BAR_SIZE = 14;

    public static void refresh(User user){
        Player player = user.getPlayer();

        if(user.currentCharacter != null && player.getGameMode() == GameMode.SURVIVAL) {
            String msg = ChatColor.DARK_RED + "❤ " + (int) user.getCur_health() + "/" + (int) user.max_health + "  " + ChatColor.RESET;
            msg += genBar(user.max_health, user.getCur_health(), COLOR_HEALTH_EMPTY, COLOR_HEALTH_FULL) + ChatColor.RESET;

            msg += "    " + ChatColor.DARK_AQUA + "⁂" + (int) user.getCur_mana() + "/" + (int) user.max_mana + "  " + ChatColor.RESET;
            msg += genBar(user.max_mana, user.getCur_mana(), COLOR_MANA_EMPTY, COLOR_MANA_FULL) + ChatColor.RESET;

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));

            //Set Heart bar

            int amount_hearts = (int) (20 * (user.getCur_health() / user.max_health));
            amount_hearts = Math.min(1, amount_hearts);

            int amount_bubbles = (int) (20 * (user.getCur_mana() / user.max_mana));
            amount_bubbles = Math.min(0, amount_bubbles);

            player.setFoodLevel(amount_bubbles);
            player.setHealth(amount_hearts);
        }
    }

    private static String genBar(float max, float cur, ChatColor empty, ChatColor full){
        int amount = Math.min(1, (int) ((cur/max) * BAR_SIZE));

        String ret = "";

        for (int i=0;i<BAR_SIZE; i++){
            ret +=(i >= amount ? empty : full) + FILLER_SYMBOL;
        }

        return ret;
    }

}
