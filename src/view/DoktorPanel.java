package view;

import dao.KanSekeriDAO;
import dao.KullaniciDAO;
import model.Kullanici;
import model.KanSekeriOlcumu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DoktorPanel extends JFrame {

    private Kullanici doktor;
    private JTable tablo;
    private DefaultTableModel model;

    public DoktorPanel(Kullanici doktor) {
        this.doktor = doktor;

        setTitle("Doktor Paneli - " + doktor.getAd() + " " + doktor.getSoyad());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"ID", "Ad", "Soyad", "TC", "Doğum Tarihi"}, 0);
        tablo = new JTable(model);
        add(new JScrollPane(tablo), BorderLayout.CENTER);

        JPanel butonPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        JButton yeniHastaBtn = new JButton("Yeni Hasta Ekle");
        JButton ilkOlcumBtn = new JButton("İlk Ölçüm ve Belirti Girişi");
        JButton olcumBtn = new JButton("Ölçüm Gör");
        JButton oneriVerBtn = new JButton("Öneri Ver");
        JButton geriBildirimBtn = new JButton("Öneri Geri Bildirimlerini Gör");
        JButton grafikBtn = new JButton("Grafik");
        JButton insulinEkleBtn = new JButton("İnsülin Kaydı Ekle"); // ✅
        JButton insulinListeBtn = new JButton("İnsülin Kayıtlarını Gör"); // ✅

        butonPanel.add(yeniHastaBtn);
        butonPanel.add(ilkOlcumBtn);
        butonPanel.add(olcumBtn);
        butonPanel.add(oneriVerBtn);
        butonPanel.add(geriBildirimBtn);
        butonPanel.add(grafikBtn);
        butonPanel.add(insulinEkleBtn);
        butonPanel.add(insulinListeBtn);

        add(butonPanel, BorderLayout.SOUTH);

        listeleHastalar();

        yeniHastaBtn.addActionListener(e -> new YeniHastaEklePanel(this,doktor));

        ilkOlcumBtn.addActionListener(e -> {
            int secili = tablo.getSelectedRow();
            if (secili >= 0) {
                Kullanici hasta = seciliHastaGetir(secili);
                new IlkOlcumBelirtiPanel(this, hasta);
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir hasta seçin.");
            }
        });

        olcumBtn.addActionListener(e -> {
            int secili = tablo.getSelectedRow();
            if (secili >= 0) {
                int hastaId = (int) model.getValueAt(secili, 0);
                String adSoyad = model.getValueAt(secili, 1) + " " + model.getValueAt(secili, 2);
                new OlcumListeEkrani(hastaId, adSoyad);
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir hasta seçin.");
            }
        });

        oneriVerBtn.addActionListener(e -> {
            int secili = tablo.getSelectedRow();
            if (secili >= 0) {
                Kullanici hasta = seciliHastaGetir(secili);
                new OneriVerPanel(this, hasta);
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir hasta seçin.");
            }
        });

        geriBildirimBtn.addActionListener(e -> {
            int secili = tablo.getSelectedRow();
            if (secili >= 0) {
                Kullanici hasta = seciliHastaGetir(secili);
                new OneriGeriBildirimPanel(hasta);
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir hasta seçin.");
            }
        });

        grafikBtn.addActionListener(e -> {
            int secili = tablo.getSelectedRow();
            if (secili >= 0) {
                int hastaId = (int) model.getValueAt(secili, 0);
                List<KanSekeriOlcumu> olcumler = new KanSekeriDAO().getOlcumlerByHastaId(hastaId);
                if (olcumler.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Grafik için yeterli veri yok.");
                } else {
                    new GrafikEkrani(olcumler);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir hasta seçin.");
            }
        });

        // ✅ İnsülin Kaydı Ekle
        insulinEkleBtn.addActionListener(e -> {
            int secili = tablo.getSelectedRow();
            if (secili >= 0) {
                Kullanici hasta = seciliHastaGetir(secili);
                new InsulinVerPanel(this, hasta);
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir hasta seçin.");
            }
        });

        // ✅ İnsülin Listele
        insulinListeBtn.addActionListener(e -> {
            int secili = tablo.getSelectedRow();
            if (secili >= 0) {
                Kullanici hasta = seciliHastaGetir(secili);
                new InsulinListePanel(hasta);
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir hasta seçin.");
            }
        });

        setVisible(true);
    }

    private Kullanici seciliHastaGetir(int secili) {
        Kullanici hasta = new Kullanici();
        hasta.setId((int) model.getValueAt(secili, 0));
        hasta.setAd((String) model.getValueAt(secili, 1));
        hasta.setSoyad((String) model.getValueAt(secili, 2));
        hasta.setTcNo((String) model.getValueAt(secili, 3));
        return hasta;
    }


    private void listeleHastalar() {
        model.setRowCount(0);
        List<Kullanici> hastalar = new KullaniciDAO().getHastalarByDoktorId(doktor.getId());
        for (Kullanici k : hastalar) {
            model.addRow(new Object[]{
                    k.getId(),
                    k.getAd(),
                    k.getSoyad(),
                    k.getTcNo(),
                    k.getDogumTarihi()
            });
        }
    }





}
