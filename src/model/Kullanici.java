package model;

import java.time.LocalDate;

public class Kullanici {
    private int id;
    private String tcNo;
    private String sifreHash;
    private String ad;
    private String soyad;
    private String eposta; // DİKKAT: "email" değil, "eposta"
    private LocalDate dogumTarihi;
    private String cinsiyet;
    private String rol;

    // --- Getter ve Setter'lar ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTcNo() {
        return tcNo;
    }

    public void setTcNo(String tcNo) {
        this.tcNo = tcNo;
    }

    public String getSifreHash() {
        return sifreHash;
    }

    public void setSifreHash(String sifreHash) {
        this.sifreHash = sifreHash;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public LocalDate getDogumTarihi() {
        return dogumTarihi;
    }

    public void setDogumTarihi(LocalDate dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    private int doktorId; // bu satırı diğer alanların yanına ekle

    public int getDoktorId() {
        return doktorId;
    }

    public void setDoktorId(int doktorId) {
        this.doktorId = doktorId;
    }

}
