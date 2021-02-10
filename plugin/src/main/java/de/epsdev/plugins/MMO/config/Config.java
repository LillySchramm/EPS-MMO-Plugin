package de.epsdev.plugins.MMO.config;

import de.epsdev.plugins.MMO.MAIN.main;
import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.data.output.Out;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public static void load(){
        FileConfiguration fileConfiguration = main.plugin.getConfig();
        fileConfiguration.addDefault("database_host", "");
        fileConfiguration.addDefault("database_user", "");
        fileConfiguration.addDefault("database_password", "");

        fileConfiguration.options().copyDefaults(true);

        main.plugin.saveConfig();

        mysql.mysql_host = fileConfiguration.getString("database_host");
        mysql.mysql_user = fileConfiguration.getString("database_user");
        mysql.mysql_pwd = fileConfiguration.getString("database_password");


    }

}
