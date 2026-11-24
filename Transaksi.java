package tokokelontong;

/** Menghubungkan pelanggan dan produk dalam satu pembelian. */
public class Transaksi {
    private String kodeTransaksi;
    private Pelanggan pelanggan;
    private Produk produk;
    private int jumlah;
    private double diskonPersen;
    private double total;
    private String status;

    public Transaksi() {
        this("T000", new Pelanggan(), new Produk(), 1, 0);
    }

    public Transaksi(String kodeTransaksi, Pelanggan pelanggan, Produk produk, int jumlah, double diskonPersen) {
        if (pelanggan == null || produk == null) throw new IllegalArgumentException("Pelanggan dan produk wajib diisi.");
        if (jumlah <= 0) throw new IllegalArgumentException("Jumlah pembelian harus lebih dari 0.");
        if (diskonPersen < 0 || diskonPersen > 50) throw new IllegalArgumentException("Diskon harus 0 sampai 50 persen.");
        this.kodeTransaksi = kodeTransaksi;
        this.pelanggan = pelanggan;
        this.produk = produk;
        this.jumlah = jumlah;
        this.diskonPersen = diskonPersen;
        this.total = hitungTotal();
        this.status = "Menunggu";
    }

    public String getKodeTransaksi() { return kodeTransaksi; }
    public Pelanggan getPelanggan() { return pelanggan; }
    public Produk getProduk() { return produk; }
    public int getJumlah() { return jumlah; }
    public double getDiskonPersen() { return diskonPersen; }
    public double getTotal() { return total; }
    public String getStatus() { return status; }

    public void setDiskonPersen(double diskonPersen) {
        if (diskonPersen < 0 || diskonPersen > 50) throw new IllegalArgumentException("Diskon harus 0 sampai 50 persen.");
        this.diskonPersen = diskonPersen;
        this.total = hitungTotal();
    }
    public double hitungSubtotal() { return produk.getHarga() * jumlah; }
    public double hitungTotal() { return hitungSubtotal() * (1 - diskonPersen / 100.0); }
    public void ubahStatus(String status) {
        if (status == null || status.trim().isEmpty()) throw new IllegalArgumentException("Status tidak boleh kosong.");
        this.status = status;
    }
    public String tampilkanInfo() {
        return String.format("%s | %s | %s x%d | Diskon %.0f%% | Total Rp%.0f | %s",
                kodeTransaksi, pelanggan.getNama(), produk.getNama(), jumlah, diskonPersen, total, status);
    }
}