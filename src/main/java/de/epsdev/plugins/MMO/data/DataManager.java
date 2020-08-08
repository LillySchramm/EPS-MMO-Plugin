package de.epsdev.plugins.MMO.data;

import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.ranks.Ranks;
import org.bukkit.entity.Player;

import javax.jws.soap.SOAPBinding;
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

    public static String[] defaults = new String[]{"","","0","1","0","player"};

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
        User user = new User(player);
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

                    float user_xp = Float.parseFloat(dataArray[2]);
                    int user_level = Integer.parseInt(dataArray[3]);
                    int user_money = Integer.parseInt(dataArray[4]);

                    user = new User(dataArray[0],dataArray[1],user_xp, user_level, user_money, Ranks.getRank(dataArray[5]));

                }
            }
        }
        return user;
    }

    public static User getUserByName(String name){
        User user = null;

        for(Map.Entry<String, User> entry : onlineUsers.entrySet()) {
            String key = entry.getKey();
            User value = entry.getValue();

            if(value.displayName.equalsIgnoreCase(name)){
                user = value;
                break;
            }

        }

        return user;
    }

    public static void patchAllUsers(){
        File f = new File("plugins/eps/players");
        if(f.list() != null) {
            ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
            for (String s : names) {
                if(s.contains(".txt")){
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

                    if(dataArray.length < defaults.length){
                        for(int i = dataArray.length; i < defaults.length; i++){
                            data += defaults[i] + ";;";
                            Out.printToConsole("Patched");
                        }
                    }

                    FileWriter writer = null;
                    try {
                        writer = new FileWriter("plugins/eps/players/" + s);
                        writer.write(data);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }


    }

    public static void createUser(Player player){
        Path path = Paths.get("plugins/eps/players/"+ player.getUniqueId() +".txt");

        try {
            Files.createFile(path);
            FileWriter writer = new FileWriter("plugins/eps/players/"+ player.getUniqueId() +".txt");
            writer.write( player.getDisplayName()+ ";;");
            writer.write(player.getUniqueId()+";;");
            for(int i = 2; i < defaults.length; i++){
                writer.write(defaults[i]+";;");
            }
            writer.close();
        } catch (IOException ex) {
            System.err.println("File already exists");
        }
    }
}
