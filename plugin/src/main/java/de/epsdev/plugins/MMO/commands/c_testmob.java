package de.epsdev.plugins.MMO.commands;

import de.epsdev.plugins.MMO.GUI.dev.Regions_GUI;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.npc.mobs.subtypes.TestMob;
import de.epsdev.plugins.MMO.schedulers.Delta_Scheduler;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_testmob implements CommandExecutor {

    // TODO: REMOVE ONCE TESTING IS OVER
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            TestMob t = new TestMob(new Vec3f(player.getLocation()));
            //t.setTargetPos(new Vec3f(t.getPos().x + (float) Math.randomDoubleBetween(-15,15),t.getPos().y,t.getPos().z + (float) Math.randomDoubleBetween(-15,15)));
            t.display();
            //t.playHit();

            // TODO: This needs to be in the base_mob constructor. Not doing it now for testing reasons.
            Delta_Scheduler.mobs.add(t);
        }

        return true;
    }
}
