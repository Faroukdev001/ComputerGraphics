package OUTOFBOUND.TwoDShearing.TwoDTranslation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class TranslationExample {

    // Function to translate points by tx along x-axis and ty along y-axis
    public static List<Point2D.Double> translate(List<Point2D.Double> points, double tx, double ty) {
        List<Point2D.Double> translatedPoints = new ArrayList<>();
        for (Point2D.Double point : points) {
            double x = point.getX() + tx;
            double y = point.getY() + ty;
            translatedPoints.add(new Point2D.Double(x, y));
        }
        return translatedPoints;
    }

    public static void main(String[] args) {
        // Define points forming a triangle
        List<Point2D.Double> points = new ArrayList<>();
        points.add(new Point2D.Double(10, 20));
        points.add(new Point2D.Double(10, 10));
        points.add(new Point2D.Double(20, 10));
        points.add(new Point2D.Double(10, 20));

        // OUTOFBOUND.TwoDShearing.Translation factors
        double tx = 5;
        double ty = 10;

        // Perform translation
        List<Point2D.Double> translatedPoints = translate(points, tx, ty);

        // Create dataset
        XYSeries originalSeries = new XYSeries("Original");
        for (Point2D.Double point : points) {
            originalSeries.add(point.getX(), point.getY());
        }
        // Closing the triangle by connecting the last point to the first
        originalSeries.add(points.get(0).getX(), points.get(0).getY());

        XYSeries translatedSeries = new XYSeries("Translated");
        for (Point2D.Double point : translatedPoints) {
            translatedSeries.add(point.getX(), point.getY());
        }

        // Closing the translated triangle by connecting the last translated point to the first translated point
        translatedSeries.add(translatedPoints.get(0).getX(), translatedPoints.get(0).getY());

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(originalSeries);
        dataset.addSeries(translatedSeries);

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "2D OUTOFBOUND.TwoDShearing.Translation", "X", "Y", dataset, PlotOrientation.VERTICAL, true, true, false);

        // Display chart in a frame
        JFrame frame = new JFrame("2D OUTOFBOUND.TwoDShearing.Translation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ChartPanel(chart));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
