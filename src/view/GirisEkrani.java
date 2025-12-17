package view;

import dao.KullaniciDAO;
import model.Kullanici;
import utils.HashUtil;

import javax.swing.*;
import java.awt.*;

public class GirisEkrani extends JFrame {

    private JTextField tcField;
    private JPasswordField sifreField;
    private JButton girisButton;
    private JLabel mesajLabel;

    public GirisEkrani() {
        setTitle("Diyabet Takip Sistemi - Giriş");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        tcField = new JTextField();
        sifreField = new JPasswordField();
        girisButton = new JButton("Giriş Yap");
        mesajLabel = new JLabel("", SwingConstants.CENTER);

        add(new JLabel("TC Kimlik No:", SwingConstants.CENTER));
        add(tcField);
        add(new JLabel("Şifre:", SwingConstants.CENTER));
        add(sifreField);
        add(girisButton);
        add(mesajLabel);

        girisButton.addActionListener(e -> girisYap());

        setVisible(true);
    }

    private void girisYap() {
        String tc = tcField.getText().trim();
        String sifre = new String(sifreField.getPassword()).trim();

        String hashedPassword = HashUtil.sha256(sifre);

        KullaniciDAO dao = new KullaniciDAO();
        Kullanici kullanici = dao.girisYap(tc, hashedPassword);

        if (kullanici != null) {
            mesajLabel.setText("✔ Giriş başarılı! Hoş geldiniz, " + kullanici.getAd());
            if (kullanici.getRol().equalsIgnoreCase("hasta")) {
                new HastaPanel(kullanici);
            } else if (kullanici.getRol().equalsIgnoreCase("doktor")) {
                new DoktorPanel(kullanici);
            }
            dispose();
        } else {
            mesajLabel.setText("❌ Giriş başarısız. TC veya şifre hatalı.");
        }
    }

    public static void main(String[] args) {
        new GirisEkrani();
    }
}
