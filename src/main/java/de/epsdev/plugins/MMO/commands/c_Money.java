package de.epsdev.plugins.MMO.commands;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class c_Money implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

            if(args.length == 0){
                Out.printToPlayer(player, user.money.formatString());
            }else {
                if(user.rank.canHandleMoney){
                    switch (args[0]){
                        default:
                            Out.printToPlayer(player, "Possible subcommands: removeAll give");
                            break;

                        case "give":
                            if(args.length == 3){

                                User u = DataManager.getUserByName(args[1]);
                                if (u != null){
                                    u.money.amount += Integer.parseInt(args[2]);
                                    u.save();
                                }else {
                                    Out.printToPlayer(player, "Player not found.");
                                }
                            }else {
                                Out.printToPlayer(player, "Correct usage: /money give <player> <amount>");
                            }
                            break;

                        case "removeAll":
                            break;
                    }
                }else {
                    Err.rankError(player);
                }
            }




        }

        return true;
    }


}
