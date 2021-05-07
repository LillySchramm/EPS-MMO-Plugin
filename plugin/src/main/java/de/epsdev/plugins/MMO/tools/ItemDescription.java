package de.epsdev.plugins.MMO.tools;

import java.util.ArrayList;
import java.util.List;

public class ItemDescription {
    public static final int MAX_CHARS_PER_LINE = 20;

    public static List<String> toItemDescription(String s) {
        List<String> description = new ArrayList<>();
        description.add("");

        String[] words = s.split(" ");
        int cur_chars = 0;
        int cur_line = 0;
        for (String word : words) {
            if(cur_chars + word.length() > MAX_CHARS_PER_LINE){
                cur_chars = word.length();
                cur_line ++;
                description.add(word + " ");
            }else {
                cur_chars += word.length();
                description.set(cur_line, description.get(cur_line) + word + " ");
            }
        }

        return description;
    }
}