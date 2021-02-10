package de.epsdev.plugins.MMO.data.mysql;


public class DatabaseManager {

    public void init() {
        mysql.connect();

        mysql.createDatabaseIfNotExists("eps_users");
        mysql.createDatabaseIfNotExists("eps_regions");
        mysql.createDatabaseIfNotExists("eps_sessions");

        mysql.createTableIfNotExists("eps_users", "players","`ID` INT NOT NULL AUTO_INCREMENT , `UUID` TEXT NOT NULL , `RANK` TEXT NOT NULL , `MONEY` INT NOT NULL , PRIMARY KEY (`ID`)");
        mysql.createTableIfNotExists("eps_users", "characters","`ID` INT NOT NULL AUTO_INCREMENT , `UUID` TEXT NOT NULL , `STATS` TEXT NOT NULL , `LAST_POS` TEXT NOT NULL, `NAME` TEXT NOT NULL, `EXP` INT NOT NULL, `LEVEL` INT NOT NULL, PRIMARY KEY (`ID`)");

        mysql.createTableIfNotExists("eps_regions", "regions", "`ID` INT NOT NULL AUTO_INCREMENT , `NAME` TEXT NOT NULL , `LEVEL` INT NOT NULL , PRIMARY KEY (`ID`)");
        mysql.createTableIfNotExists("eps_regions", "cities","`ID` INT NOT NULL AUTO_INCREMENT , `NAME` TEXT NOT NULL, `REGION_ID` INT NOT NULL , PRIMARY KEY (`ID`)");
        mysql.createTableIfNotExists("eps_regions", "houses","`ID` INT NOT NULL AUTO_INCREMENT , `NAME` TEXT NOT NULL , `COSTS` INT NOT NULL , `OWNER_UUID` TEXT NOT NULL , `BLOCKS_INSIDE` TEXT NOT NULL , `DOORS` TEXT NOT NULL , `SPAWN_POS` TEXT NOT NULL , `SHIELD_POS` TEXT NOT NULL , `CITY_ID` INT NOT NULL, `RENTTIME` INT NOT NULL , PRIMARY KEY (`ID`)");
        mysql.createTableIfNotExists("eps_regions", "npc","`ID` INT NOT NULL AUTO_INCREMENT , `NAME` TEXT NOT NULL, `SCRIPT` TEXT NOT NULL , `POS` TEXT NOT NULL, `ROTATION` TEXT NOT NULL, `SKIN` TEXT NOT NULL, PRIMARY KEY (`ID`)");

        mysql.createTableIfNotExists("eps_sessions", "sessions","`ID` INT NOT NULL AUTO_INCREMENT , `SESSION_ID` TEXT NOT NULL, `UUID` TEXT NOT NULL, `ACTIVE` INT NOT NULL, PRIMARY KEY (`ID`)");
        mysql.createTableIfNotExists("eps_sessions", "server_sessions","`ID` INT NOT NULL AUTO_INCREMENT , `SESSION_ID` TEXT NOT NULL, `IP` TEXT NOT NULL, PRIMARY KEY (`ID`)");
        mysql.createTableIfNotExists("eps_sessions", "server_commands","`ID` INT NOT NULL AUTO_INCREMENT , `FOR` TEXT NOT NULL, `CMD` TEXT NOT NULL, PRIMARY KEY (`ID`)");
    }

    public DatabaseManager(){}

}
