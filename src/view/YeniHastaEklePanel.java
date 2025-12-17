package view;

import dao.KullaniciDAO;
import model.Kullanici;
import utils.EmailService;
import utils.HashUtil;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class YeniHastaEklePanel extends JDialog {
    private JTextField adField, soyadField, tcField, sifreField, emailField, dogumTarihField, cinsiyetField;
    private Kullanici doktor;

    public YeniHastaEklePanel(JFrame parent, Kullanici doktor) {
        super(parent, "Yeni Hasta Ekle", true);
        this.doktor = doktor;

        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(8, 2, 5, 5));

        adField = new JTextField();
        soyadField = new JTextField();
        tcField = new JTextField();
        sifreField = new JTextField();
        emailField = new JTextField();
        dogumTarihField = new JTextField();
        cinsiyetField = new JTextField();

        add(new JLabel("Ad:")); add(adField);
        add(new JLabel("Soyad:")); add(soyadField);
        add(new JLabel("TC No:")); add(tcField);
        add(new JLabel("Şifre:")); add(sifreField);
        add(new JLabel("E-posta:")); add(emailField);
        add(new JLabel("Doğum Tarihi (YYYY-MM-DD):")); add(dogumTarihField);
        add(new JLabel("Cinsiyet (E/K):")); add(cinsiyetField);

        JButton ekleBtn = new JButton("Ekle");
        add(ekleBtn);

        ekleBtn.addActionListener(e -> {
            Kullanici hasta = new Kullanici();
            hasta.setAd(adField.getText().trim());
            hasta.setSoyad(soyadField.getText().trim());
            hasta.setTcNo(tcField.getText().trim());
            hasta.setSifreHash(HashUtil.sha256(sifreField.getText().trim()));
            hasta.setEposta(emailField.getText().trim());
            hasta.setDogumTarihi(LocalDate.parse(dogumTarihField.getText().trim()));
            hasta.setCinsiyet(cinsiyetField.getText().trim());
            hasta.setRol("hasta");
            hasta.setDoktorId(doktor.getId());

            if (new KullaniciDAO().hastaEkle(hasta)) {
                JOptionPane.showMessageDialog(this, "Hasta eklendi.");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Hasta eklenemedi.");
            }
        });

        setVisible(true);
    }
}
