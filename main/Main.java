package main;
import java.io.IOException;

/**
 * main
 */

public class Main {

    public static void main(String[] args) throws IOException{

        Display display = new Display();
        display.init();
        
        int w = 1;
        Display.gameScreen.objects = new Object[w*w*w];


        for (int x = 0; x < w; x++) {
            for (int y = 0; y < w; y++) {
                for (int z = 0; z < w; z++) {

                    int i = x * w * w + y * w + z;

                    Display.gameScreen.objects[i] = objectFileReader.readOBJ("main/flying-unicorn.obj");
                    Display.gameScreen.objects[i].size = 10;
                    Display.gameScreen.objects[i].orientation.z = (float) Math.PI;
                    Display.gameScreen.objects[i].position.z = 400;
                    Display.gameScreen.objects[i].position.x = x * 100;
                    Display.gameScreen.objects[i].position.y = 11;
                    Display.gameScreen.objects[i].position.z += z * 100;
                }
            }
        }

        Camera camera = new Camera();
        Object.camera = camera;
        Screen.camera = camera;
        Display.gameScreen.startGameThread();
        
    }

}