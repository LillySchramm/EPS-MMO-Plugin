package de.epsdev.plugins.MMO.commands;

import de.epsdev.plugins.MMO.GUI.CheatMenu_GUI;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_cheatmenu implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());;
            if(user.rank.canUseCheatMenu || player.isOp()){
                CheatMenu_GUI.main_gui.show(player);
            }else {
                Err.rankError(player);
            }
        }
        return true;
    }
}
