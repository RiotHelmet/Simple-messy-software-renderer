package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener{
    
    public boolean[] isKeyPressed = new boolean[100];

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() >= 0 && e.getKeyCode()  < 100) {
            isKeyPressed[e.getKeyCode()] = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() >= 0 && e.getKeyCode()  < 100) {
            isKeyPressed[e.getKeyCode()] = false;
        }
        
    }
}
