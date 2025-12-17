package dao;

import model.InsulinKaydi;
import utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InsulinDAO {

    // 1. İnsülin kaydı ekle (doktor tarafından)
    public boolean insulinEkle(InsulinKaydi kayit) {
        String sql = "INSERT INTO insulin_kayitlari (hasta_id, tarih, doz_miktari, uygulandi) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, kayit.getHastaId());
            stmt.setDate(2, Date.valueOf(kayit.getTarih()));
            stmt.setDouble(3, kayit.getDozMiktari());
            stmt.setBoolean(4, kayit.isUygulandi());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Belirli hastaya ait tüm kayıtları getir
    public List<InsulinKaydi> hastaKayitlariniGetir(int hastaId) {
        List<InsulinKaydi> liste = new ArrayList<>();
        String sql = "SELECT * FROM insulin_kayitlari WHERE hasta_id = ? ORDER BY tarih DESC";

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hastaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                InsulinKaydi k = new InsulinKaydi();
                k.setId(rs.getInt("id"));
                k.setHastaId(rs.getInt("hasta_id"));
                k.setTarih(rs.getDate("tarih").toLocalDate());
                k.setDozMiktari(rs.getDouble("doz_miktari"));
                k.setUygulandi(rs.getBoolean("uygulandi"));
                liste.add(k);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }

    // 3. Hasta tarafında uygulandı bilgisi güncelle
    public boolean uygulamaDurumuGuncelle(int id, boolean uygulandi) {
        String sql = "UPDATE insulin_kayitlari SET uygulandi = ? WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, uygulandi);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
