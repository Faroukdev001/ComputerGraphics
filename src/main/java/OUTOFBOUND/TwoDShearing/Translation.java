package OUTOFBOUND.TwoDShearing;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.*;
import com.jogamp.opengl.awt.GLCanvas;

public class Translation extends JFrame implements GLEventListener {
    private static final long serialVersionUID = 1L;
    private final GLU glu = new GLU();

    private static final int WIDTH = 1362;
    private static final int HEIGHT = 750;

    private static final int CHOICE_TRANSLATION = 1;
    private static final int CHOICE_SCALING = 2;
    private static final int CHOICE_ROTATION = 3;
    private static final int CHOICE_REFLECTION = 4;

    private int choice;
    private int choiceRot;
    private int choiceRef;

    private float[][] ptsIni = {{80, 80, -100}, {180, 80, -100}, {180, 180, -100}, {80, 180, -100},
            {60, 60, 0}, {160, 60, 0}, {160, 160, 0}, {60, 160, 0}};

    private float[][] ptsFin = new float[8][3];
    private float refptX, refptY, refptZ;
    private float TransDistX, TransDistY, TransDistZ;
    private float ScaleX, ScaleY, ScaleZ;
    private float Alpha, Beta, Gamma, Theta;
    private float A, B, C;
    private float aa, bb, cc;
    private float x1, y11, z1, x2, y2, z2;

    private final float[][] theMatrix = new float[4][4];

