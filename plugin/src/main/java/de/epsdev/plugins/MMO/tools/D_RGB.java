package de.epsdev.plugins.MMO.tools;

public class D_RGB {
    public double r;
    public double g;
    public double b;

    public int o_r;
    public int o_g;
    public int o_b;

    public D_RGB(int r, int g, int b){
        this.r = r / 255D;
        this.g = g / 255D;
        this.b = b / 255D;

        this.o_r = r;
        this.o_g = g;
        this.o_b = b;

    }

    public double[] toArray(){
        return new double[]{this.r,this.g,this.b};
    }
}
