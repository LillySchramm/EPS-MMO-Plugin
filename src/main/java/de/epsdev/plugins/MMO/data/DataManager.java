package de.epsdev.plugins.MMO.data;

import de.epsdev.plugins.MMO.GUI.*;
import de.epsdev.plugins.MMO.data.money.Money;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.Region;
import de.epsdev.plugins.MMO.data.regions.cites.City;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.ranks.Ranks;
import de.epsdev.plugins.MMO.tools.Vec3i;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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

    public static final Material GUI_FILLER = Material.THIN_GLASS;

    public static Map<String,User> onlineUsers = new HashMap<String, User>();

    public static List<Region> regions = new ArrayList<>();

    public static boolean chatMuted = false;

    public static String[] defaults_user = new String[]{"","","0","1","0","player"};
    public static String[] defaults_regions = new String[]{"","1"};
    public static String[] defaults_cities = new String[]{"",""};
    public static String[] defaults_houses = new String[]{"","1","","0>>0>>0","0>>0>>0", "0>>0>>0", "0>>0>>0", "0"}; //HouseName;HouseCost;CurrentOwnerUUID;BlocksInside;Doors;Spawnpoint;Shield;City_id;

    public static int max_id_cities = 0;
    public static int max_id_houses = 1;

    public static Map<Integer, OnClick> funs = new HashMap<>();

    public static void patch(){
        patchFile("plugins/eps/players/", defaults_user);
        patchFile("plugins/eps/regions/", defaults_regions);
        patchFile("plugins/eps/regions/cities/", defaults_cities);
        patchFile("plugins/eps/regions/cities/houses/", defaults_houses);
    }

    public static void patchFile(String path, String[] defaults){
        File f = new File(path);
        if(f.list() != null) {
            ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
            for (String s : names) {
                if(s.contains(".txt")){
                    File file = new File(path + s);
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
                            Out.printToConsole("Patched file");
                        }
                    }

                    FileWriter writer = null;
                    try {
                        writer = new FileWriter(path + s);
                        writer.write(data);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public static void performClickFunction(Player player, int i, ItemStack item, Inventory inventory){
        if (funs.containsKey(i)){
            funs.get(i).click(player, item, inventory);
        }else {
            Err.funNotRegisteredError();
        }
    }

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

    public static void createUser(Player player){
        Path path = Paths.get("plugins/eps/players/"+ player.getUniqueId() +".txt");

        try {
            Files.createFile(path);
            FileWriter writer = new FileWriter("plugins/eps/players/"+ player.getUniqueId() +".txt");
            writer.write( player.getDisplayName()+ ";;");
            writer.write(player.getUniqueId()+";;");
            for(int i = 2; i < defaults_user.length; i++){
                writer.write(defaults_user[i]+";;");
            }
            writer.close();
        } catch (IOException ex) {
            System.err.println("File already exists");
        }

    }

    public static void saveUser(User user){
        user.save();
        onlineUsers.remove(user.UUID);
        onlineUsers.put(user.UUID, user);
    }
    //-----------------------------------------------------------------
    //Region Stuff
    //-----------------------------------------------------------------

    public static void reloadRegions(){
        regions = new ArrayList<>();
        loadAllRegions();

        loadAllCities();
        loadAllHouses();

        Regions_GUI.init();

    }

    public static boolean createRegion(String name, Player executer){
        Path path = null;
        if(!isRegionAlreadyExisting(name)){
            if(regions.isEmpty()){
                path = Paths.get("plugins/eps/regions/" + 1 + ".txt");
            }else {
                path = Paths.get("plugins/eps/regions/" + ( regions.get(regions.size() - 1).id + 1) + ".txt");
            }
            try {
                Files.createFile(path);
                FileWriter writer = new FileWriter(path.toString());
                writer.write( name+ ";;");
                for(int i = 1; i < defaults_regions.length; i++){
                    writer.write(defaults_regions[i]+";;");
                }
                writer.close();
            } catch (IOException ex) {
                System.err.println("File already exists");
            }

            reloadRegions();
            return true;
        }else {

            Err.regionAlreadyExistsError(executer);
            return false;
        }


    }

    public static boolean isRegionAlreadyExisting(String name){

        for(Region region : regions){
            if(region.name.toLowerCase().equalsIgnoreCase(name.toLowerCase())){
                return true;
            }
        }

        return false;
    }

    public static Region getRegionByName(String name){
        for(Region region : regions){
            if(region.name.toLowerCase().equalsIgnoreCase(name.toLowerCase())){
                return region;
            }
        }

        return null;
    }

    public static void loadAllRegions() {
        File f = new File("plugins/eps/regions");
        if (f.list() != null) {
            ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
            for (String s : names) {

                //PATCH

                if (s.contains(".txt")) {
                    File file = new File("plugins/eps/regions/" + s);
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

                    //LOAD

                    dataArray = data.split(";;");

                    Region region = new Region(dataArray[0], Integer.parseInt(dataArray[1]));
                    region.id = Integer.parseInt(s.split(".txt")[0]);
                    regions.add(region);
                }
            }

            regions.sort(Comparator.comparing(Region::getId));


        }
    }

    public static void deleteRegion(Region region){
        File f = new File("plugins/eps/regions/" + region.id + ".txt");
        f.delete();
    }

    //-----------------------------------------------------------------
    //City Stuff
    //-----------------------------------------------------------------

    public static void createCity(String name, Region region){
        Path path = Paths.get("plugins/eps/regions/cities/" + (max_id_cities + 1) + ".txt");

        try {


            Files.createFile(path);

            FileWriter writer = new FileWriter(path.toString());
            writer.write( name+ ";;");
            writer.write(region.id+";;");
            for(int i = 2; i < defaults_cities.length; i++){
                writer.write(defaults_cities[i]+";;");
            }
            writer.close();
        } catch (IOException ex) {
            System.err.println("File already exists");
            ex.printStackTrace();
        }

        regions = new ArrayList<>();

        reloadRegions();

    }

    public static City getCityByName(String name){

        City city = null;

        for(Region region : regions){
            for(City c : region.cities){
                if(c.name.equalsIgnoreCase(name)) return c;
            }
        }

        return city;

    }

    public static Boolean isCityExisting(String name){
        if (getCityByName(name) != null){
            return true;
        }else{
            return false;
        }

    }

    public static void loadAllCities(){
        File f = new File("plugins/eps/regions/cities/");
        if (f.list() != null) {
            ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
            for (String s : names) {

                //PATCH

                if (s.contains(".txt")) {
                    File file = new File("plugins/eps/regions/cities/" + s);
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

                    City city = new City(Integer.parseInt(s.split(".txt")[0]), dataArray[0]);

                    int regionID = Integer.parseInt(dataArray[1]);

                    if(Integer.parseInt(s.split(".txt")[0]) > max_id_cities){
                        max_id_cities = Integer.parseInt(s.split(".txt")[0]);
                    }

                    for(Region region : regions){
                        if (region.id == regionID){
                            city.initGui(region);
                            region.cities.add(city);
                            region.cities.sort(Comparator.comparing(City::getId));
                        }
                    }


                }
            }


        }
        for (Region region : regions){
            region.city_gui = new City_GUI(region);
        }
    }

    public static City getCityByID(int id){
        for(Region region : regions){
            for(City city : region.cities){
                if(city.id == id) return city;
            }
        }

        return null;
    }
    //-----------------------------------------------------------------
    //House Stuff
    //-----------------------------------------------------------------

    public static int getNextHouseID(){

        int i = max_id_houses;
        max_id_houses++;
        return max_id_houses;

    }

    public static void loadAllHouses(){
        File f = new File("plugins/eps/regions/cities/houses/");
        if (f.list() != null) {
            ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
            for (String s : names) {
                if (s.contains(".txt")) {
                    File file = new File("plugins/eps/regions/cities/houses/" + s);
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

                    //LOAD

                    dataArray = data.split(";;");

                    //HouseName;HouseCost;CurrentOwnerUUID;BlocksInside;Doors;Spawnpoint;Shield;City_id;

                    int house_id = Integer.parseInt(s.split(".txt")[0]);
                    String house_name = dataArray[0];
                    Money house_cost = new Money(Integer.parseInt(dataArray[1]));
                    String house_current_ownerUUID = dataArray[2];

                    ArrayList<Vec3i> house_blocksinside = new ArrayList<Vec3i>();
                    ArrayList<Vec3i> house_doors = new ArrayList<Vec3i>();

                    String temp_string = "";

                    int i = 0;
                    Vec3i temp_vec3i = new Vec3i();

                    temp_string = dataArray[3];


                    for(String string : temp_string.split(">>")){
                        i++;
                        if(i == 1){
                            temp_vec3i.x = Integer.parseInt(string);
                        }else if(i == 2){
                            temp_vec3i.y = Integer.parseInt(string);
                        }else{
                            temp_vec3i.z = Integer.parseInt(string);
                            i = 0;
                            house_blocksinside.add(temp_vec3i);
                            temp_vec3i = new Vec3i();
                        }
                    }



                    i = 0;
                    temp_string = dataArray[4];

                    for(String string : temp_string.split(">>")){
                        i++;
                        if(i == 1){
                            temp_vec3i.x = Integer.parseInt(string);
                        }else if(i == 2){
                            temp_vec3i.y = Integer.parseInt(string);
                        }else{
                            temp_vec3i.z = Integer.parseInt(string);
                            i = 0;
                            house_doors.add(temp_vec3i);
                            temp_vec3i = new Vec3i();

                        }
                    }



                    Vec3i house_spawnpoint = new Vec3i(Integer.parseInt(dataArray[5].split(">>")[0]),
                            Integer.parseInt(dataArray[5].split(">>")[1]),
                            Integer.parseInt(dataArray[5].split(">>")[2]));
                    Vec3i house_shield = new Vec3i(Integer.parseInt(dataArray[6].split(">>")[0]),
                            Integer.parseInt(dataArray[6].split(">>")[1]),
                            Integer.parseInt(dataArray[6].split(">>")[2]));

                    City city = DataManager.getCityByID(Integer.parseInt(dataArray[7]));

                    House house = new House(house_cost,
                            house_id ,
                            house_current_ownerUUID,
                            house_name,
                            house_blocksinside,
                            house_doors,
                            house_shield,
                            house_spawnpoint,
                            city);

                    Out.printToConsole(house.blocksInside.size());
                    city.houses.add(house);
                    city.gui.houses_gui = new Dev_Houses_Gui(city);
                    house.updateSign();

                    if(house_id > max_id_houses){
                        max_id_houses = house_id;
                    }

                    Out.printToConsole("Loaded: " + house.name);

                }
            }



        }
    }
}