    public Translation() {
        setTitle("Basic Transformations");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(this);
        getContentPane().add(canvas);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translation bt = new Translation();
            bt.setVisible(true);
        });
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glOrtho(-454.0, 454.0, -250.0, 250.0, -250.0, 250.0);
        gl.glEnable(GL.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        Axes(gl);
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        Draw(gl, ptsIni);

        matrixSetIdentity(theMatrix);

        switch (choice) {
            case CHOICE_TRANSLATION:
                Translate(TransDistX, TransDistY, TransDistZ);
                break;
            case CHOICE_SCALING:
                Scale(ScaleX, ScaleY, ScaleZ);
                break;
            case CHOICE_ROTATION:
                switch (choiceRot) {
                    case 1:
                        DrawRotLine(gl);
                        Translate(0, -B, -C);
                        RotateX(Alpha);
                        Translate(0, B, C);
                        break;
                    case 2:
                        DrawRotLine(gl);
                        Translate(-A, 0, -C);
                        RotateY(Beta);
                        Translate(A, 0, C);
                        break;
                    case 3:
                        DrawRotLine(gl);
                        Translate(-A, -B, 0);
                        RotateZ(Gamma);
                        Translate(A, B, 0);
                        break;
                    case 4:
                        DrawRotLine(gl);
                        float MOD = (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y11) * (y2 - y11) + (z2 - z1) * (z2 - z1));
                        aa = (x2 - x1) / MOD;
                        bb = (y2 - y11) / MOD;
                        cc = (z2 - z1) / MOD;
                        Translate(-x1, -y11, -z1);
                        float ThetaDash;
                        ThetaDash = (float) (1260 * Math.atan(bb / cc) / 22);
                        RotateX(ThetaDash);
                        RotateY((float) (1260 * Math.asin(-aa) / 22));
                        RotateZ(Theta);
                        RotateY((float) (1260 * Math.asin(aa) / 22));
                        RotateX(-ThetaDash);
                        Translate(x1, y11, z1);
                        break;
                }
                break;
            case CHOICE_REFLECTION:
                Reflect();
                break;
        }

        TransformPoints();
        Draw(gl, ptsFin);

        gl.glFlush();
    }



    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    private void Axes(GL2 gl) {
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2s((short) -1000, (short) 0);
        gl.glVertex2s((short) 1000, (short) 0);
        gl.glEnd();
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2s((short) 0, (short) -1000);
        gl.glVertex2s((short) 0, (short) 1000);
        gl.glEnd();
    }

    private void Draw(GL2 gl, float[][] a) {
        int i;

        gl.glColor3f(0.7f, 0.4f, 0.7f);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3f(a[0][0], a[0][1], a[0][2]);
        gl.glVertex3f(a[1][0], a[1][1], a[1][2]);
        gl.glVertex3f(a[2][0], a[2][1], a[2][2]);
        gl.glVertex3f(a[3][0], a[3][1], a[3][2]);
        gl.glEnd();

        i = 0;
        gl.glColor3f(0.8f, 0.6f, 0.5f);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3f(a[0 + i][0], a[0 + i][1], a[0 + i][2]);
        gl.glVertex3f(a[1 + i][0], a[1 + i][1], a[1 + i][2]);
        gl.glVertex3f(a[5 + i][0], a[5 + i][1], a[5 + i][2]);
        gl.glVertex3f(a[4 + i][0], a[4 + i][1], a[4 + i][2]);
        gl.glEnd();

        gl.glColor3f(0.2f, 0.4f, 0.7f);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3f(a[0][0], a[0][1], a[0][2]);
        gl.glVertex3f(a[3][0], a[3][1], a[3][2]);
        gl.glVertex3f(a[7][0], a[7][1], a[7][2]);
        gl.glVertex3f(a[4][0], a[4][1], a[4][2]);
        gl.glEnd();

        i = 1;
        gl.glColor3f(0.5f, 0.4f, 0.3f);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3f(a[0 + i][0], a[0 + i][1], a[0 + i][2]);
        gl.glVertex3f(a[1 + i][0], a[1 + i][1], a[1 + i][2]);
        gl.glVertex3f(a[5 + i][0], a[5 + i][1], a[5 + i][2]);
        gl.glVertex3f(a[4 + i][0], a[4 + i][1], a[4 + i][2]);
        gl.glEnd();

        i = 2;
        gl.glColor3f(0.5f, 0.6f, 0.2f);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3f(a[0 + i][0], a[0 + i][1], a[0 + i][2]);
        gl.glVertex3f(a[1 + i][0], a[1 + i][1], a[1 + i][2]);
        gl.glVertex3f(a[5 + i][0], a[5 + i][1], a[5 + i][2]);
        gl.glVertex3f(a[4 + i][0], a[4 + i][1], a[4 + i][2]);
        gl.glEnd();

        i = 4;
        gl.glColor3f(0.7f, 0.3f, 0.4f);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3f(a[0 + i][0], a[0 + i][1], a[0 + i][2]);
        gl.glVertex3f(a[1 + i][0], a[1 + i][1], a[1 + i][2]);
        gl.glVertex3f(a[2 + i][0], a[2 + i][1], a[2 + i][2]);
        gl.glVertex3f(a[3 + i][0], a[3 + i][1], a[3 + i][2]);
        gl.glEnd();
    }

    private void matrixSetIdentity(float[][] m) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                m[i][j] = (i == j) ? 1 : 0;
    }

    private void matrixPreMultiply(float[][] a, float[][] b) {
        float[][] tmp = new float[4][4];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                tmp[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j] + a[i][2] * b[2][j] + a[i][3] * b[3][j];
        for (int i = 0; i < 4; i++)
            System.arraycopy(tmp[i], 0, theMatrix[i], 0, 4);
    }

    private void Translate(float tx, float ty, float tz) {
        float[][] m = new float[4][4];
        matrixSetIdentity(m);
        m[0][3] = tx;
        m[1][3] = ty;
        m[2][3] = tz;
        matrixPreMultiply(m, theMatrix);
    }

    private void Scale(float sx, float sy, float sz) {
        float[][] m = new float[4][4];
        matrixSetIdentity(m);
        m[0][0] = sx;
        m[0][3] = (1 - sx) * refptX;
        m[1][1] = sy;
        m[1][3] = (1 - sy) * refptY;
        m[2][2] = sz;
        m[2][3] = (1 - sy) * refptZ;
        matrixPreMultiply(m, theMatrix);
    }

    private void RotateX(float angle) {
        float[][] m = new float[4][4];
        matrixSetIdentity(m);
        angle = angle * 22 / 1260;
        m[1][1] = (float) Math.cos(angle);
        m[1][2] = (float) -Math.sin(angle);
        m[2][1] = (float) Math.sin(angle);
        m[2][2] = (float) Math.cos(angle);
        matrixPreMultiply(m, theMatrix);
    }

    private void RotateY(float angle) {
        float[][] m = new float[4][4];
        matrixSetIdentity(m);
        angle = angle * 22 / 1260;
        m[0][0] = (float) Math.cos(angle);
        m[0][2] = (float) Math.sin(angle);
        m[2][0] = (float) -Math.sin(angle);
        m[2][2] = (float) Math.cos(angle);
        matrixPreMultiply(m, theMatrix);
    }

    private void RotateZ(float angle) {
        float[][] m = new float[4][4];
        matrixSetIdentity(m);
        angle = angle * 22 / 1260;
        m[0][0] = (float) Math.cos(angle);
        m[0][1] = (float) -Math.sin(angle);
        m[1][0] = (float) Math.sin(angle);
        m[1][1] = (float) Math.cos(angle);
        matrixPreMultiply(m, theMatrix);
    }

    private void Reflect() {
        float[][] m = new float[4][4];
        matrixSetIdentity(m);
        switch (choiceRef) {
            case 1:
                m[2][2] = -1;
                break;
            case 2:
                m[0][0] = -1;
                break;
            case 3:
                m[1][1] = -1;
                break;
        }
        matrixPreMultiply(m, theMatrix);
    }

    private void DrawRotLine(GL2 gl) {
        gl.glBegin(GL.GL_LINES);
        switch (choiceRot) {
            case 1:
                gl.glVertex3s((short) -1000, (short) B, (short) C);
                gl.glVertex3s((short) 1000, (short) B, (short) C);
                break;
            case 2:
                gl.glVertex3s((short) A, (short) -1000, (short) C);
                gl.glVertex3s((short) A, (short) 1000, (short) C);
                break;
            case 3:
                gl.glVertex3s((short) A, (short) B, (short) -1000);
                gl.glVertex3s((short) A, (short) B, (short) 1000);
                break;
            case 4:
                gl.glVertex3s((short) (x1 - aa * 500), (short) (y11 - bb * 500), (short) (z1 - cc * 500));
                gl.glVertex3s((short) (x2 + aa * 500), (short) (y2 + bb * 500), (short) (z2 + cc * 500));
                break;
        }
        gl.glEnd();
    }

    private void TransformPoints() {
        for (int k = 0; k < 8; k++) {
            for (int i = 0; i < 3; i++) {
                ptsFin[k][i] = theMatrix[i][0] * ptsIni[k][0] + theMatrix[i][1] * ptsIni[k][1]
                        + theMatrix[i][2] * ptsIni[k][2] + theMatrix[i][3];
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);

    private void getUserInput() {
        System.out.println("Enter your choice number:");
        System.out.println("1. Translation");
        System.out.println("2. Scaling");
        System.out.println("3. Rotation");
        System.out.println("4. Reflection");
        System.out.print("=> ");
        choice = scanner.nextInt();

        switch (choice) {
            case CHOICE_TRANSLATION:
                System.out.println("Enter Translation along X, Y & Z");
                System.out.print("=> ");
                TransDistX = scanner.nextFloat();
                TransDistY = scanner.nextFloat();
                TransDistZ = scanner.nextFloat();
                break;
            case CHOICE_SCALING:
                System.out.println("Enter Scaling ratios along X, Y & Z");
                System.out.print("=> ");
                ScaleX = scanner.nextFloat();
                ScaleY = scanner.nextFloat();
                ScaleZ = scanner.nextFloat();
                break;
            case CHOICE_ROTATION:
                System.out.println("Enter your choice for Rotation about axis:");
                System.out.println("1. parallel to X-axis. (y=B & z=C)");
                System.out.println("2. parallel to Y-axis. (x=A & z=C)");
                System.out.println("3. parallel to Z-axis. (x=A & y=B)");
                System.out.println("4. Arbitrary line passing through (x1,y1,z1) & (x2,y2,z2)");
                System.out.print("=> ");
                choiceRot = scanner.nextInt();
                switch (choiceRot) {
                    case 1:
                        System.out.print("Enter B & C: ");
                        B = scanner.nextFloat();
                        C = scanner.nextFloat();
                        System.out.print("Enter Rot. Angle Alpha: ");
                        Alpha = scanner.nextFloat();
                        break;
                    case 2:
                        System.out.print("Enter A & C: ");
                        A = scanner.nextFloat();
                        C = scanner.nextFloat();
                        System.out.print("Enter Rot. Angle Beta: ");
                        Beta = scanner.nextFloat();
                        break;
                    case 3:
                        System.out.print("Enter A & B: ");
                        A = scanner.nextFloat();
                        B = scanner.nextFloat();
                        System.out.print("Enter Rot. Angle Gamma: ");
                        Gamma = scanner.nextFloat();
                        break;
                    case 4:
                        System.out.println("Enter values of x1, y1 & z1:");
                        x1 = scanner.nextFloat();
                        y11 = scanner.nextFloat();
                        z1 = scanner.nextFloat();
                        System.out.println("Enter values of x2, y2 & z2:");
                        x2 = scanner.nextFloat();
                        y2 = scanner.nextFloat();
                        z2 = scanner.nextFloat();
                        System.out.print("Enter Rot. Angle Theta: ");
                        Theta = scanner.nextFloat();
                        break;
                }
                break;
            case CHOICE_REFLECTION:
                System.out.println("Enter your choice for reflection about plane:");
                System.out.println("1. X-Y");
                System.out.println("2. Y-Z");
                System.out.println("3. X-Z");
                System.out.print("=> ");
                choiceRef = scanner.nextInt();
                break;
            default:
                System.out.println("Please enter a valid choice!!!");
                break;
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}
