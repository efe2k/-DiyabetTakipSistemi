package view;

import dao.KanSekeriDAO;
import model.KanSekeriOlcumu;
import model.Kullanici;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class KanSekeriEklePanel extends JDialog {

    private JTextField seviyeField, tarihField, saatField;
    private JLabel mesajLabel;
    private JLabel uyariLabel;

    public KanSekeriEklePanel(JFrame parent, Kullanici hasta) {
        super(parent, "Kan Şekeri Ölçümü Ekle", true);
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(7, 1, 5, 5));

        seviyeField = new JTextField();
        tarihField = new JTextField("26.05.2025");
        saatField = new JTextField("14:00:00");
        mesajLabel = new JLabel("", SwingConstants.CENTER);
        uyariLabel = new JLabel("", SwingConstants.CENTER);
        JButton kaydetBtn = new JButton("Kaydet");

        add(new JLabel("Kan şekeri seviyesi (mg/dL):", SwingConstants.CENTER));
        add(seviyeField);
        add(new JLabel("Tarih (GG.AA.YYYY):", SwingConstants.CENTER));
        add(tarihField);
        add(new JLabel("Saat (SS:dd:ss):", SwingConstants.CENTER));
        add(saatField);
        add(kaydetBtn);
        add(mesajLabel);
        add(uyariLabel);

        kaydetBtn.addActionListener(e -> {
            try {
                double seviye = Double.parseDouble(seviyeField.getText().trim());

                String girilenTarih = tarihField.getText().trim();
                String girilenSaat = saatField.getText().trim();
                String tamZaman = girilenTarih + " " + girilenSaat;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                LocalDateTime tarihZaman = LocalDateTime.parse(tamZaman, formatter);

                KanSekeriOlcumu olcum = new KanSekeriOlcumu();
                olcum.setHastaId(hasta.getId());
                olcum.setTarihZaman(tarihZaman);
                olcum.setSeviye(seviye);

                KanSekeriDAO dao = new KanSekeriDAO();
                if (dao.olcumEkle(olcum)) {
                    mesajLabel.setText("✔ Ölçüm başarıyla kaydedildi.");
                    seviyeField.setText("");

                    // ⚠ Otomatik uyarı sistemi
                    String uyari = "";
                    if (seviye < 70) {
                        uyari = "⚠ Düşük kan şekeri (Hipoglisemi)";
                    } else if (seviye >= 126) {
                        uyari = "⚠ Yüksek kan şekeri (Diyabet Riski)";
                    } else if (seviye >= 100) {
                        uyari = "⚠ Orta seviye (Prediyabet)";
                    } else {
                        uyari = "✔ Normal seviye.";
                    }
                    uyariLabel.setText(uyari);
                } else {
                    mesajLabel.setText("❌ Kayıt başarısız.");
                }

            } catch (NumberFormatException ex) {
                mesajLabel.setText("⚠ Lütfen geçerli bir sayı girin.");
            } catch (DateTimeParseException ex) {
                mesajLabel.setText("⚠ Tarih/Saat formatı yanlış!");
            }
        });

        setVisible(true);
    }
}
