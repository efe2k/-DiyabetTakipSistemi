package view;

import dao.OneriDAO;
import model.Kullanici;
import model.Oneri;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OneriGoruntulePanel extends JFrame {

    private DefaultTableModel model;
    private JTable tablo;

    public OneriGoruntulePanel(Kullanici hasta) {
        setTitle("Öneriler - " + hasta.getAd() + " " + hasta.getSoyad());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tablo modeli ve tabloyu oluştur
        model = new DefaultTableModel(new String[]{"ID", "Diyet Türü", "Egzersiz Türü", "Tarih", "Uygulandı mı?"}, 0);
        tablo = new JTable(model);
        add(new JScrollPane(tablo), BorderLayout.CENTER);

        // Güncelle butonu
        JButton uygulaBtn = new JButton("Seçili öneriyi uygulandı olarak işaretle");
        add(uygulaBtn, BorderLayout.SOUTH);

        uygulaBtn.addActionListener(e -> {
            int seciliSatir = tablo.getSelectedRow();
            if (seciliSatir >= 0) {
                int oneriId = (int) model.getValueAt(seciliSatir, 0);
                OneriDAO dao = new OneriDAO();
                if (dao.geriBildirimGuncelle(oneriId, true)) {
                    model.setValueAt(true, seciliSatir, 4);
                    JOptionPane.showMessageDialog(this, "✔ Geri bildirim gönderildi.");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Hata oluştu.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir öneri seçin.");
            }
        });

        // Önerileri yükle
        onerileriYukle(hasta.getId());

        setVisible(true);
    }

    private void onerileriYukle(int hastaId) {
        OneriDAO dao = new OneriDAO();
        List<Oneri> liste = dao.getOnerilerByHastaId(hastaId);

        for (Oneri o : liste) {
            model.addRow(new Object[]{
                    o.getId(), o.getDiyetTuru(), o.getEgzersizTuru(),
                    o.getTarih(), o.isUygulandi()
            });
        }
    }
}
