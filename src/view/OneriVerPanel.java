package view;

import dao.OneriDAO;
import model.Kullanici;
import model.Oneri;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class OneriVerPanel extends JDialog {

    private JComboBox<String> diyetCombo;
    private JComboBox<String> egzersizCombo;
    private JLabel mesajLabel;

    public OneriVerPanel(JFrame parent, Kullanici hasta) {
        super(parent, "Öneri Ver", true);
        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 1, 5, 5));

        String[] diyetler = {"Az Şekerli", "Şekersiz", "Dengeli"};
        String[] egzersizler = {"Yürüyüş", "Bisiklet", "Klinik Egzersiz"};

        diyetCombo = new JComboBox<>(diyetler);
        egzersizCombo = new JComboBox<>(egzersizler);
        JButton kaydetBtn = new JButton("Kaydet");
        mesajLabel = new JLabel("", SwingConstants.CENTER);

        add(new JLabel("Diyet Türü:", SwingConstants.CENTER));
        add(diyetCombo);
        add(new JLabel("Egzersiz Türü:", SwingConstants.CENTER));
        add(egzersizCombo);
        add(kaydetBtn);
        add(mesajLabel);

        kaydetBtn.addActionListener(e -> {
            String diyet = (String) diyetCombo.getSelectedItem();
            String egzersiz = (String) egzersizCombo.getSelectedItem();

            Oneri oneri = new Oneri();
            oneri.setHastaId(hasta.getId());
            oneri.setDiyetTuru(diyet);
            oneri.setEgzersizTuru(egzersiz);
            oneri.setTarih(LocalDateTime.now());
            oneri.setUygulandi(false); // hasta tarafından sonradan işaretlenecek

            OneriDAO dao = new OneriDAO();
            boolean basarili = dao.oneriEkle(oneri);

            mesajLabel.setText(basarili ? "✔ Öneri eklendi." : "❌ Hata oluştu.");
        });

        setVisible(true);
    }
}
