package Reflection3d;

import Reflection3d.eventlistener;
import com.jogamp.opengl.GL2;

public class  reflection{


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
        reflect(2, 3, 4, 5, 6, 7, 8, 9, 10);
        matrixMultiply(new int[]{2}, new int[]{3}, new int[]{4}, new int[]{5}, new int[]{6}, new int[]{7}, new int[]{8}, new int[]{9}, 10, 0);
    }



    public static void reflect(int a, int b, int c, int d, int e, int f, int g, int h, int i) {
        GL2 gl = eventlistener.gl;
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex3f((float)a / 100, (float)b / 100, (float)c / 100);
        gl.glVertex3f((float)d / 100, (float)e / 100, (float)f / 100);
        gl.glVertex3f((float)g / 100, (float)h / 100, (float)i / 100);
        gl.glEnd();







    }

    //Matrix multiplication
    public static void matrixMultiply(int[] x1, int[] y1, int[] z1, int[] x2, int[] y2, int[] z2, int[] x3, int[] y3, int z3, float O)
    {
        int [][]A = {new int[]{1}, new int[]{0}, new int[]{0}, new int[]{0}, new int[]{0}, new int[]{-1},
                new int[]{0}, new int[]{0}, new int[]{0}, new int[]{0}, new int[]{1}, new int[]{0}, new int[]{0}, new int[]{0}, new int[]{0}, new int[]{1}};
        int [][]B = { x1, y1, z1, new int[]{1}};
        int [][]C = { x2, y2, z2, new int[]{1}};
        int [][]D = { x3, y3, new int[]{z3}, new int[]{1}};

        GL2 gl = eventlistener.gl;
        int i, j, k;

        for (i = 0; i < 4; i++)
        {
            for (j = 0; j < 1; j++)
            {
                D[i][j] = 0;
                D[i][j] = 0;
                C[i][j] = 0;
                for (k = 0; k < 4; k++)
                {
                    B[i][j] += A[i][k] * B[k][j];
                    D[i][j] += A[i][k] * C[k][j];
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }


        gl.glColor3f(0.0f, 0.f, 1.0f);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex3f((float)D[0][0] / 100, (float)D[1][0] / 100, (float)D[2][0] / 100);
        gl.glVertex3f((float)D[0][0] / 100, (float)D[1][0] / 100, (float)D[2][0] / 100);
        gl.glVertex3f((float)C[0][0] / 100, (float)C[1][0] / 100, (float)C[2][0] / 100);

        gl.glEnd();
    }



    private static int[] sin(float o) {
        // TODO Auto-generated method stub
        return new int[]{0};
    }



    private static int[] cos(float o) {
        // TODO Auto-generated method stub
        return null;
    }



}
