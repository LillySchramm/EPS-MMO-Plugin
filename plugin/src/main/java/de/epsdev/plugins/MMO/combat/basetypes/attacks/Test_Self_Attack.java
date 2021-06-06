package de.epsdev.plugins.MMO.combat.basetypes.attacks;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.combat.basetypes.Base_Self_Attack;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import de.epsdev.plugins.MMO.particles.EF_Particle_Pillar;
import de.epsdev.plugins.MMO.particles.ParticleConfig;
import de.epsdev.plugins.MMO.tools.D_RGB;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Test_Self_Attack extends Base_Self_Attack {
    private int anim_scheduler;

    public Test_Self_Attack() {
        super(ChatColor.DARK_GREEN + "Test_Self_Attack",
                50L,
                1,
                1,
                30,
                10,
                "An extremely powerful attack. Just as good as lorem ipsum");
    }

    // TODO: I need to create a better way to do stuff like this
    private void cancelPAnimation(){
        Bukkit.getScheduler().cancelTask(anim_scheduler);
    }

    @Override
    public void pAnimation(Entity e) {
        AtomicInteger i = new AtomicInteger();
        anim_scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, () -> {
            Entity _e = Bukkit.getEntity(e.getUniqueId());
            EF_Particle_Pillar ef = new EF_Particle_Pillar(new ParticleConfig(Particle.REDSTONE, new D_RGB(50,150,20)),
                    1,
                    4,
                    2,
                    5);

            ef.display(_e.getLocation());
            if(i.getAndIncrement() > 12*5) cancelPAnimation();
        }, 0L, 1L);
    }

    @Override
    public void pHitAnimation(Entity e) {

    }

    @Override
    public List<Base_Mob> getTargets(Player p) {
        return null;
    }
}
