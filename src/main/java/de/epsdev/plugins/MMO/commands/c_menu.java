package de.epsdev.plugins.MMO.commands;


import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.mysql.DatabaseManager;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_menu implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player){
            Player player = (((Player) sender).getPlayer());
            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

            user.showInGameMenu(player);
        }

        return true;
    }
}
