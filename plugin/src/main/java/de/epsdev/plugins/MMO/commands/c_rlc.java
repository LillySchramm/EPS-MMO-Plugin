package de.epsdev.plugins.MMO.commands;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_rlc implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        User user = null;

        if (sender instanceof Player){
            player = (Player) sender;
            user = DataManager.onlineUsers.get(player.getUniqueId().toString());
            if(!user.rank.canReloadServer){
                Err.rankError(player);
                return true;
            }
        }

        Bukkit.reload();

        return true;
    }
}
