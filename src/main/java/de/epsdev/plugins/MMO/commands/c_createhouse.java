package de.epsdev.plugins.MMO.commands;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.cites.City;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_createhouse implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

            if(user.rank.canManageHouses){
                if(args.length >= 1){
                    City city = DataManager.getCityByName(args[0]);
                    if(city != null){
                        House house = new House();



                    }else {
                        Err.cityNotFoundError(player);
                    }
                }else {
                    Err.notEnoughArgumentsError(player);
                    Err.correctUsage(player, new String[]{"cityname"});
                }
            }else {
                Err.rankError(player);
            }

        }

        return true;
    }
}
