package view;

import dao.InsulinDAO;
import model.InsulinKaydi;
import model.Kullanici;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class InsulinVerPanel extends JDialog {

    private JTextField dozField;
    private JTextField tarihField;
    private JLabel mesajLabel;

    public InsulinVerPanel(JFrame parent, Kullanici hasta) {
        super(parent, "İnsülin Dozu Ekle", true);
        setSize(350, 220);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4, 1, 5, 5));

        dozField = new JTextField();
        tarihField = new JTextField("2025-05-27"); // Öntanımlı tarih formatı
        mesajLabel = new JLabel("", SwingConstants.CENTER);

        JButton ekleBtn = new JButton("Doz Ekle");

        add(new JLabel("Doz Miktarı (ünite):", SwingConstants.CENTER));
        add(dozField);
        add(new JLabel("Tarih (YYYY-MM-DD):", SwingConstants.CENTER));
        add(tarihField);
        add(ekleBtn);
        add(mesajLabel);

        ekleBtn.addActionListener(e -> {
            try {
                double doz = Double.parseDouble(dozField.getText().trim());
                LocalDate tarih = LocalDate.parse(tarihField.getText().trim());

                InsulinKaydi kayit = new InsulinKaydi();
                kayit.setHastaId(hasta.getId());
                kayit.setDozMiktari(doz);
                kayit.setTarih(tarih);
                kayit.setUygulandi(false);

                InsulinDAO dao = new InsulinDAO();
                if (dao.insulinEkle(kayit)) {
                    mesajLabel.setText("✔ Kayıt eklendi.");
                    dozField.setText("");
                } else {
                    mesajLabel.setText("❌ Kayıt eklenemedi.");
                }

            } catch (Exception ex) {
                mesajLabel.setText("⚠ Lütfen geçerli değer girin.");
            }
        });

        setVisible(true);
    }
}
