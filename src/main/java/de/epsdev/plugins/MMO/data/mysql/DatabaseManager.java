package de.epsdev.plugins.MMO.data.mysql;

import de.epsdev.plugins.MMO.data.DataManager;

import java.sql.SQLException;


public class DatabaseManager {

    public void init() {
        try {

            mysql.connect();

            mysql.createDatabaseIfNotExists("eps_users");
            mysql.createDatabaseIfNotExists("eps_regions");

            mysql.createTableIfNotExists("eps_users", "players","`ID` INT NOT NULL AUTO_INCREMENT , `UUID` TEXT NOT NULL , `RANK` TEXT NOT NULL , `MONEY` INT NOT NULL , PRIMARY KEY (`ID`)");
            mysql.createTableIfNotExists("eps_regions", "regions", "`ID` INT NOT NULL AUTO_INCREMENT , `NAME` TEXT NOT NULL , `LEVEL` INT NOT NULL , PRIMARY KEY (`ID`)");
            mysql.createTableIfNotExists("eps_regions", "cities","`ID` INT NOT NULL AUTO_INCREMENT , `NAME` TEXT NOT NULL, `REGION_ID` INT NOT NULL , PRIMARY KEY (`ID`)");
            mysql.createTableIfNotExists("eps_regions", "houses","`ID` INT NOT NULL AUTO_INCREMENT , `NAME` TEXT NOT NULL , `COSTS` INT NOT NULL , `OWNER_UUID` TEXT NOT NULL , `BLOCKS_INSIDE` TEXT NOT NULL , `DOORS` TEXT NOT NULL , `SPAWN_POS` TEXT NOT NULL , `SHIELD_POS` TEXT NOT NULL , `CITY_ID` INT NOT NULL , PRIMARY KEY (`ID`)");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public DatabaseManager(){

    }

}
