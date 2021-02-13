package de.epsdev.plugins.MMO.GUI.dev;

import de.epsdev.plugins.MMO.GUI.Base_Gui;
import de.epsdev.plugins.MMO.npc.eNpc.eNpc;
import org.bukkit.entity.Player;

public class DEV_eNpc_GUI {

    public Base_Gui gui;

    public DEV_eNpc_GUI(eNpc enpc){
        this.gui = new Base_Gui("ID: " + enpc.eNpc_id);
    }

    public void show(Player player){
        gui.show(player);
    }

}
