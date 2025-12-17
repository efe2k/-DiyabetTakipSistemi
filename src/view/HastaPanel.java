package view;

import dao.KanSekeriDAO;
import model.KanSekeriOlcumu;
import model.Kullanici;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HastaPanel extends JFrame {

    public HastaPanel(Kullanici hasta) {
        setTitle("Hasta Paneli - " + hasta.getAd() + " " + hasta.getSoyad());
        setSize(600, 300);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JButton olcumEkleBtn = new JButton("Kan Şekeri Ölçümü Ekle");
        JButton grafikBtn = new JButton("Grafik Gör");
        JButton oneriBtn = new JButton("Önerileri Gör");
        JButton insulinTakipBtn = new JButton("İnsülin Takibini Gör");

        add(olcumEkleBtn);
        add(grafikBtn);
        add(oneriBtn);
        add(insulinTakipBtn);

        olcumEkleBtn.addActionListener(e -> new KanSekeriEklePanel(this, hasta));

        grafikBtn.addActionListener(e -> {
            List<KanSekeriOlcumu> olcumler = new KanSekeriDAO().getOlcumlerByHastaId(hasta.getId());
            if (olcumler.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Grafik için yeterli veri yok.");
            } else {
                new GrafikEkrani(olcumler);
            }
        });

        oneriBtn.addActionListener(e -> new OneriGoruntulePanel( hasta)); // öneri görme paneli varsa

        insulinTakipBtn.addActionListener(e -> new InsulinListePanel(hasta));

        setVisible(true);
    }
}
