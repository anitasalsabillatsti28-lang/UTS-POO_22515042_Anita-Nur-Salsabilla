package tokokelontong;

/** Mewakili barang yang dijual di toko kelontong. */
public class Produk {
    private String kode;
    private String nama;
    private int stok;
    private double harga;
    private String kategori;

    public Produk() {
        this("P000", "Produk Baru", 0, 1.0, "Umum");
    }

    public Produk(String kode, String nama, int stok, double harga, String kategori) {
        this.kode = kode;
        this.nama = nama;
        setStok(stok);
        setHarga(harga);
        this.kategori = (kategori == null || kategori.trim().isEmpty()) ? "Umum" : kategori;
    }

    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public int getStok() { return stok; }
    public double getHarga() { return harga; }
    public String getKategori() { return kategori; }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) throw new IllegalArgumentException("Nama produk tidak boleh kosong.");
        this.nama = nama;
    }
    public void setStok(int stok) {
        if (stok < 0) throw new IllegalArgumentException("Stok tidak boleh negatif.");
        this.stok = stok;
    }
    public void setHarga(double harga) {
        if (harga <= 0) throw new IllegalArgumentException("Harga harus lebih dari 0.");
        this.harga = harga;
    }
    public void setKategori(String kategori) {
        if (kategori == null || kategori.trim().isEmpty()) throw new IllegalArgumentException("Kategori tidak boleh kosong.");
        this.kategori = kategori;
    }

    public boolean kurangiStok(int jumlah) {
        if (jumlah <= 0) throw new IllegalArgumentException("Jumlah pembelian harus lebih dari 0.");
        if (jumlah > this.stok) return false;
        this.stok -= jumlah;
        return true;
    }
    public void tambahStok(int jumlah) {
        if (jumlah <= 0) throw new IllegalArgumentException("Penambahan stok harus lebih dari 0.");
        this.stok += jumlah;
    }
    public String tampilkanInfo() {
        return String.format("%s | %s | Stok: %d | Harga: Rp%.0f | %s", kode, nama, stok, harga, kategori);
    }
}