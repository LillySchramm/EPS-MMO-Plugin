package de.epsdev.plugins.MMO.commands;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.Region;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_createcity implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        if(user.rank.canManageRegions) {
            if (args.length >= 2) {
                if (DataManager.isRegionAlreadyExisting(args[0])) {
                    Region region = DataManager.getRegionByName(args[0]);
                    if (!DataManager.isCityExisting(args[1])) {
                        assert region != null;
                        DataManager.createCity(args[1], region);
                        Out.printToPlayer(player, ChatColor.GREEN + "Created the city '" + args[1] + "' in '" + region.name +"'");
                    } else {
                        Err.cityAlreadyExistsError(player);
                    }
                } else {
                    Err.regionNotFoundError(player);
                }
            } else {
                Err.notEnoughArgumentsError(player);
                Err.correctUsage(player, new String[]{"region", "cityname"});
            }
        }else {
            Err.rankError(player);
        }
        return true;
    }
}
