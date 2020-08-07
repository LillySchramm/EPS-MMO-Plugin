package de.epsdev.plugins.MMO.data;

import de.epsdev.plugins.MMO.data.output.Out;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class DataManager {
    public static void loadData(String dir){
        File f = new File("plugins/" + dir);
        if(f.list() != null){
            ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
            for(String s : names){
                Out.printToConsole(s);
            }
        }

    }

    public static void createDir(String dir){
        new File("plugins/" + dir).mkdirs();
        Out.printToConsole("Created '" + dir + "'");

    }


}
