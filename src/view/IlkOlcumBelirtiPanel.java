package view;

import dao.BelirtiDAO;
import dao.KanSekeriDAO;
import model.Belirti;
import model.KanSekeriOlcumu;
import model.Kullanici;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IlkOlcumBelirtiPanel extends JFrame {

    public IlkOlcumBelirtiPanel(JFrame parent, Kullanici hasta) {
        setTitle("İlk Ölçüm ve Belirti Girişi - " + hasta.getAd() + " " + hasta.getSoyad());
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(0, 2, 10, 10));

        // Kan şekeri ölçüm alanı
        JLabel seviyeLabel = new JLabel("Kan Şekeri (mg/dL):");
        JTextField seviyeField = new JTextField();

        // Belirti checkbox'ları
        JCheckBox ckPolifaji = new JCheckBox("Polifaji");
        JCheckBox ckPolidipsi = new JCheckBox("Polidipsi");
        JCheckBox ckPoliüri = new JCheckBox("Poliüri");
        JCheckBox ckYorgunluk = new JCheckBox("Yorgunluk");
        JCheckBox ckNöropati = new JCheckBox("Nöropati");
        JCheckBox ckBulanikGorme = new JCheckBox("Bulanık Görme");
        JCheckBox ckKiloKaybi = new JCheckBox("Kilo Kaybı");

        JButton kaydetBtn = new JButton("Kaydet");

        add(seviyeLabel);
        add(seviyeField);
        add(ckPolifaji);
        add(ckPolidipsi);
        add(ckPoliüri);
        add(ckYorgunluk);
        add(ckNöropati);
        add(ckBulanikGorme);
        add(ckKiloKaybi);
        add(new JLabel()); // boşluk
        add(kaydetBtn);

        kaydetBtn.addActionListener(e -> {
            try {
                double seviye = Double.parseDouble(seviyeField.getText().trim());

                // 1. Ölçümü veritabanına kaydet
                KanSekeriOlcumu olcum = new KanSekeriOlcumu();
                olcum.setHastaId(hasta.getId());
                olcum.setSeviye(seviye);
                olcum.setTarihZaman(LocalDateTime.now());

                KanSekeriDAO dao = new KanSekeriDAO();
                boolean basarili = dao.olcumEkle(olcum);

                // 2. Belirtileri veritabanına kaydet
                List<String> secilenBelirtiler = new ArrayList<>();
                if (ckPolifaji.isSelected()) secilenBelirtiler.add("Polifaji");
                if (ckPolidipsi.isSelected()) secilenBelirtiler.add("Polidipsi");
                if (ckPoliüri.isSelected()) secilenBelirtiler.add("Poliüri");
                if (ckYorgunluk.isSelected()) secilenBelirtiler.add("Yorgunluk");
                if (ckNöropati.isSelected()) secilenBelirtiler.add("Nöropati");
                if (ckBulanikGorme.isSelected()) secilenBelirtiler.add("Bulanık Görme");
                if (ckKiloKaybi.isSelected()) secilenBelirtiler.add("Kilo Kaybı");

                BelirtiDAO belirtiDAO = new BelirtiDAO();
                for (String belirti : secilenBelirtiler) {
                    Belirti b = new Belirti();
                    b.setHastaId(hasta.getId());
                    b.setBelirti(belirti);
                    belirtiDAO.belirtiEkle(b);
                }

                if (basarili) {
                    JOptionPane.showMessageDialog(this, "İlk ölçüm ve belirtiler başarıyla kaydedildi.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Ölçüm kaydedilirken hata oluştu.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli bir sayı girin.");
            }
        });

        setVisible(true);
    }
}
