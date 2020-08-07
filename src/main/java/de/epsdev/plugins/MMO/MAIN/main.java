package de.epsdev.plugins.MMO.MAIN;

import de.epsdev.plugins.MMO.data.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    @Override
    public void onEnable() {
        initDataStructures();

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

        DataManager.loadData("eps/regions/");
    }
}
