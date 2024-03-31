package MidPointLineAlgorithm;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MidpointLineChart extends ApplicationFrame {

    private final ArrayList<Integer> X_coordinates = new ArrayList<>();
    private final ArrayList<Integer> Y_coordinates = new ArrayList<>();

    public MidpointLineChart(String title, int x1, int y1, int x2, int y2) {
        super(title);
        calculateMidpointLine(x1, y1, x2, y2);

        // creates the dataset
        DefaultXYDataset dataset = createDataset();

        // creates the chart
        JFreeChart chart = createChart(dataset);

        // creates the chart panel
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
                "Midpoint Line Chart",
                "X Axis",
                "Y Axis",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }

    private void calculateMidpointLine(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int Pk = 2 * dy - dx;

        System.out.println("Iteration |   Pk   |   X   |   Y  |");
        System.out.println("-----------------------------------");
        System.out.printf("   0      |   %d    |  %d   |  %d  |\n",Pk, x1, y1);

        int x = x1;
        int y = y1;

        X_coordinates.add(x);
        Y_coordinates.add(y);

        for (int i = 0; i < dx; i++) {
            int Pkn;
            if (Pk < 0) {
                // moves east
                Pkn = Pk + (2 * dy);
                x = x + 1;
            } else {
                // moves north east
                Pkn = Pk + (2 * dy - 2 * dx);
                x = x + 1;
                y = y + 1;
            }
            System.out.printf("   %d      |   %d   |  %d   |  %d  |\n", i + 1, Pkn, x, y);
            Pk = Pkn;
            X_coordinates.add(x);
            Y_coordinates.add(y);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the starting point of x: ");
        int x1 = scanner.nextInt();
        System.out.println("Enter the starting point of y: ");
        int y1 = scanner.nextInt();
        System.out.println("Enter the end point of x: ");
        int x2 = scanner.nextInt();
        System.out.println("Enter the end point of y: ");
        int y2 = scanner.nextInt();
        MidpointLineChart chart = new MidpointLineChart("Midpoint Line Chart", x1, y1, x2, y2);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
        scanner.close();
    }
}
