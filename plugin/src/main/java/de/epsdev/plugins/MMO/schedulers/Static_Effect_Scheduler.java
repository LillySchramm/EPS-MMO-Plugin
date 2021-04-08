package de.epsdev.plugins.MMO.schedulers;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.npc.eNpc.eNpc;
import de.epsdev.plugins.MMO.particles.EF_Image;
import de.epsdev.plugins.MMO.particles.EF_Particle_Ring;
import de.epsdev.plugins.MMO.particles.EF_Single_Particle;
import de.epsdev.plugins.MMO.particles.ParticleConfig;
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

    private static EF_Image i = new EF_Image("iVBORw0KGgoAAAANSUhEUgAAADMAAAAzCAYAAAA6oTAqAAABhWlDQ1BJQ0MgcHJvZmlsZQAAKJF9kTtIw1AUhv8+pEUqCnYQcchQxcGCqIijVqEIFUKt0KqDyU1f0KQhSXFxFFwLDj4Wqw4uzro6uAqC4APEzc1J0UVKPDcptIjxwOV+/Pf8P/eeC/gbFaaawXFA1SwjnUwI2dyqEHpFEGH0wYdRiZn6nCim4Flf99RHdRfnWd59f1aPkjcZ4BOIZ5luWMQbxNObls55nzjKSpJCfE48ZtAFiR+5Lrv8xrnosJ9nRo1Mep44SiwUO1juYFYyVOIp4piiapTvz7qscN7irFZqrHVP/sJIXltZ5jqtISSxiCWIECCjhjIqsBCnXSPFRJrOEx7+QccvkksmVxmMHAuoQoXk+MH/4PdszcLkhJsUSQBdL7b9MQyEdoFm3ba/j227eQIEnoErre2vNoCZT9LrbS12BPRuAxfXbU3eAy53gIEnXTIkRwrQ8hcKwPsZfVMO6L8FutfcubXOcfoAZGhWqRvg4BAYKVL2use7w51z+7enNb8fGOlyg4r5uqQAAAAGYktHRAD/AP8A/6C9p5MAAAAJcEhZcwAALiMAAC4jAXilP3YAAAAHdElNRQflBAgQIArTHsByAAAAGXRFWHRDb21tZW50AENyZWF0ZWQgd2l0aCBHSU1QV4EOFwAAAeJJREFUaN7tmj1OAzEQhT2r1BQ0EQ1FKpC4ASVNDpFUnIFTcIZU7CFoKLkBUqi2SIO2oeACTmXJGP+MvePxLNqR0iSbXX95M29mvQGttYrF9eNt/ADCuLi5nPT9lWIMe7E/n9/k54eQMjFFpv6CqVjvNnTKuCAliy9dkIm3qxfAHHf3fK/N9VY1f0XsglLxCvAnS7Zag1JKfTy9Q1HNhBZOtWgsSLEB2Ck29oNa7zZVFx8BMdcMwnWpE9ZwnUwQcEFMimXDtAQS3WcKQ/sKH63M6XCM1sTD1776VBBbdLYyp8MROEeZXCBTU/YxRWnG5Wa5Vt1hT2KbAEealfScLveEYz80gQiB2O/Pwc2Up3F6nS6qTMzVGqUabZrZqcYF5Liatl6/PutKL8BdO5i+082pYLZagw3lAmbB+Ga0FrXjQpEo08KmSdNM8gSdhPHZswvUaiIgNwBJqYaCSd0SSFEHrUwMSIo6s+ozC8ycGiiZMjaQhLohT7OW6mTBpBpoa3WylZHcc0jSTIo6RTAYdWZtABKGz2IYieMNqTW3VmcSjDR1/tVsBqk/NWAi9HTafgbKsdleVZmxH1g3DKvAuEbAVT8kMFKa6HJzhlGnxc0bmzIcdVMVhnv3kxQGs/u5GAAyzpFS9m9MYm7JAAAAAElFTkSuQmCC", 0.15f);


    public static void run() {
        BukkitScheduler scheduler = main.plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(main.plugin, () -> {
            effects.forEach((integer, eNpc) -> {
                eNpc.display();
            });

            Bukkit.getOnlinePlayers().forEach(player -> {
                i.setRot_offset((int) player.getLocation().getYaw());
                i.display(player.getLocation());
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
