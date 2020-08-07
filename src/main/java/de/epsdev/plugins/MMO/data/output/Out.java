package de.epsdev.plugins.MMO.data.output;

public class Out {
    public static void printToConsole(String msg){
        System.out.println("[EPS-MMO] " + msg);
    }

    public static void printToConsole(int msg){
        System.out.println("[EPS-MMO] " + msg);
    }

    public static void printToConsole(float msg){
        System.out.println("[EPS-MMO] " + msg);
    }

    public static void printToConsole(String[] msg){
        System.out.println("[EPS-MMO] " + msg.length);
        for(String s : msg){
            System.out.println("[EPS-MMO] " + s);
        }
    }

    public static void printToPlayer(){

    }

    public static void printToBroadcast(){

    }
}
