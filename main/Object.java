package main;

import math.*;
import shaders.Rasterizer;
import shaders.VertexBuffer;
import shaders.PixelShader.Pixel;

public class Object {

    public float size = 10;
    
    static Rasterizer rasterizer = new Rasterizer();

    public Vector3 position = new Vector3(0,0,0);
    public Vector3 orientation = new Vector3(0,0,0);

    Model model = new Model();

    public static Camera camera;

    public Object() {
        orientation.x += 3.14 / 2;
    }

    public void draw() {
        orientation.y += 0.05;

        VertexBuffer VertexBuffer = new VertexBuffer(model.faces.length * Model.VERTICES_PER_FACE);

        float[][] rotationMatrixX = Matrix.rotationMatrixX(orientation.x).at;
        float[][] rotationMatrixY = Matrix.rotationMatrixY(orientation.y).at;
        float[][] rotationMatrixZ = Matrix.rotationMatrixZ(orientation.z).at;

        float[][] rotationMatrixY_camera = Matrix.rotationMatrixY(camera.yaw).at;
        float[][] rotationMatrixX_camera = Matrix.rotationMatrixX(camera.pitch).at;

        Pixel[] points = new Pixel[Model.VERTICES_PER_FACE];

        points[0] = new Pixel(0, 0);
        points[1] = new Pixel(0, 0);
        points[2] = new Pixel(0, 0);

        for (int faceIndex = 0; faceIndex < model.faces.length; faceIndex++) {

            for (int v = 0; v < model.faces[faceIndex].length; v++) {

            int i = faceIndex * Model.ELEMENTS_PER_FACE + v * Model.VERTICES_PER_FACE;

            int[] face = model.faces[faceIndex];

            // Position
            VertexBuffer.vertices[i] = model.vertices.at[face[v] - 1][0]  * size;
            VertexBuffer.vertices[i + 1] = model.vertices.at[face[v] - 1][1] * size;
            VertexBuffer.vertices[i + 2] = model.vertices.at[face[v] - 1][2] * size;
            VertexBuffer.vertices[i + 3] = 1;
            // UV
            if(model.uv.at.length > 0) {
            VertexBuffer.vertices[i + 4] = model.uv.at[model.faceUV[faceIndex][v] - 1][0];
            VertexBuffer.vertices[i + 5] = model.uv.at[model.faceUV[faceIndex][v] - 1][1];
            }
            // Normal
            VertexBuffer.vertices[i + 6] = model.normals.at[model.faceNormals[faceIndex] - 1][0] + VertexBuffer.vertices[i];
            VertexBuffer.vertices[i + 7] = model.normals.at[model.faceNormals[faceIndex] - 1][1] + VertexBuffer.vertices[i + 1];
            VertexBuffer.vertices[i + 8] = model.normals.at[model.faceNormals[faceIndex] - 1][2] + VertexBuffer.vertices[i + 2];
            VertexBuffer.vertices[i + 9] = 1;

            // Orientation

            float v1 = rotationMatrixX[0][0] * VertexBuffer.vertices[i] + rotationMatrixX[0][1] * VertexBuffer.vertices[i + 1] + rotationMatrixX[0][2] * VertexBuffer.vertices[i + 2] + rotationMatrixX[0][3] * VertexBuffer.vertices[i + 3];
            float v2 = rotationMatrixX[1][0] * VertexBuffer.vertices[i] + rotationMatrixX[1][1] * VertexBuffer.vertices[i + 1] + rotationMatrixX[1][2] * VertexBuffer.vertices[i + 2] + rotationMatrixX[1][3] * VertexBuffer.vertices[i + 3];
            float v3 = rotationMatrixX[2][0] * VertexBuffer.vertices[i] + rotationMatrixX[2][1] * VertexBuffer.vertices[i + 1] + rotationMatrixX[2][2] * VertexBuffer.vertices[i + 2] + rotationMatrixX[2][3] * VertexBuffer.vertices[i + 3];


            VertexBuffer.vertices[i] = v1;
            VertexBuffer.vertices[i + 1] = v2;
            VertexBuffer.vertices[i + 2] = v3;

            v1 = rotationMatrixY[0][0] * VertexBuffer.vertices[i] + rotationMatrixY[0][1] * VertexBuffer.vertices[i + 1] + rotationMatrixY[0][2] * VertexBuffer.vertices[i + 2] + rotationMatrixY[0][3] * VertexBuffer.vertices[i + 3];
            v2 = rotationMatrixY[1][0] * VertexBuffer.vertices[i] + rotationMatrixY[1][1] * VertexBuffer.vertices[i + 1] + rotationMatrixY[1][2] * VertexBuffer.vertices[i + 2] + rotationMatrixY[1][3] * VertexBuffer.vertices[i + 3];
            v3 = rotationMatrixY[2][0] * VertexBuffer.vertices[i] + rotationMatrixY[2][1] * VertexBuffer.vertices[i + 1] + rotationMatrixY[2][2] * VertexBuffer.vertices[i + 2] + rotationMatrixY[2][3] * VertexBuffer.vertices[i + 3];


            VertexBuffer.vertices[i] = v1;
            VertexBuffer.vertices[i + 1] = v2;
            VertexBuffer.vertices[i + 2] = v3;

            v1 = rotationMatrixZ[0][0] * VertexBuffer.vertices[i] + rotationMatrixZ[0][1] * VertexBuffer.vertices[i + 1] + rotationMatrixZ[0][2] * VertexBuffer.vertices[i + 2] + rotationMatrixZ[0][3] * VertexBuffer.vertices[i + 3];
            v2 = rotationMatrixZ[1][0] * VertexBuffer.vertices[i] + rotationMatrixZ[1][1] * VertexBuffer.vertices[i + 1] + rotationMatrixZ[1][2] * VertexBuffer.vertices[i + 2] + rotationMatrixZ[1][3] * VertexBuffer.vertices[i + 3];
            v3 = rotationMatrixZ[2][0] * VertexBuffer.vertices[i] + rotationMatrixZ[2][1] * VertexBuffer.vertices[i + 1] + rotationMatrixZ[2][2] * VertexBuffer.vertices[i + 2] + rotationMatrixZ[2][3] * VertexBuffer.vertices[i + 3];

            VertexBuffer.vertices[i] = v1;
            VertexBuffer.vertices[i + 1] = v2;
            VertexBuffer.vertices[i + 2] = v3;

            float n1 = rotationMatrixX[0][0] * VertexBuffer.vertices[i + 6] + rotationMatrixX[0][1] * VertexBuffer.vertices[i + 7] + rotationMatrixX[0][2] * VertexBuffer.vertices[i + 8] + rotationMatrixX[0][3] * VertexBuffer.vertices[i + 9];
            float n2 = rotationMatrixX[1][0] * VertexBuffer.vertices[i + 6] + rotationMatrixX[1][1] * VertexBuffer.vertices[i + 7] + rotationMatrixX[1][2] * VertexBuffer.vertices[i + 8] + rotationMatrixX[1][3] * VertexBuffer.vertices[i + 9];
            float n3 = rotationMatrixX[2][0] * VertexBuffer.vertices[i + 6] + rotationMatrixX[2][1] * VertexBuffer.vertices[i + 7] + rotationMatrixX[2][2] * VertexBuffer.vertices[i + 8] + rotationMatrixX[2][3] * VertexBuffer.vertices[i + 9];
    

            VertexBuffer.vertices[i + 6] = n1;
            VertexBuffer.vertices[i + 7] = n2;
            VertexBuffer.vertices[i + 8] = n3;

            n1 = rotationMatrixY[0][0] * VertexBuffer.vertices[i + 6] + rotationMatrixY[0][1] * VertexBuffer.vertices[i + 7] + rotationMatrixY[0][2] * VertexBuffer.vertices[i + 8] + rotationMatrixY[0][3] * VertexBuffer.vertices[i + 9];
            n2 = rotationMatrixY[1][0] * VertexBuffer.vertices[i + 6] + rotationMatrixY[1][1] * VertexBuffer.vertices[i + 7] + rotationMatrixY[1][2] * VertexBuffer.vertices[i + 8] + rotationMatrixY[1][3] * VertexBuffer.vertices[i + 9];
            n3 = rotationMatrixY[2][0] * VertexBuffer.vertices[i + 6] + rotationMatrixY[2][1] * VertexBuffer.vertices[i + 7] + rotationMatrixY[2][2] * VertexBuffer.vertices[i + 8] + rotationMatrixY[2][3] * VertexBuffer.vertices[i + 9];



            VertexBuffer.vertices[i + 6] = n1;
            VertexBuffer.vertices[i + 7] = n2;
            VertexBuffer.vertices[i + 8] = n3;

            n1 = rotationMatrixZ[0][0] * VertexBuffer.vertices[i + 6] + rotationMatrixZ[0][1] * VertexBuffer.vertices[i + 7] + rotationMatrixZ[0][2] * VertexBuffer.vertices[i + 8] + rotationMatrixZ[0][3] * VertexBuffer.vertices[i + 9];
            n2 = rotationMatrixZ[1][0] * VertexBuffer.vertices[i + 6] + rotationMatrixZ[1][1] * VertexBuffer.vertices[i + 7] + rotationMatrixZ[1][2] * VertexBuffer.vertices[i + 8] + rotationMatrixZ[1][3] * VertexBuffer.vertices[i + 9];
            n3 = rotationMatrixZ[2][0] * VertexBuffer.vertices[i + 6] + rotationMatrixZ[2][1] * VertexBuffer.vertices[i + 7] + rotationMatrixZ[2][2] * VertexBuffer.vertices[i + 8] + rotationMatrixZ[2][3] * VertexBuffer.vertices[i + 9];
    

            VertexBuffer.vertices[i + 6] = n1;
            VertexBuffer.vertices[i + 7] = n2;
            VertexBuffer.vertices[i + 8] = n3;

            // Camera space;

            // Position
            VertexBuffer.vertices[i] += position.x - camera.position.x;
            VertexBuffer.vertices[i + 1] += position.y - camera.position.y;
            VertexBuffer.vertices[i + 2] += position.z - camera.position.z;

            // Normal
            VertexBuffer.vertices[i + 6] += position.x - camera.position.x;
            VertexBuffer.vertices[i + 7] += position.y - camera.position.y;
            VertexBuffer.vertices[i + 8] += position.z - camera.position.z;

            // Light

            float lightX = -300 + VertexBuffer.vertices[i];
            float lightY = 600 + VertexBuffer.vertices[i + 1];
            float lightZ = VertexBuffer.vertices[i + 2];

            // Rotate vertices


            v1 = rotationMatrixY_camera[0][0] * VertexBuffer.vertices[i] + rotationMatrixY_camera[0][1] * VertexBuffer.vertices[i + 1] + rotationMatrixY_camera[0][2] * VertexBuffer.vertices[i + 2] + rotationMatrixY_camera[0][3] * VertexBuffer.vertices[i + 3];
            v2 = rotationMatrixY_camera[1][0] * VertexBuffer.vertices[i] + rotationMatrixY_camera[1][1] * VertexBuffer.vertices[i + 1] + rotationMatrixY_camera[1][2] * VertexBuffer.vertices[i + 2] + rotationMatrixY_camera[1][3] * VertexBuffer.vertices[i + 3];
            v3 = rotationMatrixY_camera[2][0] * VertexBuffer.vertices[i] + rotationMatrixY_camera[2][1] * VertexBuffer.vertices[i + 1] + rotationMatrixY_camera[2][2] * VertexBuffer.vertices[i + 2] + rotationMatrixY_camera[2][3] * VertexBuffer.vertices[i + 3];

            VertexBuffer.vertices[i] = v1;
            VertexBuffer.vertices[i + 1] = v2;
            VertexBuffer.vertices[i + 2] = v3;


            v1 = rotationMatrixX_camera[0][0] * VertexBuffer.vertices[i] + rotationMatrixX_camera[0][1] * VertexBuffer.vertices[i + 1] + rotationMatrixX_camera[0][2] * VertexBuffer.vertices[i + 2] + rotationMatrixX_camera[0][3] * VertexBuffer.vertices[i + 3];
            v2 = rotationMatrixX_camera[1][0] * VertexBuffer.vertices[i] + rotationMatrixX_camera[1][1] * VertexBuffer.vertices[i + 1] + rotationMatrixX_camera[1][2] * VertexBuffer.vertices[i + 2] + rotationMatrixX_camera[1][3] * VertexBuffer.vertices[i + 3];
            v3 = rotationMatrixX_camera[2][0] * VertexBuffer.vertices[i] + rotationMatrixX_camera[2][1] * VertexBuffer.vertices[i + 1] + rotationMatrixX_camera[2][2] * VertexBuffer.vertices[i + 2] + rotationMatrixX_camera[2][3] * VertexBuffer.vertices[i + 3];

            VertexBuffer.vertices[i] = v1;
            VertexBuffer.vertices[i + 1] = v2;
            VertexBuffer.vertices[i + 2] = v3;

            // Rotate normals

            n1 = rotationMatrixY_camera[0][0] * VertexBuffer.vertices[i + 6] + rotationMatrixY_camera[0][1] * VertexBuffer.vertices[i + 7] + rotationMatrixY_camera[0][2] * VertexBuffer.vertices[i + 8] + rotationMatrixY_camera[0][3] * VertexBuffer.vertices[i + 9];
            n2 = rotationMatrixY_camera[1][0] * VertexBuffer.vertices[i + 6] + rotationMatrixY_camera[1][1] * VertexBuffer.vertices[i + 7] + rotationMatrixY_camera[1][2] * VertexBuffer.vertices[i + 8] + rotationMatrixY_camera[1][3] * VertexBuffer.vertices[i + 9];
            n3 = rotationMatrixY_camera[2][0] * VertexBuffer.vertices[i + 6] + rotationMatrixY_camera[2][1] * VertexBuffer.vertices[i + 7] + rotationMatrixY_camera[2][2] * VertexBuffer.vertices[i + 8] + rotationMatrixY_camera[2][3] * VertexBuffer.vertices[i + 9];

            VertexBuffer.vertices[i + 6] = n1;
            VertexBuffer.vertices[i + 7] = n2;
            VertexBuffer.vertices[i + 8] = n3;

            n1 = rotationMatrixX_camera[0][0] * VertexBuffer.vertices[i + 6] + rotationMatrixX_camera[0][1] * VertexBuffer.vertices[i + 7] + rotationMatrixX_camera[0][2] * VertexBuffer.vertices[i + 8] + rotationMatrixX_camera[0][3] * VertexBuffer.vertices[i + 9];
            n2 = rotationMatrixX_camera[1][0] * VertexBuffer.vertices[i + 6] + rotationMatrixX_camera[1][1] * VertexBuffer.vertices[i + 7] + rotationMatrixX_camera[1][2] * VertexBuffer.vertices[i + 8] + rotationMatrixX_camera[1][3] * VertexBuffer.vertices[i + 9];
            n3 = rotationMatrixX_camera[2][0] * VertexBuffer.vertices[i + 6] + rotationMatrixX_camera[2][1] * VertexBuffer.vertices[i + 7] + rotationMatrixX_camera[2][2] * VertexBuffer.vertices[i + 8] + rotationMatrixX_camera[2][3] * VertexBuffer.vertices[i + 9];
    

            VertexBuffer.vertices[i + 6] = n1 - VertexBuffer.vertices[i];
            VertexBuffer.vertices[i + 7] = n2 - VertexBuffer.vertices[i + 1];
            VertexBuffer.vertices[i + 8] = n3 - VertexBuffer.vertices[i + 2];
            VertexBuffer.vertices[i + 9] = 1;

            float dot = VertexBuffer.vertices[i] * VertexBuffer.vertices[i + 6] + 
                        VertexBuffer.vertices[i + 1] * VertexBuffer.vertices[i + 7] +
                        VertexBuffer.vertices[i + 2] * VertexBuffer.vertices[i + 8];

            if(dot <= 0) {

                float[][] projmatrix = camera.getProjectionMatrix().at;

                v1 = projmatrix[0][0] * VertexBuffer.vertices[i] + projmatrix[0][1] * VertexBuffer.vertices[i + 1] + projmatrix[0][2] * VertexBuffer.vertices[i + 2] + projmatrix[0][3] * VertexBuffer.vertices[i + 3];
                v2 = projmatrix[1][0] * VertexBuffer.vertices[i] + projmatrix[1][1] * VertexBuffer.vertices[i + 1] + projmatrix[1][2] * VertexBuffer.vertices[i + 2] + projmatrix[1][3] * VertexBuffer.vertices[i + 3];
                v3 = projmatrix[2][0] * VertexBuffer.vertices[i] + projmatrix[2][1] * VertexBuffer.vertices[i + 1] + projmatrix[2][2] * VertexBuffer.vertices[i + 2] + projmatrix[2][3] * VertexBuffer.vertices[i + 3];
                float v4 = projmatrix[3][0] * VertexBuffer.vertices[i] + projmatrix[3][1] * VertexBuffer.vertices[i + 1] + projmatrix[3][2] * VertexBuffer.vertices[i + 2] + projmatrix[3][3] * VertexBuffer.vertices[i + 3];


                v1 = Math.max(v1, Math.min(v1, -v4));
                v2 = Math.max(v2, Math.min(v2, -v4));
                v3 = Math.max(v3, Math.min(v3, -v4));

                VertexBuffer.vertices[i] = (v1 / v4) * Screen.height;
                VertexBuffer.vertices[i + 1] = (v2 / v4) * Screen.width;
                VertexBuffer.vertices[i + 2] = v3 / v4;

                float len = lightX * lightX + lightY * lightY + lightZ * lightZ;
                len = (float)Math.sqrt(len);

                lightX /= len;
                lightY /= len;
                lightZ /= len;

                float lightDot = lightX * model.normals.at[(int) model.faceNormals[faceIndex] - 1][0] + 
                            lightY * model.normals.at[(int) model.faceNormals[faceIndex] - 1][1] +
                            lightZ * model.normals.at[(int) model.faceNormals[faceIndex] - 1][2];


                lightDot = (lightDot + 1) / 2;

                points[v].position.x = (int)VertexBuffer.vertices[i] + Screen.width / 2;
                points[v].position.y = (int)VertexBuffer.vertices[i + 1] + Screen.height / 2;
                
                points[v].uv[0] = VertexBuffer.vertices[i + 4];
                points[v].uv[1] = VertexBuffer.vertices[i + 5];

                points[v].Color.r = (255 * lightDot);
                points[v].Color.g = (255 * lightDot);
                points[v].Color.b = (255 * lightDot);

                points[v].z = VertexBuffer.vertices[i + 2];
            } else {
                break;
            }
            if(v == 2) {
                rasterizer.fillTriangle(points);
            }
            }
        }
    }

    public void update() {

}}