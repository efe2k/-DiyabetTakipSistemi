package dao;

import model.Oneri;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OneriDAO {

    public boolean oneriEkle(Oneri oneri) {
        String sql = "INSERT INTO oneriler (hasta_id, diyet_turu, egzersiz_turu, tarih, uygulandi) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, oneri.getHastaId());
            stmt.setString(2, oneri.getDiyetTuru());
            stmt.setString(3, oneri.getEgzersizTuru());
            stmt.setTimestamp(4, Timestamp.valueOf(oneri.getTarih()));
            stmt.setBoolean(5, oneri.isUygulandi());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Oneri> getOnerilerByHastaId(int hastaId) {
        List<Oneri> liste = new ArrayList<>();
        String sql = "SELECT * FROM oneriler WHERE hasta_id = ? ORDER BY tarih DESC";

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hastaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Oneri o = new Oneri();
                o.setId(rs.getInt("id"));
                o.setHastaId(rs.getInt("hasta_id"));
                o.setDiyetTuru(rs.getString("diyet_turu"));
                o.setEgzersizTuru(rs.getString("egzersiz_turu"));
                o.setTarih(rs.getTimestamp("tarih").toLocalDateTime());
                o.setUygulandi(rs.getBoolean("uygulandi"));
                liste.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }

    public boolean geriBildirimGuncelle(int oneriId, boolean uygulandi) {
        String sql = "UPDATE oneriler SET uygulandi = ? WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, uygulandi);
            stmt.setInt(2, oneriId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int getToplamOneriSayisi(int hastaId) {
        String sql = "SELECT COUNT(*) FROM oneriler WHERE hasta_id = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hastaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getUygulananOneriSayisi(int hastaId) {
        String sql = "SELECT COUNT(*) FROM oneriler WHERE hasta_id = ? AND uygulandi = TRUE";
        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hastaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}

