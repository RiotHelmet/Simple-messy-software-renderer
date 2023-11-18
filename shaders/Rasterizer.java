package shaders;
import main.Display;
import main.Screen;
import math.color;
import shaders.PixelShader.Pixel;

public class Rasterizer {
    
    private int[][] scanline;
    private color[][] colors;
    private float[][] zBuffer;

    private int yMin;

    
    public void triangle(Pixel[] points) {
        
        for (int i = 0; i < points.length; i++) {
            BresenHamLine(points[i], points[(i + 1) % points.length]);
        }

    }

    public void fillTriangle(Pixel[] points) {

        int yMax = 0;
        yMin = points[0].position.y;

        for (int i = 0; i < points.length; i++) {
            yMax = Math.max(yMax, points[i].position.y);
            yMin = Math.min(yMin, points[i].position.y);
        }

        int yDist = yMax - yMin;

        scanline = new int[yDist + 1][2];
        colors = new color[yDist + 1][2];
        zBuffer = new float[yDist + 1][2];


        for (int y = 0; y < scanline.length; y++) {
            scanline[y][0] = Screen.width - 1;
        }

        for (int i = 0; i < points.length; i++) {
            scanline(points[i], points[(i + 1) % points.length]);
        }



        for (int y = 0; y < scanline.length; y++) {
            for (int x = scanline[y][0]; x < scanline[y][1]; x++) {

                float ratio2 = (float)(x - scanline[y][0]) / (float)(scanline[y][1] - scanline[y][0]);
                float ratio1 = (float)(scanline[y][1] - x) / (float)(scanline[y][1] - scanline[y][0]);

                float z = ratio1 * zBuffer[y][0] + ratio2 * zBuffer[y][1];

                if(z < PixelShader.zBuffer[(y + yMin) * Screen.width + x]) {

                    PixelShader.zBuffer[(y + yMin) * Screen.width + x] = z;
                    float red = ratio1 * colors[y][0].r + ratio2 * colors[y][1].r;
                    float green = ratio1 * colors[y][0].g + ratio2 * colors[y][1].g;
                    float blue = ratio1 * colors[y][0].b + ratio2 * colors[y][1].b;

                    setRGB(x, (y + yMin), color.getRGB((int)(red),(int) (green), (int)blue));
                }
            }
        }

    }

    public void setRGB(int x, int y, int Color) {
        Display.gameScreen.pixels[(y * Screen.width + x)] = Color;
    }


    public void BresenHamLine(Pixel P1, Pixel P2) {

        if(Math.abs(P2.position.y-P1.position.y) < Math.abs(P2.position.x-P1.position.x)) {
            if(P1.position.x > P2.position.x) {
                 lineLow(P2, P1);
            } else {
                 lineLow(P1, P2);
            }
        } else {
            if(P1.position.y > P2.position.y) {
                 lineHigh(P2, P1);
            } else {
                 lineHigh(P1, P2);
            }
        }

    }

    private void lineLow(Pixel P1, Pixel P2) {
        int x0 = P1.position.x;
        int x1 = P2.position.x;
        int y0 = P1.position.y;
        int y1 = P2.position.y;

        int dx = x1 - x0;
        int dy = y1 - y0;

        int yi = 1;
        if(dy < 0) {
            yi *= -1;
            dy *= -1;
        }

        int D = 2 * dy - dx;
        int y = y0;

        for (int x = x0; x < x1; x++) {
            
            if(y < Screen.height && y >= 0 && x < Screen.width && x >= 0) 
            {
                setRGB(x, y, 1000000);

            };

            if( D > 0 ) {
                y += yi;
                D += 2 * (dy - dx);
            } else {
                D += 2 * dy;
            }
        }
    }

    private void lineHigh(Pixel P1, Pixel P2) {
        int x0 = P1.position.x;
        int x1 = P2.position.x;
        int y0 = P1.position.y;
        int y1 = P2.position.y;

        int dx = x1 - x0;
        int dy = y1 - y0;

        int xi = 1;
        if(dx < 0) {
            xi *= -1;
            dx *= -1;
        }

        int D = 2 * dy - dx;
        int x = x0;

        for (int y = y0; y < y1; y++) {
            
            if(y < Screen.height && y >= 0 && x < Screen.width && x >= 0) 
            {
                setRGB(x, y, 1000000);

            };
            
            if( D > 0 ) {
                x += xi;
                D += 2 * (dx - dy);
            } else {
                D += 2 * dx;
            }
        }
    }
    

