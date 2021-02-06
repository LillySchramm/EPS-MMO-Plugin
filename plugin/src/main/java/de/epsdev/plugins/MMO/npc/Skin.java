package de.epsdev.plugins.MMO.npc;

public class Skin {
    public String texture_data = "";
    public String texture_signature = "";

    public Skin(String texture_data, String texture_signature){
        this.texture_data = texture_data;
        this.texture_signature = texture_signature;
    }

    public Skin(){

    }

    public String toString(){
        return this.texture_data + "<!>" + this.texture_signature;
    }
}
