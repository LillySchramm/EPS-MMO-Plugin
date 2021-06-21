package de.epsdev.plugins.MMO.commands;

import de.epsdev.plugins.MMO.GUI.dev.Regions_GUI;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.npc.mobs.Mob_Types;
import de.epsdev.plugins.MMO.npc.mobs.subtypes.DisplayMob;
import de.epsdev.plugins.MMO.npc.mobs.subtypes.DummyMob;
import de.epsdev.plugins.MMO.npc.mobs.subtypes.TestMob;
import de.epsdev.plugins.MMO.schedulers.Delta_Scheduler;
import de.epsdev.plugins.MMO.tools.Vec2f;
import de.epsdev.plugins.MMO.tools.Vec2i;
import de.epsdev.plugins.MMO.tools.Vec3f;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_testmob implements CommandExecutor {

    // TODO: REMOVE ONCE TESTING IS OVER
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){

            Player player = (Player) sender;
            if(args.length == 0){

                Vec3f playerPosition = new Vec3f(player.getLocation());
                DummyMob t = new DummyMob(playerPosition, Mob_Types.VILLAGER);

            }else {
                //Displays Every Mob Type
                Vec2i cursor = new Vec2i();
                Vec3f playerPosition = new Vec3f(player.getLocation());
                for(Mob_Types mob_type : Mob_Types.values()){
                    DisplayMob t = new DisplayMob(new Vec3f(
                            playerPosition.x + 7 * cursor.x,
                            playerPosition.y,
                            playerPosition.z + 7 * cursor.y
                    ), mob_type);

                    cursor.increase2D(7);
                }
            }

        }

        return true;
    }
}
