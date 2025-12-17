package view;

import dao.OneriDAO;
import model.Kullanici;
import model.Oneri;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OneriListePanel extends JDialog {

    private JTable tablo;
    private DefaultTableModel model;

    public OneriListePanel(JFrame parent, Kullanici hasta) {
        super(parent, "Öneri Geçmişi - " + hasta.getAd() + " " + hasta.getSoyad(), true);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Tarih", "Diyet", "Egzersiz", "Uygulandı mı?"}, 0);
        tablo = new JTable(model);
        add(new JScrollPane(tablo), BorderLayout.CENTER);

        // Önerileri getir
        listeleOneriler(hasta.getId());

        setVisible(true);
    }

    private void listeleOneriler(int hastaId) {
        OneriDAO dao = new OneriDAO();
        List<Oneri> liste = dao.getOnerilerByHastaId(hastaId);

        for (Oneri o : liste) {
            model.addRow(new Object[]{
                    o.getTarih(),
                    o.getDiyetTuru(),
                    o.getEgzersizTuru(),
                    o.isUygulandi() ? "✔ Evet" : "✖ Hayır"
            });
        }
    }
}
