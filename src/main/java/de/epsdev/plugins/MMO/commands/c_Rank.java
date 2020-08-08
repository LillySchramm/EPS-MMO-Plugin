package de.epsdev.plugins.MMO.commands;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.ranks.Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class c_Rank implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0 && sender instanceof Player){
            Player player = (Player) sender;
            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

            Rank rank = user.rank;

            Out.printToPlayer(player, ChatColor.DARK_GREEN + "Your rank is: " + rank.prefix);
        }else if (args.length >= 2){
            switch (args[0]){
                default:
                    if (sender instanceof Player){
                        Player player = (Player) sender;
                        Out.printToPlayer(player, "Available subcomands: set remove");
                    }else{
                        Out.printToConsole("Available subcomands: set remove");
                    }
                    break;
                case "set":
                    if(sender instanceof Player){
                        Player player = (Player) sender;
                        User user = DataManager.getUser(player);
                        if(user.rank.canChangeRanks){

                        }else {
                            Err.rankError(player);
                        }
                    }else {
                        if(args.length == 3){

                        }else{
                            Out.printToConsole("No rank defined.");
                        }
                    }
                    break;
            }
        }
        return true;
    }
}
