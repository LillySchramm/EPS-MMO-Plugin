package de.epsdev.plugins.MMO.MAIN;

import de.epsdev.plugins.MMO.commands.c_Money;
import de.epsdev.plugins.MMO.commands.c_Rank;
import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.events.e_BlockDestroy;
import de.epsdev.plugins.MMO.events.e_BlockPlace;
import de.epsdev.plugins.MMO.events.e_PlayerJoin;
import de.epsdev.plugins.MMO.events.e_PlayerLeave;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    @Override
    public void onEnable() {
        initDataStructures();
        DataManager.patchAllUsers();
        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void initDataStructures(){
        DataManager.createDir("eps/");
        DataManager.createDir("eps/players/");
        DataManager.createDir("eps/regions/");
        DataManager.createDir("eps/regions/cities/");
        DataManager.createDir("eps/regions/cities/houses/");
        DataManager.createDir("eps/regions/mobspawns/");

    }

    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new e_PlayerJoin(), this);
        pm.registerEvents(new e_PlayerLeave(), this);
        pm.registerEvents(new e_BlockDestroy(), this);
        pm.registerEvents(new e_BlockPlace(), this);
    }

    private void registerCommands(){
        getCommand("money").setExecutor(new c_Money());
        getCommand("rank").setExecutor(new c_Rank());
    }


}
