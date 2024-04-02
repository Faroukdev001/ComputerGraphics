package TwoDScaling;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;

import javax.swing.*;
import java.awt.*;

public class ScalingExampleV2 {

    public static double[][] scale(double[][] points, double sx, double sy) {
        // Scaling matrix
        double[][] scalingMatrix =
                        {{sx, 0, 0},
                        {0, sy, 0},
                        {0, 0, 1}};

        // Append ones to the points for homogeneous coordinates
        double[][] homogeneousPoints = new double[points.length][points[0].length + 1];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[0].length; j++) {
                homogeneousPoints[i][j] = points[i][j];
            }
            homogeneousPoints[i][points[0].length] = 1; // Set last element to 1
        }

        // Apply scaling matrix
        RealMatrix scalingMatrixRM = MatrixUtils.createRealMatrix(scalingMatrix);
        RealMatrix pointsRM = MatrixUtils.createRealMatrix(homogeneousPoints);
        RealMatrix scaledPointsRM = scalingMatrixRM.multiply(pointsRM.transpose()).transpose();

        // Extract scaled points
        double[][] scaledPoints = new double[points.length][2];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < 2; j++) {
                scaledPoints[i][j] = scaledPointsRM.getEntry(i, j);
            }
        }
        return scaledPoints;
    }

    public static void main(String[] args) {
        // Define some points
        double[][] points = {{100, 200},
                {100, 100},
                {200, 100},
                {100, 200}};

        // Scaling factors
        double sx = 2;
        double sy = 1.5;

        // Perform scaling
        double[][] scaledPoints = scale(points, sx, sy);

        // Print scaled points
        System.out.println("Scaled Points:");
        for (double[] point : scaledPoints) {
            System.out.println("(" + point[0] + ", " + point[1] + ")");
        }

        // Plot original and scaled points
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.BLUE);
                for (int i = 0; i < points.length - 1; i++) {
                    g2d.drawLine((int) points[i][0], (int) points[i][1], (int) points[i + 1][0], (int) points[i + 1][1]);
                }
                g2d.drawLine((int) points[points.length - 1][0], (int) points[points.length - 1][1], (int) points[0][0], (int) points[0][1]);

                g2d.setColor(Color.RED);
                for (int i = 0; i < scaledPoints.length - 1; i++) {
                    g2d.drawLine((int) scaledPoints[i][0], (int) scaledPoints[i][1], (int) scaledPoints[i + 1][0], (int) scaledPoints[i + 1][1]);
                }
                g2d.drawLine((int) scaledPoints[scaledPoints.length - 1][0], (int) scaledPoints[scaledPoints.length - 1][1], (int) scaledPoints[0][0], (int) scaledPoints[0][1]);
            }
        });

        frame.setSize(500, 400);
        frame.setVisible(true);
    }
}

