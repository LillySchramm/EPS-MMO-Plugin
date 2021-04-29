package de.epsdev.plugins.MMO.schedulers;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.npc.eNpc.eNpc;
import de.epsdev.plugins.MMO.particles.*;
import de.epsdev.plugins.MMO.particles.animations.ANIM_TestRotImg;
import de.epsdev.plugins.MMO.tools.D_RGB;
import de.epsdev.plugins.MMO.tools.Vec3f;
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

    private static EF_Image i = new EF_Image("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", 0.2f);
    public static ANIM_TestRotImg a = new ANIM_TestRotImg(180, i);

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
