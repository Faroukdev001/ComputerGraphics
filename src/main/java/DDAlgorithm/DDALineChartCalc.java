package DDAlgorithm;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class DDALineChartCalc extends ApplicationFrame {

    // DDALineChartCalc Constructor initializes the starting and ending points
    public DDALineChartCalc(String title) {
        super(title);
        int x1 = 2, y1 = 2;
        int x2 = 8, y2 = 10;
        System.out.println("DDA Line Algorithm:");
        System.out.println("Starting point: (" + x1 + ", " + y1 + ")");
        System.out.println("Ending point: (" + x2 + ", " + y2 + ")");
        System.out.println("Calculating iterations:");

        // creates a dataset
        DefaultXYDataset dataset = createDataset(x1, y1, x2, y2);

        // creates a chart
        JFreeChart chart = createChart(dataset);

        // creates the chart panel to hold the chart
        ChartPanel chartPanel = new ChartPanel(chart);

        // set the size of the panel
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private DefaultXYDataset createDataset(int x1, int y1, int x2, int y2) {
        DefaultXYDataset dataset = new DefaultXYDataset();
        double[][] data = new double[2][];
        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        double xinc = (double) dx / steps;
        double yinc = (double) dy / steps;

        double x = x1;
        double y = y1;

        double[] xData = new double[steps + 1];
        double[] yData = new double[steps + 1];

        // this loop calculate the coordinates of all the points
        for (int i = 0; i <= steps; i++) {
            xData[i] = Math.round(x);
            yData[i] = Math.round(y);
            x += xinc;
            y += yinc;
            System.out.println("Iteration " + (i + 1) + ": (" + Math.round(x) + ", " + Math.round(y) + ")");
        }

        data[0] = xData;
        data[1] = yData;
        dataset.addSeries("Line", data);
        return dataset;
    }

    private JFreeChart createChart(DefaultXYDataset dataset) {
        return ChartFactory.createXYLineChart(
                "DDA Line Chart",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }

    public static void main(String[] args) {
        DDALineChartCalc chart = new DDALineChartCalc("DDA Line Chart");
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}

