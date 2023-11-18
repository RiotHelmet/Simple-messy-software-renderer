package shaders;
import math.color;
import main.Screen;
import math.Vector2int;

public class PixelShader {
    
    /**
     * InnerPixelShader
     */

    public static float[] zBuffer = new float[Screen.width * Screen.height];


    public static class Pixel {
    
        public Vector2int position = new Vector2int(0,0);

        public float z;
        
        public color Color = new color(0, 0, 255);

        public float[] uv = new float[2];

        public Pixel(int x, int y) {
            position.x = x;
            position.y = y;
        }

    }

}
