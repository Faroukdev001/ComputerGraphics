package TwoDRotation;

//import TwoDTranslation.TranslationExample1;

import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

class Rotation extends JPanel {

    // Function to rotate points by degrees
    public static List<Point2D.Double> rotate(List<Point2D.Double> points, double degrees) {

        // creates an array of the rotated points
        List<Point2D.Double> rotatedPoints = new ArrayList<>();

        /*
        *   [ x']   [  m00  m01  m02  ] [ x ]   [ m00x + m01y + m02 ]
        *   [ y'] = [  m10  m11  m12  ] [ y ] = [ m10x + m11y + m12 ]
        *   [ 1 ]   [   0    0    1   ] [ 1 ]   [         1         ]
        *
        *  In the provided code, the rotation is achieved using the AffineTransform.rotate() method,
        *  which internally handles the trigonometric calculations.
        *  You don't see explicit calculations involving sine and cosine because AffineTransform abstracts
        *  these calculations away for you.
        * */

        AffineTransform transform = new AffineTransform();

        // rotate about the points
        transform.rotate(Math.toRadians(degrees), points.get(0).getX(), points.get(0).getY());

        for (Point2D.Double point : points) {
            Point2D.Double rotatedPoint = new Point2D.Double();
            transform.transform(point, rotatedPoint);
            rotatedPoints.add(rotatedPoint);
        }
        return rotatedPoints;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Define some points forming a triangle
        List<Point2D.Double> points = new ArrayList<>();
        points.add(new Point2D.Double(100, 200));
        points.add(new Point2D.Double(50, 100));
        points.add(new Point2D.Double(200, 100));

        // Rotation angle in degrees
        double degrees = 45;

        // Perform rotation
        List<Point2D.Double> rotatedPoints = rotate(points, degrees);

        // Set the transparency level (alpha)
//        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

        // Plot original points (triangle)
        g2d.setColor(new Color(0, 0, 255, 127));
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        for (int i = 0; i < 3; i++) {
            xPoints[i] = (int) points.get(i).getX();
            yPoints[i] = (int) points.get(i).getY();
        }
        g2d.fillPolygon(xPoints, yPoints, 3);

        // Plot rotated points (triangle)
        g2d.setColor(new Color(255, 0, 0, 127));
        int[] xRotatedPoints = new int[3];
        int[] yRotatedPoints = new int[3];
        for (int i = 0; i < 3; i++) {
            xRotatedPoints[i] = (int) rotatedPoints.get(i).getX();
            yRotatedPoints[i] = (int) rotatedPoints.get(i).getY();
        }
        g2d.fillPolygon(xRotatedPoints, yRotatedPoints, 3);
    }

    public static void main(String[] args) {
        // Define some points forming a triangle
        List<Point2D.Double> points = new ArrayList<>();
        points.add(new Point2D.Double(100, 200));
        points.add(new Point2D.Double(50, 100));
        points.add(new Point2D.Double(200, 100));

        // Rotation angle in degrees
        double degrees = 45;

        // Perform rotation
        List<Point2D.Double> rotatedPoints = rotate(points, degrees);

        // Print rotated points
        System.out.println("Rotated Points:");
        for (Point2D.Double point : rotatedPoints) {
            System.out.println("(" + point.getX() + ", " + point.getY() + ")");
        }

        // Create and display the JFrame
        JFrame frame = new JFrame("2D Rotation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Rotation());
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
