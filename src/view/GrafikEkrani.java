package view;

import model.KanSekeriOlcumu;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.List;

public class GrafikEkrani extends JFrame {

    public GrafikEkrani(List<KanSekeriOlcumu> olcumler) {
        setTitle("Kan Şekeri Zaman Grafiği");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Zaman serisi oluştur
        TimeSeries series = new TimeSeries("Kan Şekeri (mg/dL)");
        for (KanSekeriOlcumu o : olcumler) {
            series.addOrUpdate(new Second(Timestamp.valueOf(o.getTarihZaman())), o.getSeviye());
        }

        // Dataset ve grafik oluştur
        TimeSeriesCollection dataset = new TimeSeriesCollection(series);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Kan Şekeri Zaman Grafiği",
                "Zaman",
                "Seviye (mg/dL)",
                dataset,
                false,
                true,
                false
        );

        // Panel olarak ekle
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel);

        setVisible(true);
    }
}
