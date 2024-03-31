package TwoDScaling;

import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ScalingExample extends JPanel {

    private Point2D[] scale(Point2D[] points, double sx, double sy) {
        AffineTransform scalingTransform = AffineTransform.getScaleInstance(sx, sy);
        Point2D[] scaledPoints = new Point2D[points.length];

        for (int i = 0; i < points.length; i++) {
            scaledPoints[i] = new Point2D.Double();
            scalingTransform.transform(points[i], scaledPoints[i]);
        }
        // Print the coordinates of scaled points
        System.out.println("Scaled Points:");
        for (Point2D p : scaledPoints) {
            System.out.println("(" + p.getX() + ", " + p.getY() + ")");
        }
        return scaledPoints;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Define some points
        Point2D[] points = {
                new Point2D.Double(100, 200),
                new Point2D.Double(100, 100),
                new Point2D.Double(200, 100),
                new Point2D.Double(100, 200)};

        // Scaling factors
        double sx = 2;
        double sy = 1.5;

        // Perform scaling
        Point2D[] scaledPoints = scale(points, sx, sy);

        // Plot original points
        Path2D originalPath = new Path2D.Double();
        originalPath.moveTo(points[0].getX(), points[0].getY());
        for (int i = 1; i < points.length; i++) {
            originalPath.lineTo(points[i].getX(), points[i].getY());
        }

        // Plot scaled points
        Path2D scaledPath = new Path2D.Double();
        scaledPath.moveTo(scaledPoints[0].getX(), scaledPoints[0].getY());
        for (int i = 1; i < scaledPoints.length; i++) {
            scaledPath.lineTo(scaledPoints[i].getX(), scaledPoints[i].getY());
        }

        // Draw original and scaled points
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(originalPath);
        g2d.setColor(Color.RED);
        g2d.draw(scaledPath);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("2D Scaling");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ScalingExample());
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
