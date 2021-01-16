package de.epsdev.plugins.MMO.data.money;

import org.bukkit.ChatColor;

public class Money {
    public int amount = 0;

    public Money(){

    }

    public Money(int amount){
        this.amount = amount;
    }

    public boolean decreaseMoney(int a){
        if(this.amount - a >= 0){
            this.amount -= a;
            return true;
        }

        return false;
    }

    public String formatString(){
        String ret = "";
        int i = this.amount;

        int temp = i/10000;
        ret += ChatColor.GOLD + Integer.toString(temp) + "G ";
        i -= 10000 * temp;

        temp = i / 100;
        ret += ChatColor.DARK_GRAY + Integer.toString(temp) + "S ";
        i -= 100 * temp;

        ret += ChatColor.RED + Integer.toString(i) + "C ";

        return ret;
    }

}
