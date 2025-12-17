package view;

import dao.KanSekeriDAO;
import model.KanSekeriOlcumu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalTime;
import java.util.List;

public class OlcumListeEkrani extends JFrame {

    private JTable olcumTablosu;
    private DefaultTableModel model;
    private JLabel ortalamaLabel;
    private JLabel uyarilarLabel;

    public OlcumListeEkrani(int hastaId, String hastaAdiSoyadi) {
        setTitle("Ölçüm Listesi - " + hastaAdiSoyadi);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Tablo modeli
        model = new DefaultTableModel(new String[]{"Tarih-Zaman", "Seviye (mg/dL)"}, 0);
        olcumTablosu = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(olcumTablosu);
        add(scrollPane, BorderLayout.CENTER);

        // Alt bilgi paneli
        JPanel altPanel = new JPanel(new GridLayout(2, 1));
        ortalamaLabel = new JLabel("Ortalama: -", SwingConstants.CENTER);
        uyarilarLabel = new JLabel("", SwingConstants.CENTER);

        altPanel.add(ortalamaLabel);
        altPanel.add(uyarilarLabel);
        add(altPanel, BorderLayout.SOUTH);

        // Verileri yükle
        olcumleriYukleVeOrtalamaHesapla(hastaId);

        setVisible(true);
    }

    private void olcumleriYukleVeOrtalamaHesapla(int hastaId) {
        KanSekeriDAO dao = new KanSekeriDAO();
        List<KanSekeriOlcumu> olcumler = dao.getOlcumlerByHastaId(hastaId);

        int toplamOlcum = olcumler.size();
        int sayi = 0;
        double toplamSeviye = 0.0;
        boolean zamanDisiVar = false;

        for (KanSekeriOlcumu o : olcumler) {
            model.addRow(new Object[]{
                    o.getTarihZaman().toString(),
                    o.getSeviye()
            });

            // Zaman aralığı kontrolü (06:00–22:00)
            LocalTime saat = o.getTarihZaman().toLocalTime();
            if (!saat.isBefore(LocalTime.of(6, 0)) && !saat.isAfter(LocalTime.of(22, 0))) {
                toplamSeviye += o.getSeviye();
                sayi++;
            } else {
                zamanDisiVar = true;
            }
        }

        if (toplamOlcum == 0) {
            ortalamaLabel.setText("Ortalama: -");
            uyarilarLabel.setText("⚠ Ölçüm bulunamadı.");
        } else if (sayi < 3) {
            ortalamaLabel.setText("Ortalama: -");
            uyarilarLabel.setText("⚠ Yetersiz veri! Ortalama hesaplaması güvenilir değildir.");
        } else {
            double ortalama = toplamSeviye / sayi;
            ortalamaLabel.setText("Ortalama: " + String.format("%.2f", ortalama) + " mg/dL");

            if (zamanDisiVar) {
                uyarilarLabel.setText("⚠ Bazı ölçümler zaman dışı olduğu için ortalamaya dahil edilmedi.");
            } else {
                uyarilarLabel.setText("");
            }
        }
    }
}
