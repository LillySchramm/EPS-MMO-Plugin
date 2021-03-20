package de.epsdev.plugins.MMO.commands;

import de.epsdev.plugins.MMO.GUI.dev.Item_Overview_Collection_GUI;
import de.epsdev.plugins.MMO.GUI.player.PlayerHouses_GUI;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_items implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

            if(user.rank.canManageItems){
                DataManager.item_overview_gui.showPage(player, 0, 0);
            }else {
                Err.rankError(player);
            }
        }

        return true;
    }
}
