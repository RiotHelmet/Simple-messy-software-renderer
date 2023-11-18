package main;
import javax.swing.*;


public class Display {

    public static JFrame window;
    public static Screen gameScreen;

    private static Display display = null;

    public Display() {};

    public void init() {
        if(display == null) {
            display = new Display();
        }
        
        window = new JFrame("Test"); 
        gameScreen = new Screen();
        window.add(gameScreen);
        gameScreen.parent = this;
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);    

    }

}
