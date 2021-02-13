package de.epsdev.plugins.MMO.tools;

public class D_RGB {
    public double r;
    public double g;
    public double b;

    public D_RGB(int r, int g, int b){
        this.r = r / 255D;
        this.g = g / 255D;
        this.b = b / 255D;
    }

    public double[] toArray(){
        return new double[]{this.r,this.g,this.b};
    }
}
