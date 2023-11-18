package math;

public class color {

    public float r,g,b;

    public color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static int getRGB(int r, int g, int b) {
        return (r << 16) + (g << 8) + b;
    }
}
