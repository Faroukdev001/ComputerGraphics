package OUTOFBOUND.TwoDShearing;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShearingExampleX extends JPanel {

    private Point2D[] shear(Point2D[] points, double shx) {
        AffineTransform shearingTransformX = new AffineTransform(1, shx, 0, 1, 0, 0);

        Point2D[] shearedPointsX = new Point2D[points.length];

        for (int i = 0; i < points.length; i++) {
            shearedPointsX[i] = new Point2D.Double();

            shearedPointsX[i] = shearingTransformX.transform(points[i], null);
        }

        return shearedPointsX;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Define some points
        double size = 50; // Scale factor for triangle size
        Point2D[] points = {new Point2D.Double(0, 0),
                new Point2D.Double(0, size),
                new Point2D.Double(size, size),
                new Point2D.Double(size, 0),
                new Point2D.Double(0, 0)};

        // Shearing factor along x-axis
        double shx = 2;

        // Perform shearing
        Point2D[] shearedPointsX = shear(points, shx);

        // Plot original and sheared points along x-axis
        Path2D originalPathX = new Path2D.Double();
        originalPathX.moveTo(points[0].getX(), points[0].getY() ); // Scale by 100
        for (int i = 1; i < points.length; i++) {
            originalPathX.lineTo(points[i].getX(), points[i].getY()); // Scale by 100
        }

        Path2D shearedPathX = new Path2D.Double();
        shearedPathX.moveTo(shearedPointsX[0].getX(), shearedPointsX[0].getY()); // Scale by 100
        for (int i = 1; i < shearedPointsX.length; i++) {
            shearedPathX.lineTo(shearedPointsX[i].getX(), shearedPointsX[i].getY()); // Scale by 100
        }

        // Draw original and sheared points along x-axis
        g2d.setColor(Color.BLUE);
        g2d.draw(originalPathX);
        g2d.setColor(Color.RED);
        g2d.draw(shearedPathX);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("2D Shearing along X-axis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ShearingExampleX());
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
