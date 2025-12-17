package model;

import java.time.LocalDateTime;

public class Belirti {
    private int id;
    private int hastaId;
    private LocalDateTime tarihZaman;
    private double seviye;
    private String belirtiler;

    // Getters ve Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHastaId() {
        return hastaId;
    }

    public void setHastaId(int hastaId) {
        this.hastaId = hastaId;
    }

    public LocalDateTime getTarihZaman() {
        return tarihZaman;
    }

    public void setTarihZaman(LocalDateTime tarihZaman) {
        this.tarihZaman = tarihZaman;
    }

    public double getSeviye() {
        return seviye;
    }

    public void setSeviye(double seviye) {
        this.seviye = seviye;
    }

    public String getBelirtiler() {
        return belirtiler;
    }


    private String belirti;


    public void setBelirti(String belirti) {
        this.belirti = belirti;
    }

}
