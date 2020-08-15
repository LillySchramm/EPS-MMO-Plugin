package de.epsdev.plugins.MMO.commands;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_createregion implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;

        if(sender instanceof Player){
            player = (Player) sender;
            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

            if(!user.rank.canManageRegions){
                Err.rankError(player);
                return true;
            }

        }

        if((args.length >= 1)){
            boolean success = DataManager.createRegion(args[0], player);
            if(player != null && success){
                Out.printToPlayer(player, "Created the region: " + args[0]);
            }else if (success){
                Out.printToConsole("Created the region:" + args[0]);
            }
        }else {
            if(player != null){
                Err.notEnoughArgumentsError(player);
            }

        }

        return true;
    }
}
