package tokokelontong;

import java.util.ArrayList;

public class TokoKelontong {
    private String namaToko;
    private String alamat;
    private ArrayList<Produk> daftarProduk;
    private ArrayList<Pelanggan> daftarPelanggan;
    private AntrianTransaksi antrean;
    
    public TokoKelontong() {
        this("Toko Kelontong Mini",
                "Alamat belum diisi", 5);
    }
    
    public TokoKelontong(String namaToko,
                         String alamat,
                         int kapasitasAntrean) {

        if (namaToko == null || namaToko.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Nama toko tidak boleh kosong.");
        }

        if (alamat == null || alamat.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Alamat toko tidak boleh kosong.");
        }

        this.namaToko = namaToko.trim();
        this.alamat = alamat.trim();

        this.daftarProduk = new ArrayList<Produk>();
        this.daftarPelanggan = new ArrayList<Pelanggan>();

        this.antrean = new AntrianTransaksi(kapasitasAntrean);
    }

    public String getNamaToko() {
        return this.namaToko;
    }

    public String getAlamat() {
        return this.alamat;
    }

    public int getJumlahProduk() {
        return this.daftarProduk.size();
    }

    public int getJumlahPelanggan() {
        return this.daftarPelanggan.size();
    }

    public int getJumlahAntrean() {
        return this.antrean.size();
    }
    
    public void tambahProduk(Produk produk) {
        if (produk == null) {
            throw new IllegalArgumentException(
                    "Produk tidak boleh kosong.");
        }

        if (this.cariProduk(produk.getKode()) != null) {
            throw new IllegalArgumentException(
                    "Kode produk sudah terdaftar.");
        }

        this.daftarProduk.add(produk);
    }
    
    public void tambahPelanggan(Pelanggan pelanggan) {
        if (pelanggan == null) {
            throw new IllegalArgumentException(
                    "Pelanggan tidak boleh kosong.");
        }

        if (this.cariPelanggan(
                pelanggan.getIdPelanggan()) != null) {
            throw new IllegalArgumentException(
                    "ID pelanggan sudah terdaftar.");
        }

        this.daftarPelanggan.add(pelanggan);
    }
    
    public Produk cariProduk(String kunci) {
        if (kunci == null || kunci.trim().isEmpty()) {
            return null;
        }

        for (Produk produk : this.daftarProduk) {
            if (produk.getKode().equalsIgnoreCase(kunci.trim())
                    || produk.getNama().equalsIgnoreCase(kunci.trim())) {
                return produk;
            }
        }

        return null;
    }
    
    public Pelanggan cariPelanggan(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }

        for (Pelanggan pelanggan : this.daftarPelanggan) {
            if (pelanggan.getIdPelanggan()
                    .equalsIgnoreCase(id.trim())) {
                return pelanggan;
            }
        }

        return null;
    }
    
    public boolean buatTransaksi(Transaksi transaksi) {
        if (transaksi == null) {
            throw new IllegalArgumentException(
                    "Transaksi tidak boleh kosong.");
        }

        if (transaksi.getProduk().getStok()
                < transaksi.getJumlah()) {
            throw new IllegalArgumentException(
                    "Stok produk tidak mencukupi.");
        }

        if (this.antrean.isFull()) {
            return false;
        }

        boolean stokBerhasil =
                transaksi.getProduk().kurangiStok(
                        transaksi.getJumlah());

        if (!stokBerhasil) {
            return false;
        }

        boolean antreanBerhasil =
                this.antrean.enqueue(transaksi);

        if (!antreanBerhasil) {
            transaksi.getProduk().tambahStok(
                    transaksi.getJumlah());
            return false;
        }

        return true;
    }
    
    public Transaksi prosesTransaksiBerikutnya() {
        Transaksi transaksi = this.antrean.dequeue();

        if (transaksi != null) {
            transaksi.ubahStatus("Selesai");
        }

        return transaksi;
    }
    
    public String tampilkanProduk() {
        if (this.daftarProduk.isEmpty()) {
            return "Belum ada produk yang terdaftar.";
        }

        StringBuilder hasil = new StringBuilder();

        for (Produk produk : this.daftarProduk) {
            hasil.append(produk.tampilkanInfo())
                    .append(System.lineSeparator());
        }

        return hasil.toString();
    }
    
    public String tampilkanPelanggan() {
        if (this.daftarPelanggan.isEmpty()) {
            return "Belum ada pelanggan yang terdaftar.";
        }

        StringBuilder hasil = new StringBuilder();

        for (Pelanggan pelanggan : this.daftarPelanggan) {
            hasil.append(pelanggan.tampilkanInfo())
                    .append(System.lineSeparator());
        }

        return hasil.toString();
    }
    
    public String tampilkanAntrean() {
        return this.antrean.tampilkanIsi();
    }
    
    public String laporan() {
        int totalStok = 0;
        double nilaiStok = 0.0;

        for (Produk produk : this.daftarProduk) {
            totalStok += produk.getStok();
            nilaiStok += produk.getStok() * produk.getHarga();
        }

        return "===== LAPORAN TOKO ====="
                + "\nNama toko: " + this.namaToko
                + "\nAlamat: " + this.alamat
                + "\nJumlah jenis produk: " + this.daftarProduk.size()
                + "\nJumlah pelanggan: " + this.daftarPelanggan.size()
                + "\nTransaksi dalam antrean: " + this.antrean.size()
                + "\nTotal unit stok tersisa: " + totalStok
                + String.format(
                "%nNilai persediaan: Rp%.0f", nilaiStok);
    }
}
