package model;

import java.time.LocalDateTime;

public class KanSekeriOlcumu {
    private int id;
    private int hastaId;
    private LocalDateTime tarihZaman;
    private double seviye;

    // getter-setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getHastaId() { return hastaId; }
    public void setHastaId(int hastaId) { this.hastaId = hastaId; }

    public LocalDateTime getTarihZaman() { return tarihZaman; }
    public void setTarihZaman(LocalDateTime tarihZaman) { this.tarihZaman = tarihZaman; }

    public double getSeviye() { return seviye; }
    public void setSeviye(double seviye) { this.seviye = seviye; }
}
