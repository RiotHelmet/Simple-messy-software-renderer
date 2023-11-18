package math;

public class Vector3 {

    public float x, y, z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public float[] getArray() {
        return new float[]{x,y,z};
    }

    public void plus(Vector3 B) {
        x += B.x;
        y += B.y;
        z += B.z;
    }

    public Vector3 mult(float B) {
        return new Vector3(x * B, y * B, z * B);
    }

    public float at(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
        }
        return 0;
    } 

}