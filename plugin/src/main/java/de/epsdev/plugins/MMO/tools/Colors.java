package de.epsdev.plugins.MMO.tools;

public class Colors {
    public static short WHITE = 0;
    public static short ORANGE = 1;
    public static short MAGENTA = 2;
    public static short LIGHT_BLUE = 3;
    public static short YELLOW = 4;
    public static short LIME = 5;
    public static short PINK = 6;
    public static short GREY = 7;
    public static short LIGHT_GREY = 8;
    public static short CYAN = 9;
    public static short PURPLE = 10;
    public static short BLUE = 11;
    public static short BROWN = 12;
    public static short GREEN = 13;
    public static short RED = 14;
    public static short BLACK = 15;

    public static String removeBukkitColorCodes(String toAlter){
        toAlter.replace("&0", ""); toAlter.replace("&1", ""); toAlter.replace("&2", "");
        toAlter.replace("&3", ""); toAlter.replace("&4", ""); toAlter.replace("&5", "");
        toAlter.replace("&6", ""); toAlter.replace("&7", ""); toAlter.replace("&8", "");
        toAlter.replace("&9", ""); toAlter.replace("&A", ""); toAlter.replace("&B", "");
        toAlter.replace("&C", ""); toAlter.replace("&D", ""); toAlter.replace("&E", "");
        toAlter.replace("&F", ""); toAlter.replace("&M", ""); toAlter.replace("&N", "");
        toAlter.replace("&L", ""); toAlter.replace("&K", ""); toAlter.replace("&O", "");
        return toAlter;
    }


}
