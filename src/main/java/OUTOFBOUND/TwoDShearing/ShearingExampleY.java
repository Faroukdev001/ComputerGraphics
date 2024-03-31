package OUTOFBOUND.TwoDShearing;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShearingExampleY extends JPanel {

    private Point2D[] shear(Point2D[] points, double shy) {
        AffineTransform shearingTransformY = new AffineTransform(1, shy, 0, 0, 1, 0);

        Point2D[] shearedPointsY = new Point2D[points.length];

        for (int i = 0; i < points.length; i++) {
            shearedPointsY[i] = new Point2D.Double();

            shearedPointsY[i] = shearingTransformY.transform(points[i], null);
        }

        return shearedPointsY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Define some points
        double size = 10; // Scale factor for triangle size
        Point2D[] points = {new Point2D.Double(0, 0),
                new Point2D.Double(0, size),
                new Point2D.Double(size, size),
                new Point2D.Double(size, 0),
                new Point2D.Double(0, 0)};

        // Shearing factor along y-axis
        double shy = 3;

        // Perform shearing
        Point2D[] shearedPointsY = shear(points, shy);

        // Plot original and sheared points along y-axis
        Path2D originalPathY = new Path2D.Double();
        originalPathY.moveTo(points[0].getX(), points[0].getY()); // Scale by 100
        for (int i = 1; i < points.length; i++) {
            originalPathY.lineTo(points[i].getX(), points[i].getY()); // Scale by 100
        }

        Path2D shearedPathY = new Path2D.Double();
        shearedPathY.moveTo(shearedPointsY[0].getX(), shearedPointsY[0].getY()); // Scale by 100
        for (int i = 1; i < shearedPointsY.length; i++) {
            shearedPathY.lineTo(shearedPointsY[i].getX(), shearedPointsY[i].getY()); // Scale by 100
        }

        // Draw original and sheared points along y-axis
        g2d.setColor(Color.BLUE);
        g2d.draw(originalPathY);
        g2d.setColor(Color.RED);
        g2d.draw(shearedPathY);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("2D Shearing along Y-axis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ShearingExampleY());
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

