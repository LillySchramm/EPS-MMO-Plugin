package de.epsdev.plugins.MMO.combat;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import de.epsdev.plugins.MMO.tools.Colors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class Attack {
    public final String name;
    public final long reloadTime;
    public final float baseDmg;
    public final float baseHeal;
    public final float ManaCastCost;
    public final float LiveCastCost;
    public final String description;

    public boolean onCooldown= false;
    public long currentCooldown = 0L;

    private int cooldownScheduler = 0;

    /**
     * @param reloadTime In ticks. (1sec == 20 ticks)
     */
    public Attack(String name, long reloadTime, float baseDmg, float baseHeal, float manaCastCost, float liveCastCost, String description){
        this.name = name;
        this.reloadTime = reloadTime;
        this.baseDmg = baseDmg;
        this.ManaCastCost = manaCastCost;
        this.LiveCastCost = liveCastCost;
        this.description = description;
        this.baseHeal = baseHeal;
    }

    public void executeAttack(User user){
        if(!onCooldown){
            if(user.cur_mana >= ManaCastCost){
                if(user.cur_health >= LiveCastCost){
                    List<Base_Mob> targets = getTargets();

                    if(targets == null) return;

                    for (Base_Mob base_mob : targets) {
                        float damage = calculateDamage(user,base_mob);
                        base_mob.doDamage(damage);
                    }

                    cooldown();
                }else {
                    Out.printToPlayer(user.getPlayer(), ChatColor.RED + "Your liveforce is insufficient.");
                }
            }else {
                Out.printToPlayer(user.getPlayer(), ChatColor.RED + "Your mana is insufficient.");
            }
        }else {
            float cooldown = (float) (reloadTime - currentCooldown) / 20;
            Out.printToPlayer(user.getPlayer(), ChatColor.RED + "This attack is on cooldown. Remaining: " + cooldown + "s");
        }

    }

    public void executeHeal(User user){
        List<User> targets = getHealTargets();

        if(targets == null) return;
    }

    public void cooldown(){
        this.onCooldown = true;

        this.cooldownScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.plugin, () -> {
            this.currentCooldown += 1L;
            if(this.currentCooldown >= this.reloadTime){
                this.onCooldown = false;
                this.currentCooldown = 0L;

                cancelCooldownScheduler();
            }

        }, 0L, 1L);
    }

    public void cancelCooldownScheduler(){
        Bukkit.getScheduler().cancelTask(this.cooldownScheduler);
    }

    public ItemStack getItem(){
        ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, Colors.GREEN);

        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(this.name);
        List<String> lore = new ArrayList<>();
        lore.add(description);
        meta.setLore(lore);

        stack.setItemMeta(meta);
        return stack;
    }

    public abstract List<Base_Mob> getTargets();
    public abstract List<User> getHealTargets();
    public abstract void playAnimation();
    public abstract float calculateDamage(User user, Base_Mob mob);
}
