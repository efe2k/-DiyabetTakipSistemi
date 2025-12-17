package view;

import dao.OneriDAO;
import model.Kullanici;
import model.Oneri;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OneriGeriBildirimPanel extends JFrame {

    public OneriGeriBildirimPanel(Kullanici hasta) {
        setTitle("Öneri Geri Bildirimleri - " + hasta.getAd() + " " + hasta.getSoyad());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new String[]{
                "ID", "Diyet", "Egzersiz", "Tarih", "Uygulandı mı?"
        }, 0);
        JTable tablo = new JTable(model);
        add(new JScrollPane(tablo), BorderLayout.CENTER);

        OneriDAO dao = new OneriDAO();
        List<Oneri> liste = dao.getOnerilerByHastaId(hasta.getId());

        for (Oneri o : liste) {
            model.addRow(new Object[]{
                    o.getId(),
                    o.getDiyetTuru(),
                    o.getEgzersizTuru(),
                    o.getTarih(),
                    o.isUygulandi() ? "✔ Evet" : "✖ Hayır"
            });
        }

        setVisible(true);
    }
}
