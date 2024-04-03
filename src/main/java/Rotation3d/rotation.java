package Rotation3d;

import com.jogamp.opengl.GL2;

public class rotation {

    public static void drawTriangle(){
        GL2 gl = eventlistener.gl;
        gl.glColor3d(0, 0, 0);
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3f(1, 0, 0);
        gl.glVertex3f(-1, 0, 0);
        gl.glVertex3f(0, 1, 0);
        gl.glVertex3f(0, -1, 0);
        gl.glVertex3f(0, 0, -1);
        gl.glVertex3f(0, 0, 1);
        gl.glEnd();
        // Provide values for x1, y1, z1, x2, y2, z2, x3, y3, z3, and O here
        rotate(new int[]{3, 4, 5}, new int[]{5, 6, 7}, new int[]{8, 9, 10}, 0);
    }

    public static void rotate(int[] a, int[] b, int[] c, float O) {
        GL2 gl = eventlistener.gl;
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex3f((float)a[0] / 100, (float)a[1] / 100, (float)a[2] / 100);
        gl.glVertex3f((float)b[0] / 100, (float)b[1] / 100, (float)b[2] / 100);
        gl.glVertex3f((float)c[0] / 100, (float)c[1] / 100, (float)c[2] / 100);
        gl.glEnd();
    }

    public static void rotate() {
        // Provide default values for rotation if needed
        int[] a = {1, 0, 0};
        int[] b = {0, 1, 0};
        int[] c = {0, 0, 1};
        float O = 0.0f; // Provide default rotation angle if needed
        rotate(a, b, c, O);
    }

    public static void matrixMultiply(int[][] A, int[][] B, int[][] C, int[][] D, float O) {
        int[][] resultD = new int[4][1];
        int[][] resultE = new int[4][1];
        int[][] resultF = new int[4][1];

        // Matrix multiplication logic
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 1; j++) {
                resultD[i][j] = 0;
                resultE[i][j] = 0;
                resultF[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                    resultD[i][j] += A[i][k] * B[k][j];
                    resultE[i][j] += A[i][k] * C[k][j];
                    resultF[i][j] += A[i][k] * D[k][j];
                }
            }
        }

        GL2 gl = eventlistener.gl;
        gl.glColor3f(0.0f, 0.f, 1.0f);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex3f((float)resultD[0][0] / 100, (float)resultD[1][0] / 100, (float)resultD[2][0] / 100);
        gl.glVertex3f((float)resultE[0][0] / 100, (float)resultE[1][0] / 100, (float)resultE[2][0] / 100);
        gl.glVertex3f((float)resultF[0][0] / 100, (float)resultF[1][0] / 100, (float)resultF[2][0] / 100);
        gl.glEnd();
    }

    private static int[] sin(float o) {
        // Implement sin calculation
        return null;
    }

    private static int[] cos(float o) {
        // Implement cos calculation
        return null;
    }
}
