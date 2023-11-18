package shaders;

public class VertexBuffer {

    public float[] vertices; // works in groups of 10 elements. Position (4), UV (2), Normal (4)
    
    public VertexBuffer(int length) {
        vertices = new float[length * 10];
    }

}
