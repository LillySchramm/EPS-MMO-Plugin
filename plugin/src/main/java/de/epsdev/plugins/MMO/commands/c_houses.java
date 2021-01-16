package de.epsdev.plugins.MMO.commands;

import de.epsdev.plugins.MMO.GUI.player.PlayerHouses_GUI;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_houses implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
            PlayerHouses_GUI.genHouseOverviewMenu(user).show(player);

        }

        return true;
    }
}
