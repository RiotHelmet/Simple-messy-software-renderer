package math;

public class Matrix {
    
        public float[][] at;
        
        public int rows, cols;

        public Matrix(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
            at = new float[rows][cols];

        }
    
        public void print() {
            for (int i = 0; i < rows; i++) {
                System.out.println("");
                for (int j = 0; j < cols; j++) {
                    System.out.print(at[i][j] + ", ");
                }
            }
            System.out.println("");
        }
    
        public static Matrix multiplyMatrix(Matrix A, Matrix B) throws IllegalArgumentException{

            if(A.cols != B.rows) {
                throw new IllegalArgumentException("Incompatible matrices");
            }

            Matrix temp = new Matrix(A.rows, B.cols);

            for (int row = 0; row < temp.rows; row++) {
                for (int col = 0; col < temp.cols; col++) {
                    for (int k = 0; k < A.cols; k++) {
                        temp.at[row][col] += A.at[row][k] * B.at[k][col];
                    }
                }
            }

            return temp;
        }

        public static Matrix rotationMatrixX(double deg) {

            Matrix rotationMatrix = new Matrix(4, 4);
            rotationMatrix.at = new float[][]{
                {1,0,0,0},
                {0,(float) Math.cos(deg),(float) Math.sin(deg),0},
                {0,(float) -Math.sin(deg),(float) Math.cos(deg),0},
                {0,0,0,1}
            };
    
            return rotationMatrix;
        }
    
        public static Matrix rotationMatrixY(double deg) {
    
            Matrix rotationMatrix = new Matrix(4, 4);
            rotationMatrix.at = new float[][]{
                {(float)Math.cos(deg),0,(float)-Math.sin(deg),0},
                {0,1,0,0},
                {(float)Math.sin(deg),0,(float) Math.cos(deg),0},
                {0,0,0,1}
            };
    
            return rotationMatrix;
        }
    
        public static Matrix rotationMatrixZ(double deg) {
    
            Matrix rotationMatrix = new Matrix(4, 4);
            rotationMatrix.at = new float[][]{
                {(float) Math.cos(deg),(float) Math.sin(deg),0,0},
                {(float) -Math.sin(deg),(float) Math.cos(deg),0,0},
                {0,0,1,0},
                {0,0,0,1}
            };
    
            return rotationMatrix;
        }

        public void translate(Vector3 translateVec) {
            
        }

        public static Vector3 multiplyMatrix(Matrix A, Vector3 B) {
            Vector3 temp = new Vector3(0, 0, 0);

            temp.x = B.x * A.at[0][0] + B.y * A.at[0][1] + B.z * A.at[0][2];
            temp.y = B.x * A.at[1][0] + B.y * A.at[1][1] + B.z * A.at[1][2];
            temp.z = B.x * A.at[2][0] + B.y * A.at[2][1] + B.z * A.at[2][2];

            return temp;
        }

        public static float[] multiplyMatrix(Matrix A, float[] B) {
            float[] temp = new float[B.length];

            for (int row = 0; row < temp.length; row++) {
                    for (int k = 0; k < A.cols; k++) {
                        temp[row] += A.at[row][k] * B[k];
                    }
            }
            return temp;
        }

}
