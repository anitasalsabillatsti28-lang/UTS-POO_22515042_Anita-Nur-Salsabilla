package tokokelontong;

import java.util.ArrayList;

/** Mengelola data produk, pelanggan, dan antrean transaksi toko. */
public class TokoKelontong {
    private String namaToko;
    private String alamat;
    private ArrayList<Produk> daftarProduk;
    private ArrayList<Pelanggan> daftarPelanggan;
    private AntrianTransaksi antrean;

    public TokoKelontong() { this("Toko Kelontong Mini", "Alamat belum diisi", 10); }

    public TokoKelontong(String namaToko, String alamat, int kapasitasAntrean) {
        if (namaToko == null || namaToko.trim().isEmpty()) throw new IllegalArgumentException("Nama toko wajib diisi.");
        this.namaToko = namaToko;
        this.alamat = (alamat == null) ? "-" : alamat;
        this.daftarProduk = new ArrayList<>();
        this.daftarPelanggan = new ArrayList<>();
        this.antrean = new AntrianTransaksi(kapasitasAntrean);
    }

    public String getNamaToko() { return namaToko; }
    public String getAlamat() { return alamat; }
    public AntrianTransaksi getAntrean() { return antrean; }

    public void tambahProduk(Produk produk) {
        if (produk == null) throw new IllegalArgumentException("Produk tidak boleh null.");
        if (cariProduk(produk.getKode()) != null) throw new IllegalArgumentException("Kode produk sudah terdaftar.");
        daftarProduk.add(produk);
    }
    public void tambahPelanggan(Pelanggan pelanggan) {
        if (pelanggan == null) throw new IllegalArgumentException("Pelanggan tidak boleh null.");
        if (cariPelanggan(pelanggan.getIdPelanggan()) != null) throw new IllegalArgumentException("ID pelanggan sudah terdaftar.");
        daftarPelanggan.add(pelanggan);
    }
    public Produk cariProduk(String kunci) {
        for (Produk p : daftarProduk) if (p.getKode().equalsIgnoreCase(kunci) || p.getNama().equalsIgnoreCase(kunci)) return p;
        return null;
    }
    public Pelanggan cariPelanggan(String id) {
        for (Pelanggan p : daftarPelanggan) if (p.getIdPelanggan().equalsIgnoreCase(id)) return p;
        return null;
    }
    public boolean buatTransaksi(Transaksi transaksi) {
        if (transaksi == null) throw new IllegalArgumentException("Transaksi tidak boleh null.");
        if (transaksi.getProduk().getStok() < transaksi.getJumlah()) throw new IllegalArgumentException("Stok produk tidak mencukupi.");
        if (antrean.isFull()) return false;
        if (!transaksi.getProduk().kurangiStok(transaksi.getJumlah())) return false;
        if (!antrean.enqueue(transaksi)) {
            transaksi.getProduk().tambahStok(transaksi.getJumlah());
            return false;
        }
        return true;
    }
    public Transaksi prosesTransaksiBerikutnya() {
        Transaksi t = antrean.dequeue();
        if (t != null) t.ubahStatus("Selesai");
        return t;
    }
    public String laporan() {
        int totalStok = 0;
        for (Produk p : daftarProduk) totalStok += p.getStok();
        return "Jumlah produk: " + daftarProduk.size()
                + "\nJumlah pelanggan: " + daftarPelanggan.size()
                + "\nAntrean transaksi: " + antrean.size()
                + "\nTotal unit stok tersisa: " + totalStok;
    }
    public String tampilkanProduk() {
        StringBuilder sb = new StringBuilder();
        for (Produk p : daftarProduk) sb.append(p.tampilkanInfo()).append(System.lineSeparator());
        return sb.length() == 0 ? "Belum ada produk." : sb.toString();
    }
    public String tampilkanPelanggan() {
        StringBuilder sb = new StringBuilder();
        for (Pelanggan p : daftarPelanggan) sb.append(p.tampilkanInfo()).append(System.lineSeparator());
        return sb.length() == 0 ? "Belum ada pelanggan." : sb.toString();
    }
}