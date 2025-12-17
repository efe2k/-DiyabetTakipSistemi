package dao;

import model.Kullanici;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KullaniciDAO {

    public boolean hastaEkle(Kullanici hasta) {
        String sql = "INSERT INTO kullanicilar (tc_no, sifre_hash, ad, soyad, eposta, dogum_tarihi, cinsiyet, rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hasta.getTcNo());
            stmt.setString(2, hasta.getSifreHash());
            stmt.setString(3, hasta.getAd());
            stmt.setString(4, hasta.getSoyad());
            stmt.setString(5, hasta.getEposta());
            stmt.setDate(6, Date.valueOf(hasta.getDogumTarihi()));
            stmt.setString(7, hasta.getCinsiyet());
            stmt.setString(8, hasta.getRol());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Kullanici girisYap(String tcNo, String sifreHash) {
        String sql = "SELECT * FROM kullanicilar WHERE tc_no = ? AND sifre_hash = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tcNo);
            stmt.setString(2, sifreHash);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Kullanici k = new Kullanici();
                k.setId(rs.getInt("id"));
                k.setTcNo(rs.getString("tc_no"));
                k.setSifreHash(rs.getString("sifre_hash"));
                k.setAd(rs.getString("ad"));
                k.setSoyad(rs.getString("soyad"));
                k.setEposta(rs.getString("eposta"));
                k.setDogumTarihi(rs.getDate("dogum_tarihi").toLocalDate());
                k.setCinsiyet(rs.getString("cinsiyet"));
                k.setRol(rs.getString("rol"));
                return k;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Kullanici> getHastalar() {
        List<Kullanici> hastalar = new ArrayList<>();
        String sql = "SELECT * FROM kullanicilar WHERE rol = 'hasta' AND doktor_id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Kullanici k = new Kullanici();
                k.setId(rs.getInt("id"));
                k.setAd(rs.getString("ad"));
                k.setSoyad(rs.getString("soyad"));
                k.setTcNo(rs.getString("tc_no"));
                k.setDogumTarihi(rs.getDate("dogum_tarihi").toLocalDate());
                hastalar.add(k);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hastalar;
    }
    public List<Kullanici> getHastalarByDoktorId(int doktorId) {
        List<Kullanici> list = new ArrayList<>();
        String sql = "SELECT * FROM kullanicilar WHERE rol = 'hasta' AND doktor_id = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doktorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Kullanici k = new Kullanici();
                k.setId(rs.getInt("id"));
                k.setAd(rs.getString("ad"));
                k.setSoyad(rs.getString("soyad"));
                k.setTcNo(rs.getString("tc_no"));
                k.setDogumTarihi(rs.getDate("dogum_tarihi").toLocalDate());
                k.setDoktorId(doktorId);
                list.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
