package BLAlgorithm;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BresenhamLineChart extends ApplicationFrame {

    private final ArrayList<Integer> X_coordinates = new ArrayList<>();
    private final ArrayList<Integer> Y_coordinates = new ArrayList<>();

    public BresenhamLineChart(String title, int x1, int y1, int x2, int y2) {
        super(title);
        drawBresenhamLine(x1, y1, x2, y2);

        // creates a dataset
        DefaultXYDataset dataset = createDataset();

        // creates the chart
        JFreeChart chart = createChart(dataset);

        // creates the panel
        ChartPanel chartPanel = new ChartPanel(chart);

        // sets the size of the panel
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private DefaultXYDataset createDataset() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        double[][] data = new double[2][];

        // returns the number of elements in the list
        int size = X_coordinates.size();

        double[] xData = new double[size];
        double[] yData = new double[size];

        for (int i = 0; i < size; i++) {
            // returns the element at the specified position in this list
            xData[i] = X_coordinates.get(i);
            yData[i] = Y_coordinates.get(i);
        }
        data[0] = xData;
        data[1] = yData;
        dataset.addSeries("Line", data);
        return dataset;
    }

    private JFreeChart createChart(DefaultXYDataset dataset) {
        return ChartFactory.createXYLineChart(
                "Bresenham Line Chart",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }

//    private void drawBresenhamLine(int x1, int y1, int x2, int y2) {
//        int dx = x2 - x1;
//        int dy = y2 - y1;
//        int Pk = 2 * dy - dx;
//
//        System.out.println("1     |   PK   |   Pk+1   |  Xk+1  |   Yk+1  |  Plot  ");
//        System.out.println("      |        |     " + x1 + "   |  " + y1 + "    |   (" + x1 + "," + y1 + ")     ");
//        X_coordinates.add(x1);
//        Y_coordinates.add(y1);
//
//        for (int i = 0; i < dx; i++) {
//            int Pkn;
//            if (Pk < 0) {
//                Pkn = Pk + (2 * dy);
//                x1 += 1;
//                System.out.println("   " + i + "   |   " + Pk + "   |    " + Pkn + "    |     " + x1 + "   |  " + y1 + "    |   (" + x1 + "," + y1 + ")     ");
//                Pk = Pkn;
//            } else if (Pk > 0){
//                Pkn = Pk + (2 * dy - 2 * dx);
//                x1 += 1;
//                y1 += 1;
//                System.out.println("   " + i + "   |   " + Pk + "   |    " + Pkn + "    |     " + x1 + "   |  " + y1 + "    |   (" + x1 + "," + y1 + ")     ");
//                Pk = Pkn;
//            }
//            X_coordinates.add(x1);
//            Y_coordinates.add(y1);
//        }
//    }

    private void drawBresenhamLine(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int Pk = 2 * dy - dx;
        int xIncrement = x1 < x2 ? 1 : -1;
        int yIncrement = y1 < y2 ? 1 : -1;
        int x = x1;
        int y = y1;

        System.out.println("1     |   PK   |   Pk+1   |  Xk+1  |   Yk+1  |  Plot  ");
        System.out.println("      |        |     " + x1 + "   |  " + y1 + "    |   (" + x1 + "," + y1 + ")     ");
//        X_coordinates.add(x1);
//        Y_coordinates.add(y1);

        for (int i = 0; i < dx; i++) {
            if (Pk < 0) {
                Pk += 2 * dy;
            } else {
                Pk += 2 * dy - 2 * dx;
                y += yIncrement;
            }
            x += xIncrement;
            System.out.println("   " + i + "   |   " + Pk + "   |    " + (Pk + 2 * dy) + "    |     " + x + "   |  " + y + "    |   (" + x + "," + y + ")     ");
            X_coordinates.add(x);
            Y_coordinates.add(y);
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("STARTING POINTS");
        int x1 = scanner.nextInt();
        int y1 = scanner.nextInt();
        System.out.println("ENDING POINTS");
        int x2 = scanner.nextInt();
        int y2 = scanner.nextInt();
        BresenhamLineChart chart = new BresenhamLineChart("Bresenham Line Chart", x1, y1, x2, y2);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
        scanner.close();
    }
}

