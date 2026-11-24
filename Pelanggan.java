package tokokelontong;

public class Pelanggan {
    private String idPelanggan;
    private String nama;
    private String noHp;
    private int poin;
    
    public Pelanggan() {
        this("C000", "Pelanggan Umum", "-", 0);
    }
    
    public Pelanggan(String idPelanggan, String nama,
                     String noHp, int poin) {
        if (idPelanggan == null ||
                idPelanggan.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "ID pelanggan tidak boleh kosong.");
        }

        this.idPelanggan = idPelanggan.trim();
        setNama(nama);
        setNoHp(noHp);
        setPoin(poin);
    }

    public String getIdPelanggan() {
        return this.idPelanggan;
    }

    public String getNama() {
        return this.nama;
    }

    public String getNoHp() {
        return this.noHp;
    }

    public int getPoin() {
        return this.poin;
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Nama pelanggan tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public void setNoHp(String noHp) {
        if (noHp == null || noHp.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Nomor HP tidak boleh kosong.");
        }
        this.noHp = noHp.trim();
    }

    public void setPoin(int poin) {
        if (poin < 0) {
            throw new IllegalArgumentException(
                "Poin tidak boleh negatif.");
        }
        this.poin = poin;
    }
    
    public void tambahPoin(int nilai) {
        if (nilai < 0) {
            throw new IllegalArgumentException(
                "Penambahan poin tidak boleh negatif.");
        }
        this.poin += nilai;
    }
    
    public boolean gunakanPoin(int nilai) {
        if (nilai <= 0 || nilai > this.poin) {
            return false;
        }

        this.poin -= nilai;
        return true;
    }
    
    public String tampilkanInfo() {
        return String.format(
            "%s | %s | No. HP: %s | Poin: %d",
            this.idPelanggan, this.nama,
            this.noHp, this.poin
        );
    }
}
