package de.epsdev.plugins.MMO.npc;

public class Skin {
    public String texture_data = "";
    public String texture_signature = "";

    public Skin(String texture_data, String texture_signature){
        this.texture_data = texture_data;
        this.texture_signature = texture_signature;
    }

    public Skin(String raw){
        String[] _raw = raw.split("<!>");

        this.texture_data = _raw[0];
        this.texture_signature = _raw[1];
    }

    public String toString(){
        return this.texture_data + "<!>" + this.texture_signature;
    }
}
