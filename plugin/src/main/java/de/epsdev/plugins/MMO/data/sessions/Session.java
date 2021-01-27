package de.epsdev.plugins.MMO.data.sessions;

import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.tools.Math;

import java.util.UUID;

public class Session {
    public String id;

    public Session(String uuid){
        this.id = Math.randomString(256);
        mysql.query("INSERT INTO `eps_sessions`.`sessions` (`ID`, `SESSION_ID`, `UUID`, `ACTIVE`) VALUES (NULL, '" + this.id + "', '"+ uuid +"', '1');");
    }

    public void unload(){
        mysql.query("UPDATE `eps_sessions`.`sessions` SET `ACTIVE` = '0' WHERE SESSION_ID = '"+ this.id +"';");
    }
}
