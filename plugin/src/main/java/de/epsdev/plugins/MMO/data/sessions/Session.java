package de.epsdev.plugins.MMO.data.sessions;

import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.tools.Math;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Session {
    public String id = "0";

    public Session(String uuid){
        while(this.id == "0"){
            this.id = Math.randomString(256);
            ResultSet set = mysql.query("SELECT ID FROM `eps_sessions`.`sessions` WHERE SESSION_ID = '" + this.id + "';");
            try {
                if (set.next()) this.id = "0";
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        mysql.query("INSERT INTO `eps_sessions`.`sessions` (`ID`, `SESSION_ID`, `UUID`, `ACTIVE`) VALUES (NULL, '" + this.id + "', '"+ uuid +"', '1');");
    }

    public void unload(){
        mysql.query("UPDATE `eps_sessions`.`sessions` SET `ACTIVE` = '0' WHERE SESSION_ID = '"+ this.id +"';");
    }
}
