package de.epsdev.plugins.MMO.commands;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.npc.NPC;
import de.epsdev.plugins.MMO.npc.NPC_Manager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_npc implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            NPC npc = NPC_Manager.createNPC(player, player.getName());
            npc.save(true);
            DataManager.loadAllNPC();
        }
        return true;
    }
}
