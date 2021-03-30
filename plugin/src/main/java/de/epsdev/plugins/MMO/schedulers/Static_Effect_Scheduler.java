package de.epsdev.plugins.MMO.schedulers;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.npc.eNpc.eNpc;
import de.epsdev.plugins.MMO.particles.EF_Particle_Ring;
import de.epsdev.plugins.MMO.particles.EF_Single_Particle;
import de.epsdev.plugins.MMO.particles.ParticleConfig;
import de.epsdev.plugins.MMO.tools.D_RGB;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;

public class Static_Effect_Scheduler {

    public static HashMap<Integer, eNpc> effects = new HashMap<>();
    private static boolean shown = false;

    public static void showArmorStandConfig(Player player, int id){
        for (eNpc e : effects.values()){
            if(e.getArmorStandID() == id){
                User user = DataManager.onlineUsers.get(player.getUniqueId().toString());
                if(user.rank.canManageStaticEffects) e.showManageGUI(player);
            }
        }
    }

    public static void register(eNpc e){
        effects.put(e.eNpc_id, e);
    }

    public static void unload(eNpc e){
        effects.remove(e.eNpc_id);
    }

    public static void run() {
        BukkitScheduler scheduler = main.plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(main.plugin, () -> {
            effects.forEach((integer, eNpc) -> {
                eNpc.display();
            });

        }, 0L, 8L);
    }

    public static void showAllArmorStands(){
        effects.forEach((integer, eNpc) -> {
            eNpc.spawnArmorStand();
        });
    }

    public static void toggleArmorStands(){
        if(!shown) showAllArmorStands();
        else destroyAllArmorStands();

        shown = !shown;
    }

    public static void destroyAllArmorStands(){
        effects.forEach(((integer, eNpc) -> {
            eNpc.removeArmorStand();
        }));
    }

    public static void hardReloadArmorStand(int id){
        effects.get(id).fullReload();
    }

    public static void deleteArmorStand(int id){
        effects.get(id).delete();
    }
}
