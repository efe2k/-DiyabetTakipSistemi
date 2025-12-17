    package model;

    import java.time.LocalDate;

    public class InsulinKaydi {
        private int id;
        private int hastaId;
        private LocalDate tarih;
        private double dozMiktari;
        private boolean uygulandi;

        // Getter & Setter
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public int getHastaId() { return hastaId; }
        public void setHastaId(int hastaId) { this.hastaId = hastaId; }

        public LocalDate getTarih() { return tarih; }
        public void setTarih(LocalDate tarih) { this.tarih = tarih; }

        public double getDozMiktari() { return dozMiktari; }
        public void setDozMiktari(double dozMiktari) { this.dozMiktari = dozMiktari; }

        public boolean isUygulandi() { return uygulandi; }
        public void setUygulandi(boolean uygulandi) { this.uygulandi = uygulandi; }
    }
