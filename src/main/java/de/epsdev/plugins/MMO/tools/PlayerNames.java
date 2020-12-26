package de.epsdev.plugins.MMO.tools;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.epsdev.plugins.MMO.data.http.GET;
import de.epsdev.plugins.MMO.data.output.Out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerNames {

    public static Map<String, String> playerNames = new HashMap<>();
    public static Map<String, String> playerUUIDS = new HashMap<>();

    public static String playerNameByUUID(String uuid) {

        if(!playerNames.containsKey(uuid)){
            String res = GET.request("https://api.mojang.com/user/profiles/" + uuid + "/names");
            String[] tmp = res.split("name");
            String last = tmp[tmp.length - 1 ];

            tmp = last.split(":");
            last = tmp[1];

            tmp = last.split(",");
            last = tmp[0];

            last = last.replace('"',' ');
            last = last.replace('}', ' ');
            last = last.replace(']', ' ');
            last = last.trim();

            playerNames.put(uuid, last);
            playerUUIDS.put(last, uuid);
        }

        return playerNames.get(uuid);
    }
}
