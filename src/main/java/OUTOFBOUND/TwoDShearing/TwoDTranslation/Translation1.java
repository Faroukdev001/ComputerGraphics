package OUTOFBOUND.TwoDShearing.TwoDTranslation;

import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

class TranslationExample1 extends JPanel {

    // Function to translate points by tx along x-axis and ty along y-axis
    public static List<Point2D.Double> translate(List<Point2D.Double> points, double tx, double ty) {
        // Create translation matrix
        AffineTransform translationMatrix = new AffineTransform();
        translationMatrix.translate(tx, ty);

        // Create a list to store translated points
        List<Point2D.Double> translatedPoints = new ArrayList<>();

        // Apply translation to each point
        for (Point2D.Double point : points) {
            Point2D.Double translatedPoint = new Point2D.Double();
            translationMatrix.transform(point, translatedPoint);
            translatedPoints.add(translatedPoint);
        }

        return translatedPoints;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Define some points forming a triangle
        List<Point2D.Double> points = new ArrayList<>();
        points.add(new Point2D.Double(50, 200));
        points.add(new Point2D.Double(200, 50));
        points.add(new Point2D.Double(350, 200));

        // Translation factors
        double tx = 50;
        double ty = 100;

        // Perform translation
        List<Point2D.Double> translatedPoints = translate(points, tx, ty);

        // Plot original points (triangle)
        g2d.setColor(Color.BLUE);
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        for (int i = 0; i < 3; i++) {
            xPoints[i] = (int) points.get(i).getX();
            yPoints[i] = (int) points.get(i).getY();
        }
        g2d.fillPolygon(xPoints, yPoints, 3);

        // Plot translated points (triangle)
        g2d.setColor(Color.RED);
        int[] xTranslatedPoints = new int[3];
        int[] yTranslatedPoints = new int[3];
        for (int i = 0; i < 3; i++) {
            xTranslatedPoints[i] = (int) translatedPoints.get(i).getX();
            yTranslatedPoints[i] = (int) translatedPoints.get(i).getY();
        }
        g2d.fillPolygon(xTranslatedPoints, yTranslatedPoints, 3);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("2D Translation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TranslationExample1());
        frame.setSize(500, 500); // increased size
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
