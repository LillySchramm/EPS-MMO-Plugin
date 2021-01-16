package de.epsdev.plugins.MMO.npc;

public class Skin {
    public String[] texture = null;
    public String Owner = null;

    public Skin(String[] texture, String Owner){
        this.Owner = Owner;
        this.texture = texture;
    }

    public Skin(){

    }
}
