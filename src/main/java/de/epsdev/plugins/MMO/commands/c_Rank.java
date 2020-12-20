package de.epsdev.plugins.MMO.commands;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.ranks.Rank;
import de.epsdev.plugins.MMO.ranks.Ranks;
import de.epsdev.plugins.MMO.scoreboards.DefaultScroreboard;
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
                        Out.printToPlayer(player, "Available subcomands: set ");
                    }else{
                        Out.printToConsole("Available subcomands: set ");
                    }
                    break;
                case "set":
                    if(sender instanceof Player){
                        Player player = (Player) sender;
                        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());;
                        if(user.rank.canChangeRanks || player.isOp()){
                            if(args.length == 3){
                                User u = DataManager.getUserByName(args[1]);
                                if(u != null){
                                    Rank rank = Ranks.getRank(args[2]);
                                    if (rank != null){
                                        u.rank = rank;
                                        u.save();
                                        Out.printToPlayer(player ,"Changed Rank of player " + args[1] + " to " + rank.prefix);
                                        DefaultScroreboard.refresh(u);
                                    }else {
                                        Out.printToPlayer(player,"Rank not found");
                                    }
                                }else {
                                    Out.printToPlayer(player,"Player not online.");
                                }
                            }else{
                                Out.printToPlayer(player,"No rank defined.");
                            }
                        }else {
                            Err.rankError(player);
                        }
                    }else {
                        if(args.length == 3){
                            User user = DataManager.getUserByName(args[1]);
                            if(user != null){
                                Rank rank = Ranks.getRank(args[2]);
                                if (rank != null){
                                    user.rank = rank;
                                    user.save();
                                    Out.printToConsole("Changed Rank of player " + args[1] + " to " + rank.prefix);
                                }else {
                                    Out.printToConsole("Rank not found");
                                }
                            }else {
                                Out.printToConsole("Player not online.");
                            }
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
