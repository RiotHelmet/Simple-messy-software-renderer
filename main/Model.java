package main;

import math.Matrix;

public class Model {
    public static final int VERTICES_PER_FACE = 3;
    public static final int ELEMENTS_PER_VERTEX = 10;
    public static final int ELEMENTS_PER_FACE = 30;

    public Matrix vertices;
    public Matrix normals;
    public int[][] faces;
    public int[] faceNormals;
    public int[][] faceUV;
    public Matrix uv;

    public Model() {}

}
