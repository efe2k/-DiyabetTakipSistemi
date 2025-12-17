package view;

import dao.InsulinDAO;
import model.InsulinKaydi;
import model.Kullanici;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InsulinListePanel extends JFrame {

    private JTable tablo;
    private DefaultTableModel model;
    private Kullanici hasta;

    public InsulinListePanel(Kullanici hasta) {
        this.hasta = hasta;

        setTitle("İnsülin Takibi - " + hasta.getAd() + " " + hasta.getSoyad());
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"ID", "Tarih", "Doz (ünite)", "Uygulandı mı"}, 0);
        tablo = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tablo);
        add(scrollPane, BorderLayout.CENTER);

        JButton uygulandiBtn = new JButton("Seçili Dozu Uygulandı Olarak İşaretle");
        add(uygulandiBtn, BorderLayout.SOUTH);

        uygulandiBtn.addActionListener(e -> {
            int satir = tablo.getSelectedRow();
            if (satir >= 0) {
                int id = (int) model.getValueAt(satir, 0);
                boolean basarili = new InsulinDAO().uygulamaDurumuGuncelle(id, true);
                if (basarili) {
                    JOptionPane.showMessageDialog(this, "✔ Doz uygulandı olarak işaretlendi.");
                    model.setValueAt(true, satir, 3); // Güncelleme yap
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Güncelleme başarısız.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir doz seçin.");
            }
        });

        verileriYukle();
        setVisible(true);
    }

    private void verileriYukle() {
        InsulinDAO dao = new InsulinDAO();
        List<InsulinKaydi> liste = dao.hastaKayitlariniGetir(hasta.getId());

        for (InsulinKaydi k : liste) {
            model.addRow(new Object[]{
                    k.getId(),
                    k.getTarih().toString(),
                    k.getDozMiktari(),
                    k.isUygulandi()
            });
        }
    }
}
