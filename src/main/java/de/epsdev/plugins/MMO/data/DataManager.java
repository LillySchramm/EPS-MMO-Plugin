package de.epsdev.plugins.MMO.data;

import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DataManager {

    public static Map<String,User> onlineUsers = new HashMap<String, User>();

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

    public static User getUser(Player player){
        File f = new File("plugins/eps/players");
        if(f.list() != null){
            ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
            for(String s : names){
                if(s.equalsIgnoreCase(player.getUniqueId().toString() + ".txt")){
                    File file = new File("plugins/eps/players/" + s);
                    Scanner myReader = null;
                    try {
                        myReader = new Scanner(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String data = "";
                    while (myReader.hasNextLine()) {
                        data += myReader.nextLine();
                    }
                    myReader.close();
                    String[] dataArray = data.split(";;");
                    Out.printToConsole(dataArray);
                    return new User(dataArray[0],dataArray[1],Float.parseFloat(dataArray[2]),Integer.getInteger(dataArray[3]));

                }
            }
        }
        return new User(player);
    }

    public static void createUser(Player player){
        Path path = Paths.get("plugins/eps/players/"+ player.getUniqueId() +".txt");

        try {
            Files.createFile(path);
            FileWriter writer = new FileWriter("plugins/eps/players/"+ player.getUniqueId() +".txt");
            writer.write( player.getDisplayName()+ ";;");
            writer.write(player.getUniqueId()+";;");
            writer.write("0;;");
            writer.write("1;;");
            writer.close();
        } catch (IOException ex) {
            System.err.println("File already exists");
        }



    }


}