    private void scanline(Pixel P1, Pixel P2) {
        if(Math.abs(P2.position.y-P1.position.y) < Math.abs(P2.position.x-P1.position.x)) {
            if(P1.position.x > P2.position.x) {
                 scanlineLow(P2, P1);
            } else {
                 scanlineLow(P1, P2);
            }
        } else {
            if(P1.position.y > P2.position.y) {
                 scanlineHigh(P2, P1);
            } else {
                 scanlineHigh(P1, P2);
            }
        }
    }

    private void scanlineLow(Pixel P1, Pixel P2) {
        int x0 = P1.position.x;
        int x1 = P2.position.x;
        int y0 = P1.position.y;
        int y1 = P2.position.y;

        int dx = x1 - x0;
        int dy = y1 - y0;

        int yi = 1;
        if(dy < 0) {
            yi *= -1;
            dy *= -1;
        }

        int D = 2 * dy - dx;
        int y = y0;

        for (int x = x0; x <= x1; x++) {
            
            if(y < Screen.height && y >= 0 && x < Screen.width && x >= 0) 
            {
                float ratio2 = (float)(x - x0) / (float)(x1 - x0);
                float ratio1 = (float)(x1 - x) / (float)(x1 - x0);

                int red = (int)(ratio1 * P1.Color.r + ratio2 * P2.Color.r);
                int green = (int)(ratio1 * P1.Color.g + ratio2 * P2.Color.g);
                int blue = (int)(ratio1 * P1.Color.b + ratio2 * P2.Color.b);

                float z = ratio1 * P1.z + ratio2 * P2.z;

                // setRGB(x, y, color.getRGB((int)(red),(int) (green), (int)blue));


                if(x > scanline[y - yMin][1]) {
                    scanline[y - yMin][1] = x;
                    colors[y - yMin][1] = new color(red, green, blue);
                    zBuffer[y - yMin][1] = z;
                }

                if(x < scanline[y - yMin][0]) {
                    scanline[y - yMin][0] = x;
                    colors[y - yMin][0] = new color(red, green, blue);
                    zBuffer[y - yMin][0] = z;
                }
            };

            if( D > 0 ) {
                y += yi;
                D += 2 * (dy - dx);
            } else {
                D += 2 * dy;
            }
        }
    }

    private void scanlineHigh(Pixel P1, Pixel P2) {
        int x0 = P1.position.x;
        int x1 = P2.position.x;
        int y0 = P1.position.y;
        int y1 = P2.position.y;

        int dx = x1 - x0;
        int dy = y1 - y0;

        int xi = 1;
        if(dx < 0) {
            xi *= -1;
            dx *= -1;
        }

        int D = 2 * dy - dx;
        int x = x0;

        for (int y = y0; y <= y1; y++) {
            
            if(y < Screen.height && y >= 0 && x < Screen.width && x >= 0) 
            {
                float ratio2 = (float)(y - y0) / (float)(y1 - y0);
                float ratio1 = (float)(y1 - y) / (float)(y1 - y0);

                int red = (int)(ratio1 * P1.Color.r + ratio2 * P2.Color.r);
                int green = (int)(ratio1 * P1.Color.g + ratio2 * P2.Color.g);
                int blue = (int)(ratio1 * P1.Color.b + ratio2 * P2.Color.b);

                float z = ratio1 * P1.z + ratio2 * P2.z;

                // setRGB(x, y, color.getRGB((int)(red),(int) (green), (int)blue));

                if(x > scanline[y - yMin][1]) {
                    scanline[y - yMin][1] = x;
                    colors[y - yMin][1] = new color(red, green, blue);
                    zBuffer[y - yMin][1] = z;
                }

                if(x < scanline[y - yMin][0]) {
                    scanline[y - yMin][0] = x;
                    colors[y - yMin][0] = new color(red, green, blue);
                    zBuffer[y - yMin][0] = z;
                }
            };
            
            if( D > 0 ) {
                x += xi;
                D += 2 * (dx - dy);
            } else {
                D += 2 * dx;
            }
        }
    }
    
    
}

