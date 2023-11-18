package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import shaders.PixelShader;


public class Screen extends Canvas implements Runnable{
    
    public static final int width = 1920;
    public static final int height = 1080;
    public int targetFPS = 10000;
    public double targetDeltaT = 1000 / (double)targetFPS; // ms
    public double deltaT;

    public Render render;

    Display parent;
    public static Camera camera;

    public KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    public int[] pixels;
    private BufferedImage img;


    Object[] objects;

    Object obj;

    public Screen() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        // this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ( (DataBufferInt) img.getRaster().getDataBuffer()).getData();
        render = new Render();

    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {

        // Gameloop
        double drawInterval = 1_000_000_000 / targetFPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if( delta >= 1 ) {
                update();
                render();

                delta--;
                drawCount++;
            }

            if(timer >= 1_000_000_000) {
                System.out.println("Running at " + drawCount + " FPS");
                drawCount = 0;
                timer = 0;
            }

        }

    }

    public void update() {


        // left, up, right, down = 37,38,39,40

        float speed = (float) 5;
        float pitchSpeed = (float) 0.01;

        if(keyH.isKeyPressed[(int) 'W']) {
            camera.position.z += camera.forward.z * speed;
            camera.position.x += camera.forward.x * speed;
        }
        if(keyH.isKeyPressed[(int) 'A']) {
            camera.position.x += camera.right.x * -speed;
            camera.position.z += camera.right.z * -speed;
        }
        if(keyH.isKeyPressed[(int) 'S']) {
            camera.position.x += camera.forward.x * -speed;
            camera.position.z += camera.forward.z * -speed;
        }
        if(keyH.isKeyPressed[(int) 'D']) {
            camera.position.x += camera.right.x * speed;
            camera.position.z += camera.right.z * speed;
        }
        if(keyH.isKeyPressed[(int) 'Q']) {
            camera.position.y += camera.up.y * -speed;
        }
        if(keyH.isKeyPressed[(int) 'E']) {
            camera.position.y += camera.up.y * speed;
        }

        if(keyH.isKeyPressed[37]) {
            camera.yaw -= pitchSpeed;
            camera.resetViewVectors();
            camera.rotateX(-camera.pitch);
            camera.rotateY(-camera.yaw);

        }
        if(keyH.isKeyPressed[39]) {
            camera.yaw += pitchSpeed;
            camera.resetViewVectors();
            camera.rotateX(-camera.pitch);
            camera.rotateY(-camera.yaw);
        }

        if(keyH.isKeyPressed[38]) {
            camera.pitch += pitchSpeed;
            camera.resetViewVectors();
            camera.rotateX(-camera.pitch);
            camera.rotateY(-camera.yaw);

        }
        if(keyH.isKeyPressed[40]) {
            camera.pitch -= pitchSpeed;
            camera.resetViewVectors();
            camera.rotateX(-camera.pitch);
            camera.rotateY(-camera.yaw);
        }

    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        for (int i = 0; i < width * height; i++) {
            pixels[i] = 0;
            PixelShader.zBuffer[i] = 1;
        }

        for (int i = 0; i < objects.length; i++) {
            objects[i].draw();
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, width, height, null);
        g.dispose();
        bs.show();

}}
