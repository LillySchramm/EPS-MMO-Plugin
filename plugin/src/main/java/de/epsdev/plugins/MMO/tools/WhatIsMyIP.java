package de.epsdev.plugins.MMO.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class WhatIsMyIP {

    public static String getIP() {
        String ip = "";

        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            ip = in.readLine();
        }catch (Exception e){
            e.printStackTrace();
        }

        return ip;
    }

}
