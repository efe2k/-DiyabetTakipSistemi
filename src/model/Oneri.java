package model;

import java.time.LocalDateTime;

public class Oneri {
    private int id;
    private int hastaId;
    private String diyetTuru;
    private String egzersizTuru;
    private LocalDateTime tarih;
    private boolean uygulandi;

    // Getter ve Setter'lar
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getHastaId() { return hastaId; }
    public void setHastaId(int hastaId) { this.hastaId = hastaId; }

    public String getDiyetTuru() { return diyetTuru; }
    public void setDiyetTuru(String diyetTuru) { this.diyetTuru = diyetTuru; }

    public String getEgzersizTuru() { return egzersizTuru; }
    public void setEgzersizTuru(String egzersizTuru) { this.egzersizTuru = egzersizTuru; }

    public LocalDateTime getTarih() { return tarih; }
    public void setTarih(LocalDateTime tarih) { this.tarih = tarih; }

    public boolean isUygulandi() { return uygulandi; }
    public void setUygulandi(boolean uygulandi) { this.uygulandi = uygulandi; }
}
