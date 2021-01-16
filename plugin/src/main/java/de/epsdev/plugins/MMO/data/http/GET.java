package de.epsdev.plugins.MMO.data.http;

import java.net.*;
import java.io.*;

public class GET {
    public static String request(String s_url)  {
        String tmp = null;

        try {
            URL url = new URL(s_url);
            URLConnection urlConnection = url.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            urlConnection.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                tmp += inputLine;
            }
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return tmp;
    }
}


