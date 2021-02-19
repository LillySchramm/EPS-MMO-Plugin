package de.epsdev.plugins.MMO.commands;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.npc.eNpc.eNpc;
import de.epsdev.plugins.MMO.schedulers.Static_Effect_Scheduler;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_staticeffect implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            User user = DataManager.onlineUsers.get(player.getUniqueId().toString());;
            if(user.rank.canManageStaticEffects){
                new eNpc(0, DataManager.DEFAULT_EFFECT, new Vec3f(player.getLocation())).save();
            }else {
                Err.rankError(player);
            }
        }
        return true;
    }
}
