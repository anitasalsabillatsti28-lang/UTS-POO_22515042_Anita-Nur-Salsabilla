package tokokelontong;

/** Mewakili pelanggan toko. */
public class Pelanggan {
    private String idPelanggan;
    private String nama;
    private String noHp;
    private int poin;

    public Pelanggan() {
        this("C000", "Pelanggan Umum", "-", 0);
    }

    public Pelanggan(String idPelanggan, String nama, String noHp, int poin) {
        this.idPelanggan = idPelanggan;
        setNama(nama);
        setNoHp(noHp);
        setPoin(poin);
    }

    public String getIdPelanggan() { return idPelanggan; }
    public String getNama() { return nama; }
    public String getNoHp() { return noHp; }
    public int getPoin() { return poin; }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) throw new IllegalArgumentException("Nama pelanggan tidak boleh kosong.");
        this.nama = nama;
    }
    public void setNoHp(String noHp) {
        if (noHp == null || noHp.trim().isEmpty()) throw new IllegalArgumentException("Nomor HP tidak boleh kosong.");
        this.noHp = noHp;
    }
    public void setPoin(int poin) {
        if (poin < 0) throw new IllegalArgumentException("Poin tidak boleh negatif.");
        this.poin = poin;
    }

    public void tambahPoin(int nilai) {
        if (nilai < 0) throw new IllegalArgumentException("Nilai poin tidak boleh negatif.");
        this.poin += nilai;
    }
    public boolean hapusPoin(int nilai) {
        if (nilai <= 0 || nilai > poin) return false;
        poin -= nilai;
        return true;
    }
    public String tampilkanInfo() {
        return String.format("%s | %s | HP: %s | Poin: %d", idPelanggan, nama, noHp, poin);
    }
}