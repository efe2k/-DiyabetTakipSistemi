package dao;

import model.KanSekeriOlcumu;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KanSekeriDAO {

    public boolean olcumEkle(KanSekeriOlcumu olcum) {
        String sql = "INSERT INTO kan_seker_olcumleri (hasta_id, olcum_zamani, seviye) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, olcum.getHastaId());
            stmt.setTimestamp(2, Timestamp.valueOf(olcum.getTarihZaman()));
            stmt.setDouble(3, olcum.getSeviye());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<KanSekeriOlcumu> getOlcumlerByHastaId(int hastaId) {
        List<KanSekeriOlcumu> liste = new ArrayList<>();
        String sql = "SELECT * FROM kan_seker_olcumleri WHERE hasta_id = ? ORDER BY olcum_zamani DESC";

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hastaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                KanSekeriOlcumu o = new KanSekeriOlcumu();
                o.setId(rs.getInt("id"));
                o.setHastaId(rs.getInt("hasta_id"));
                o.setTarihZaman(rs.getTimestamp("olcum_zamani").toLocalDateTime());
                o.setSeviye(rs.getDouble("seviye"));
                liste.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }
}
