package de.epsdev.plugins.MMO.commands;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.cites.City;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.events.OnBreak;
import de.epsdev.plugins.MMO.events.OnPlace;
import de.epsdev.plugins.MMO.tools.Vec3i;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

public class c_createhouse implements CommandExecutor {

    private OnPlace deff_blocks = e -> {
        Block block = e.getBlock();
        Player player = e.getPlayer();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.temp_house.processBlock(new Vec3i(block.getX(),block.getY(),block.getZ()), false);
    };

    private OnBreak dest_blocks = e -> {
        Block block = e.getBlock();
        Player player = e.getPlayer();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
        user.temp_house.processBlock(new Vec3i(block.getX(),block.getY(),block.getZ()), true);
    };

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

            if(user.rank.canManageHouses){
                if(args.length >= 1){
                    City city = DataManager.getCityByName(args[0]);
                    if(city != null){
                        DataManager.onlineUsers.get(player.getUniqueId().toString()).temp_house = new House();
                        DataManager.onlineUsers.get(player.getUniqueId().toString()).onPlace = deff_blocks;
                        DataManager.onlineUsers.get(player.getUniqueId().toString()).onBreak = dest_blocks;
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
