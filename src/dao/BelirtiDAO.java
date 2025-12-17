package dao;

import model.Belirti;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class BelirtiDAO {

    public boolean belirtiEkle(Belirti belirti) {
        String sql = "INSERT INTO belirtiler (hasta_id, tarih_zaman, seviye, belirtiler) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, belirti.getHastaId());
            stmt.setTimestamp(2, Timestamp.valueOf(belirti.getTarihZaman()));
            stmt.setDouble(3, belirti.getSeviye());
            stmt.setString(4, belirti.getBelirtiler());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
