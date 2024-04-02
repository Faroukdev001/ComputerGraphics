package OUTOFBOUND.TwoDShearing;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class ShearTransformation {

    public static RealMatrix shear(double[][] points, double shx, double shy) {
        double[][] shearingMatrixX = {{1, shx, 0}, {0, 1, 0}, {0, 0, 1}};
        double[][] shearingMatrixY = {{1, 0, 0}, {shy, 1, 0}, {0, 0, 1}};

        // Append ones to the points for homogeneous coordinates
        double[][] pointsHomogeneous = new double[points.length][points[0].length + 1];
        for (int i = 0; i < points.length; i++) {
            pointsHomogeneous[i] = new double[]{points[i][0], points[i][1], 1.0};
        }

        // Apply shearing matrix along x-axis
        RealMatrix shearedPointsX = new Array2DRowRealMatrix(shearingMatrixX).multiply(new Array2DRowRealMatrix(pointsHomogeneous).transpose()).transpose();

        // Apply shearing matrix along y-axis
        RealMatrix shearedPointsY = new Array2DRowRealMatrix(shearingMatrixY).multiply(new Array2DRowRealMatrix(pointsHomogeneous).transpose()).transpose();

        return shearedPointsX.getSubMatrix(0, shearedPointsX.getRowDimension() - 1, 0, 1); // Return only the transformed points (excluding the 1 added for homogeneous coordinates)
    }

    public static void main(String[] args) {
        double[][] points = {{0, 0}, {0, 1}, {1, 1}, {1, 0}};
        double shx = 2; // Shear along x-axis
        double shy = 3; // Shear along y-axis

        // Perform shearing along x-axis
        RealMatrix shearedPointsX = shear(points, shx, 0);

        // Perform shearing along y-axis
        RealMatrix shearedPointsY = shear(points, 0, shy);

        // Create series for original points, sheared points along x-axis, and sheared points along y-axis
        XYSeries originalSeries = new XYSeries("Original");
        XYSeries shearedSeriesX = new XYSeries("Sheared along x-axis");
        XYSeries shearedSeriesY = new XYSeries("Sheared along y-axis");

        for (double[] point : points) {
            originalSeries.add(point[0], point[1]);
        }

        for (int i = 0; i < shearedPointsX.getRowDimension(); i++) {
            double x = shearedPointsX.getEntry(i, 0);
            double y = shearedPointsX.getEntry(i, 1);
            shearedSeriesX.add(x, y);
        }

        for (int i = 0; i < shearedPointsY.getRowDimension(); i++) {
            double x = shearedPointsY.getEntry(i, 0);
            double y = shearedPointsY.getEntry(i, 1);
            shearedSeriesY.add(x, y);
        }

        // Create dataset and add series to it
        XYSeriesCollection datasetX = new XYSeriesCollection();
        datasetX.addSeries(originalSeries);
        datasetX.addSeries(shearedSeriesX);

        XYSeriesCollection datasetY = new XYSeriesCollection();
        datasetY.addSeries(originalSeries);
        datasetY.addSeries(shearedSeriesY);

        // Create chart for sheared points along x-axis
        JFreeChart chartX = ChartFactory.createXYLineChart(
                "Shearing Transformation along x-axis", // Chart title
                "X", // X-axis label
                "Y", // Y-axis label
                datasetX, // Dataset
                PlotOrientation.VERTICAL,
                true, // Show legend
                true, // Show tooltips
                false // Show URLs
        );

        // Create chart for sheared points along y-axis
        JFreeChart chartY = ChartFactory.createXYLineChart(
                "Shearing Transformation along y-axis", // Chart title
                "X", // X-axis label
                "Y", // Y-axis label
                datasetY, // Dataset
                PlotOrientation.VERTICAL,
                true, // Show legend
                true, // Show tooltips
                false // Show URLs
        );

        // Display the charts
        SwingUtilities.invokeLater(() -> {
            JFrame frameX = new JFrame("Shearing Transformation along x-axis");
            frameX.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameX.getContentPane().add(new ChartPanel(chartX), BorderLayout.CENTER);
            frameX.pack();
            frameX.setVisible(true);

            JFrame frameY = new JFrame("Shearing Transformation along y-axis");
            frameY.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameY.getContentPane().add(new ChartPanel(chartY), BorderLayout.CENTER);
            frameY.pack();
            frameY.setVisible(true);
        });
    }
}
