/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.util.Random;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;

/**
 *
 * @author Gagadok
 */
public class DiagramProducts extends JFrame {

    private Color[] colors = {new Color(200, 200, 255), new Color(255, 200, 200),
        new Color(200, 255, 200), new Color(200, 255, 200)};

    public DiagramProducts(String[] products, String[] number) {
        super("Диаграмма товаров");
        JFreeChart chart = createChart(createDataset(products, number), products);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Размещение диаграммы в панели
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        setContentPane(chartPanel);
        pack();
        setVisible(true);
    }

    private JFreeChart createChart(PieDataset dataset, String[] products) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Диаграмма товаров", // chart title
                dataset, // data
                false, // no legend
                true, // tooltips
                false // no URL generation
        );

        // Определение фона графического изображения
        chart.setBackgroundPaint(new GradientPaint(new Point(0, 0),
                new Color(20, 20, 20),
                new Point(400, 200),
                Color.DARK_GRAY));

        // Определение заголовка
        TextTitle t = chart.getTitle();
        t.setHorizontalAlignment(HorizontalAlignment.LEFT);
        t.setPaint(new Color(240, 240, 240));
        t.setFont(new Font("Arial", Font.BOLD, 26));

        // Определение подзаголовка
        TextTitle source = new TextTitle("Относительное количество товаров",
                new Font("Courier New", Font.PLAIN, 12));
        source.setPaint(Color.WHITE);
        source.setPosition(RectangleEdge.BOTTOM);
        source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        chart.addSubtitle(source);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(null);
        plot.setInteriorGap(0.04);
        plot.setOutlineVisible(false);

        RadialGradientPaint[] rgp = new RadialGradientPaint[products.length];

        // Инициализируем генератор
        Random rnd = new Random(System.currentTimeMillis());

        if (products.length > 0) {
            rgp[0] = createGradientPaint(colors[0], Color.PINK);
        }

        for (int i = 1; i < products.length; i += 4) {
            if (products.length > i + 1) {
                int random = rnd.nextInt(5);
                rgp[i + 1] = createGradientPaint(colors[random], Color.RED);
            }
            if (products.length > i + 2) {
                int random = rnd.nextInt(5);
                rgp[i + 2] = createGradientPaint(colors[random], Color.GREEN);
            }
            if (products.length > i + 3) {
                int random = rnd.nextInt(5);
                rgp[i + 3] = createGradientPaint(colors[random], Color.YELLOW);
            }
        }

        // Определение секций круговой диаграммы
        for (int i = 0; i < products.length; i++) {
            plot.setSectionPaint(products[i], rgp[i]);
        }

        plot.setBaseSectionOutlinePaint(Color.WHITE);
        plot.setSectionOutlinesVisible(true);
        plot.setBaseSectionOutlineStroke(new BasicStroke(2.0f));

        // Настройка меток названий секций
        plot.setLabelFont(new Font("Courier New", Font.BOLD, 20));
        plot.setLabelLinkPaint(Color.WHITE);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(null);

        return chart;
    }

    private RadialGradientPaint createGradientPaint(Color c1, Color c2) {
        Point2D center = new Point2D.Float(0, 0);
        float radius = 200;
        float[] dist = {0.0f, 1.0f};
        return new RadialGradientPaint(center, radius, dist,
                new Color[]{c1, c2});
    }

    private PieDataset createDataset(String[] products, String[] number) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (int i = 0; i < products.length; i++) {
            dataset.setValue(products[i], new Double(Double.parseDouble(number[i])));
        }
        return dataset;
    }
}
