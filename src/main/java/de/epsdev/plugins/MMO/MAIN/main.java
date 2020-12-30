package de.epsdev.plugins.MMO.MAIN;

import de.epsdev.plugins.MMO.GUI.dev.CheatMenu_GUI;
import de.epsdev.plugins.MMO.GUI.dev.Regions_GUI;
import de.epsdev.plugins.MMO.commands.*;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.mysql.DatabaseManager;
import de.epsdev.plugins.MMO.events.*;

import de.epsdev.plugins.MMO.schedulers.MAIN_UPDATE_SCHEDULER;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class main extends JavaPlugin {

    public static Plugin plugin;
    public static DatabaseManager databaseManager = new DatabaseManager();

    @Override
    public void onEnable() {
        plugin = this;

        databaseManager.init();

        initRegions();
        registerEvents();
        registerCommands();
        initGUIs();

        startSchedulers();

    }

    @Override
    public void onDisable() {
        DataManager.saveAllHouses();
        for(Player p : Bukkit.getOnlinePlayers()) {
            DataManager.onlineUsers.get(p.getUniqueId().toString()).save();
            p.kickPlayer("Reload");
        }
    }

    private void startSchedulers(){
        MAIN_UPDATE_SCHEDULER.run();
    }

    private void initRegions(){
        DataManager.loadAllRegions();
        DataManager.loadAllCities();
        DataManager.loadAllHouses();
    }

    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new e_PlayerJoin(), this);
        pm.registerEvents(new e_PlayerLeave(), this);
        pm.registerEvents(new e_BlockDestroy(), this);
        pm.registerEvents(new e_BlockPlace(), this);
        pm.registerEvents(new e_PlayerChat(), this);
        pm.registerEvents(new e_ClickEvent(), this);
        pm.registerEvents(new e_PlayerInteract(), this);

    }

    private void registerCommands(){
        getCommand("money").setExecutor(new c_Money());
        getCommand("rank").setExecutor(new c_Rank());
        getCommand("mutechat").setExecutor(new c_Mutechat());
        getCommand("cheatmenu").setExecutor(new c_cheatmenu());
        getCommand("rlc").setExecutor(new c_rlc());
        getCommand("next").setExecutor(new c_next());

        getCommand("gmc").setExecutor(new c_gmc());
        getCommand("gms").setExecutor(new c_gms());
        getCommand("gmspec").setExecutor(new c_gmspec());

        getCommand("regions").setExecutor(new c_regions());
        getCommand("createregion").setExecutor(new c_createregion());
        getCommand("createcity").setExecutor(new c_createcity());

        getCommand("createhouse").setExecutor(new c_createhouse());
        getCommand("houses").setExecutor(new c_houses());

        getCommand("npc").setExecutor(new c_npc());

    }

    private void initGUIs(){
        CheatMenu_GUI.init();
        Regions_GUI.init();
    }

    public static void doSync(SyncTask task){

        BukkitScheduler scheduler = Bukkit.getScheduler();

        scheduler.callSyncMethod(plugin, () -> {
            task.run();

            return null;
        });
    }



}
