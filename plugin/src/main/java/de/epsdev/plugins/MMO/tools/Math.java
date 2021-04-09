package de.epsdev.plugins.MMO.tools;

import de.epsdev.plugins.MMO.data.output.Out;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.security.SecureRandom;
import java.util.Random;


public class Math {

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom rnd = new SecureRandom();

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean isAlphabetical(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    public static boolean compareInventory(ItemStack[] inventory1, Inventory inventory2){
        if(inventory1 == null ^ inventory2 == null) return false;

        if(inventory1.length != inventory2.getStorageContents().length) return false;

        for(int i = 0; i < inventory1.length; i++){

            ItemStack itemStack1 = inventory1[i];
            ItemStack itemStack2 = inventory2.getStorageContents()[i];

            if(itemStack1 == null && itemStack2 == null) continue;

            if(itemStack1 == null || itemStack2 == null) return false;

            if(itemStack1.getAmount() != itemStack2.getAmount()) return false;

            if(itemStack1.getType() != itemStack2.getType()) return false;

            if(itemStack1.getDurability() != itemStack2.getDurability()) return false;

            ItemMeta itemMeta1 = itemStack1.getItemMeta();
            ItemMeta itemMeta2 = itemStack2.getItemMeta();

            if(itemMeta1.getDisplayName() != itemMeta2.getDisplayName() ||
                itemMeta1.getLore() != itemMeta2.getLore()) return false;

        }


        return true;
    }

    public static String randomString(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static double randomDoubleBetween(double min, double max){
        Random r = new Random();
        double randomValue = min + (max - min) * r.nextDouble();

        return randomValue;
    }

    public static BufferedImage base64_toImage(String base64){
        BufferedImage img = null;

        try {
            byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64);
            img = ImageIO.read(new ByteArrayInputStream(imageBytes));
        }catch (Exception e){
            e.printStackTrace();
        }

        return img;
    }

    public static int min(int min, int f) {
        return f < min ? min : f;
    }

    public static int max(int max, int f){
        return f > max ? max : f;
    }

    public static int minmax(int min, int max, int f){
        f = min(min, f);
        f = max(max, f);

        return f;
    }


}
