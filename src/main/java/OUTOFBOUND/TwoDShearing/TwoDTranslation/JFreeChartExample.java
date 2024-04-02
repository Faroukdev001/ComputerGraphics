package OUTOFBOUND.TwoDShearing.TwoDTranslation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;

public class JFreeChartExample extends JFrame {

    public JFreeChartExample() {
        initUI();
    }

    private void initUI() {
        setTitle("OUTOFBOUND.TwoDShearing.Translation Graph");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        XYSeriesCollection dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 400));
        add(chartPanel);

    }

    private XYSeriesCollection createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series1 = new XYSeries("Original Triangle");
        series1.add(10, 20);
        series1.add(10, 10);
        series1.add(20, 10);
        series1.add(10, 20);


        XYSeries series2 = new XYSeries("Translated Triangle");
        series2.add(10 + 5, 20 + 10);
        series2.add(10 + 5, 10 + 10);
        series2.add(20 + 5, 10 + 10);
        series2.add(10 + 5, 20 + 10);



        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }

    private JFreeChart createChart(XYSeriesCollection dataset) {
        return ChartFactory.createXYLineChart(
                "OUTOFBOUND.TwoDShearing.Translation Graph",
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
        EventQueue.invokeLater(() -> {
            JFreeChartExample ex = new JFreeChartExample();
            ex.setVisible(true);
        });
    }
}
