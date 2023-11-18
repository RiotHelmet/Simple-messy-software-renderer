package main;
import math.*;

public class Camera {

    public double fov = 100;

    public float znear = (float) 0.1;
    public float zfar = (float) 10000;

    public Vector3 position = new Vector3(0,0,0);
    public double pitch = 0;
    public double yaw = 0; 

    public Vector3 right = new Vector3(1,0,0);
    public Vector3 up = new Vector3(0,1,0);
    public Vector3 forward = new Vector3(0,0,1);

    public Camera() {
    }

    public void resetViewVectors() {
        right = new Vector3(1,0,0);
        up = new Vector3(0,1,0);
        forward = new Vector3(0,0,1);
    }

    public Matrix getProjectionMatrix() {
        Matrix projMatrix = new Matrix(4, 4);

        float aspectRatio = (float) Screen.height / (float) (Screen.width);

        float f = 1 / (float) Math.tan(((fov * 2 * Math.PI)/360) / 2);

        projMatrix.at[0][0] = f / aspectRatio;
        projMatrix.at[1][1] = f;
        projMatrix.at[2][2] = zfar / (zfar - znear);
        projMatrix.at[2][3] = (-zfar * znear) / ( zfar - znear);
        projMatrix.at[3][2] = 1;

        return projMatrix;
    }

    public void rotateX(double deg) {

        Matrix rotationMatrix = new Matrix(3, 3);
        rotationMatrix.at = new float[][]{
            {1,0,0},
            {0,(float) Math.cos(deg),(float) Math.sin(deg)},
            {0,(float) -Math.sin(deg),(float) Math.cos(deg)},
        };


        forward = Matrix.multiplyMatrix(rotationMatrix, forward);

        right = Matrix.multiplyMatrix(rotationMatrix, right);
        
        up = Matrix.multiplyMatrix(rotationMatrix, up);

    }

    public void rotateY(double deg) {

        Matrix rotationMatrix = new Matrix(3, 3);
        rotationMatrix.at = new float[][]{
            {(float)Math.cos(deg),0,(float)-Math.sin(deg)},
            {0,1,0},
            {(float)Math.sin(deg),0,(float) Math.cos(deg)}
        };

        forward = Matrix.multiplyMatrix(rotationMatrix, forward);

        right = Matrix.multiplyMatrix(rotationMatrix, right);
        
        up = Matrix.multiplyMatrix(rotationMatrix, up);

    }    

    

}
