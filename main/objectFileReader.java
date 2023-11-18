package main;
import java.io.*;
import math.Matrix;;


public class objectFileReader{

    public static Object readOBJ(String filePath) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line = reader.readLine();

        int vertexAmount = 0;
        int normalAmount = 0;
        int faceAmount = 0;
        int uvAmount = 0;

        int paddingV = 0;
        int paddingVt = 0;
        int paddingVn = 0;
        while (line != null) {

            String type = line.split(" ")[0];

            switch (type) {
                case "v":
                    paddingV = line.split(" ").length - 4;
                    vertexAmount++;
                    break;
                case "f":
                    String[] faceData = line.split(" ");
                    faceAmount += (faceData.length - 3);
                    break;
                case "vn":
                    paddingVn = line.split(" ").length - 4;
                    normalAmount++;
                    break;
                case "vt":
                    paddingVt = line.split(" ").length - 4;
                    uvAmount++;
                    break;

                default:
                    break;
            }

            line = reader.readLine();
        }

        Matrix vertices = new Matrix(vertexAmount, 3);
        Matrix normals = new Matrix(normalAmount, 4);
        int[][] faces = new int[faceAmount][3];
        int[][] faceUVs = new int[faceAmount][3];
        int[] faceNormals = new int[faceAmount];
        Matrix uv = new Matrix(uvAmount, 2);

        reader.close();

        vertexAmount = 0;
        normalAmount = 0;
        faceAmount = 0;
        uvAmount = 0;

        reader = new BufferedReader(new FileReader(filePath));

        line = reader.readLine();

        while (line != null) {

            String type = line.split(" ")[0];

            switch (type) {
                case "v":
                    String[] vertexData = line.split(" ");
                    
                    
                    vertices.at[vertexAmount][0] = Float.parseFloat(vertexData[1 + paddingV]);
                    vertices.at[vertexAmount][1] = Float.parseFloat(vertexData[2 + paddingV]);
                    vertices.at[vertexAmount][2] = Float.parseFloat(vertexData[3 + paddingV]);

                    vertexAmount++;
                    break;
                case "f":
                
                    String[] faceData = line.split(" ");
                    
                    

                    if(faceData.length == 5) {
                        String[] splitData = faceData[1].split("/");

                        faces[faceAmount][0] = Integer.parseInt(splitData[0]);
                        faceUVs[faceAmount][0] = (splitData[1].length() > 0) ? Integer.parseInt(splitData[1]) : 1;
                        faceNormals[faceAmount] = Integer.parseInt(splitData[2]);
                        faceNormals[faceAmount + 1] = Integer.parseInt(splitData[2]);

                        faces[faceAmount + 1][2] = Integer.parseInt(splitData[0]);
                        faceUVs[faceAmount + 1][2] = (splitData[1].length() > 0) ? Integer.parseInt(splitData[1]) : 1;

                        splitData = faceData[2].split("/");
                        faces[faceAmount][1] = Integer.parseInt(splitData[0]);
                        faceUVs[faceAmount][1] = (splitData[1].length() > 0) ? Integer.parseInt(splitData[1]) : 1;


                        splitData = faceData[3].split("/");
                        faces[faceAmount][2] = Integer.parseInt(splitData[0]);
                        faceUVs[faceAmount][2] = (splitData[1].length() > 0) ? Integer.parseInt(splitData[1]) : 1;

                        faces[faceAmount + 1][0] = Integer.parseInt(splitData[0]);
                        faceUVs[faceAmount + 1][0] = (splitData[1].length() > 0) ? Integer.parseInt(splitData[1]) : 1;

                        splitData = faceData[4].split("/");
                        faces[faceAmount + 1][1] = Integer.parseInt(splitData[0]);
                        faceUVs[faceAmount + 1][1] = (splitData[1].length() > 0) ? Integer.parseInt(splitData[1]) : 1;

                        faceAmount += 2;
                        break;
                    }

                    for (int i = 1; i < faceData.length; i++) {
                        String[] splitData = faceData[i].split("/");

                        faces[faceAmount][i - 1] = Integer.parseInt(splitData[0]);
                        faceUVs[faceAmount][ i - 1] = (splitData[1].length() > 0) ? Integer.parseInt(splitData[1]) : 1;
                        faceNormals[faceAmount] = Integer.parseInt(splitData[2]);
                    }

                    faceAmount++;
                    break;
                case "vn":

                    String[] normalData = line.split(" ");
                    
                    normals.at[normalAmount][0] = Float.parseFloat(normalData[1 + paddingVn]);
                    normals.at[normalAmount][1] = Float.parseFloat(normalData[2 + paddingVn]);
                    normals.at[normalAmount][2] = Float.parseFloat(normalData[3 + paddingVn]);
                    
                    normalAmount++;
                    break;
                case "vt":

                    String[] uvData = line.split(" ");
                    
                    uv.at[uvAmount][0] = Float.parseFloat(uvData[1 + paddingVt]);
                    uv.at[uvAmount][1] = Float.parseFloat(uvData[2 + paddingVt]);
                    
                    uvAmount++;
                    break;

                default:
                    break;
            }

            line = reader.readLine();
        }

        Object returnObject = new Object();

        returnObject.model.faceNormals = faceNormals;
        returnObject.model.vertices = vertices;
        returnObject.model.faces = faces;
        returnObject.model.normals = normals;
        returnObject.model.faceUV = faceUVs;
        returnObject.model.uv = uv;

        reader.close();

        return returnObject;
    }
}
