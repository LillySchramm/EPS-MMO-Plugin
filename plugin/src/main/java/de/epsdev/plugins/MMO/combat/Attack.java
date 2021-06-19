package de.epsdev.plugins.MMO.combat;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.mysql.DatabaseManager;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import de.epsdev.plugins.MMO.tools.Colors;
import de.epsdev.plugins.MMO.tools.ItemDescription;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
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

    public boolean onCooldown = false;
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

    public void executeAttack(Attackable attackable){
        if(!onCooldown){
            if(attackable.getCur_mana() >= ManaCastCost){
                if(attackable.getCur_health() >= LiveCastCost){
                    List<Attackable> targets = getTargets(attackable);
                    if(targets != null) {
                        for (Attackable _attackable : targets) {
                            if(attackable.side != _attackable.side && _attackable.uuid != attackable.uuid){
                                float damage = calculateDamage(attackable, _attackable);
                                _attackable.dealDamage(damage);
                                playHitAnimation(attackable.getEntity().getBukkitEntity());
                            }
                        }
                    }

                    playAnimation(attackable.getEntity().getBukkitEntity());

                    attackable.dealDamage(this.LiveCastCost);
                    attackable.reduceMana(this.ManaCastCost);

                    cooldown();
                }else {
                    if(attackable instanceof User) Out.printToPlayer(((User) attackable).getPlayer() , ChatColor.RED + "Your liveforce is insufficient.");
                }
            }else {
                if(attackable instanceof User) Out.printToPlayer(((User) attackable).getPlayer(), ChatColor.RED + "Your mana is insufficient.");
            }
        }else {
            float cooldown = (float) (reloadTime - currentCooldown) / 20;
            if(attackable instanceof User) Out.printToPlayer(((User) attackable).getPlayer(), ChatColor.RED + "This attack is on cooldown. Remaining: " + cooldown + "s");
        }

    }

    public void executeHeal(Attackable a){
        List<Attackable> targets = getHealTargets(a);

        if(targets == null) return;
    }

    public ItemStack genCooldownItem(){
        ItemStack itemStack = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = itemStack.getItemMeta();

        float cooldown = (float) (reloadTime - currentCooldown) / 20;
        itemMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + cooldown + "s");
        List<String> lore = new ArrayList<>();
        lore.add("");
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
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
        meta.setDisplayName("[SKILL] "  + this.name);
        List<String> lore = ItemDescription.toItemDescription(description);
        meta.setLore(lore);

        stack.setItemMeta(meta);
        return stack;
    }

    public abstract List<Attackable> getTargets(Attackable a);
    public abstract List<Attackable> getHealTargets(Attackable a);

    /**
     * @apiNote Only for animations related to the user of the attack. Recommended to use an scheduler
     * @see this.playHitAnimation for hit animations
     */
    public abstract void playAnimation(Entity e);

    /**
     * @apiNote Only for animations related to the entity hit by the attack. Recommended to use an scheduler
     */
    public abstract void playHitAnimation(Entity e);

    public abstract float calculateDamage(Attackable user, Attackable target);
}
