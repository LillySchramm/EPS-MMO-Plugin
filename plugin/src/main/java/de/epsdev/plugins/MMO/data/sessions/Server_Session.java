package de.epsdev.plugins.MMO.data.sessions;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.mysql.mysql;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.npc.NPC;
import de.epsdev.plugins.MMO.npc.NPC_Manager;
import de.epsdev.plugins.MMO.tools.Math;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Server_Session {
    public String id = "0";

    public Server_Session(){
        while(this.id == "0"){
            this.id = Math.randomString(256);
            ResultSet set = mysql.query("SELECT ID FROM `eps_sessions`.`server_sessions` WHERE SESSION_ID = '" + this.id + "';");

            try {
                if (set.next()) this.id = "0";
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        mysql.query("INSERT INTO `eps_sessions`.`server_sessions` (`ID`, `SESSION_ID`) VALUES (NULL, '" + this.id + "');");
    }

    public void processCommands(){
        try {
            ResultSet rs = mysql.query("SELECT CMD FROM `eps_sessions`.`server_commands` WHERE `FOR` = '" + this.id + "';");

            while(rs.next()){
                String cmd = rs.getString("CMD");
                cmd = cmd.toLowerCase();

                String [] _cmd = cmd.split(" ");

                switch (_cmd[0]){
                    case "server":
                        if (_cmd[1].equalsIgnoreCase("reload")){
                            Bukkit.getServer().reload();
                        }
                        break;
                    case "npc":
                        if(_cmd[1].equalsIgnoreCase("reload")){
                            Out.printToConsole(Integer.parseInt(_cmd[2]));
                            for (NPC npc : NPC_Manager.NPCs){
                                if(npc.npc_id == Integer.parseInt(_cmd[2])){
                                    NPC_Manager.fullReloadNPC(npc);
                                    break;
                                }
                            }
                        }
                        break;
                }

            }

            mysql.query("DELETE FROM `eps_sessions`.`server_commands` WHERE `FOR` = '" + this.id + "';");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void unload(){
        mysql.query("DELETE FROM `eps_sessions`.`server_sessions` WHERE SESSION_ID = '"+ this.id +"';");
    }
}
