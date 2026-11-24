package tokokelontong;

public class Transaksi {
    private String kodeTransaksi;
    private Pelanggan pelanggan;
    private Produk produk;
    private int jumlah;
    private double diskonPersen;
    private double total;
    private String status;
    
    public Transaksi() {
        this("T000", new Pelanggan(),
                new Produk(), 1, 0.0);
    }
    
    public Transaksi(String kodeTransaksi,
                     Pelanggan pelanggan,
                     Produk produk,
                     int jumlah,
                     double diskonPersen) {

        if (kodeTransaksi == null ||
                kodeTransaksi.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Kode transaksi tidak boleh kosong.");
        }

        if (pelanggan == null) {
            throw new IllegalArgumentException(
                    "Pelanggan harus dipilih.");
        }

        if (produk == null) {
            throw new IllegalArgumentException(
                    "Produk harus dipilih.");
        }

        if (jumlah <= 0) {
            throw new IllegalArgumentException(
                    "Jumlah pembelian harus lebih dari 0.");
        }

        validasiDiskon(diskonPersen);

        this.kodeTransaksi = kodeTransaksi.trim();
        this.pelanggan = pelanggan;
        this.produk = produk;
        this.jumlah = jumlah;
        this.diskonPersen = diskonPersen;
        this.total = hitungTotal();
        this.status = "Menunggu";
    }

    private void validasiDiskon(double diskonPersen) {
        if (diskonPersen < 0 || diskonPersen > 50
                || Double.isNaN(diskonPersen)
                || Double.isInfinite(diskonPersen)) {
            throw new IllegalArgumentException(
                    "Diskon harus berada pada rentang 0 sampai 50 persen.");
        }
    }

    public String getKodeTransaksi() {
        return this.kodeTransaksi;
    }

    public Pelanggan getPelanggan() {
        return this.pelanggan;
    }

    public Produk getProduk() {
        return this.produk;
    }

    public int getJumlah() {
        return this.jumlah;
    }

    public double getDiskonPersen() {
        return this.diskonPersen;
    }

    public double getTotal() {
        return this.total;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setDiskonPersen(double diskonPersen) {
        validasiDiskon(diskonPersen);
        this.diskonPersen = diskonPersen;
        this.total = hitungTotal();
    }
    
    public double hitungSubtotal() {
        return this.produk.getHarga() * this.jumlah;
    }
    
    public double hitungTotal() {
        return this.hitungSubtotal()
                * (1.0 - this.diskonPersen / 100.0);
    }
    
    public void ubahStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Status transaksi tidak boleh kosong.");
        }
        this.status = status.trim();
    }
    
    public String tampilkanInfo() {
        return String.format(
                "%s | Pelanggan: %s | Produk: %s | Jumlah: %d"
                        + " | Diskon: %.1f%% | Total: Rp%.0f | Status: %s",
                this.kodeTransaksi,
                this.pelanggan.getNama(),
                this.produk.getNama(),
                this.jumlah,
                this.diskonPersen,
                this.total,
                this.status
        );
    }
}
