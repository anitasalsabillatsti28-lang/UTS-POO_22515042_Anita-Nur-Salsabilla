package tokokelontong;

import java.util.Scanner;

/** Menu console untuk Sistem Manajemen Toko Kelontong Mini. */
public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final TokoKelontong toko = new TokoKelontong("Toko Kelontong Zephyr", "Kendari", 5);
    private static int nomorTransaksi = 1;

    public static void main(String[] args) {
        isiDataAwal();
        boolean jalan = true;
        while (jalan) {
            System.out.println("\n=== " + toko.getNamaToko() + " ===");
            System.out.println("1. Tampilkan produk");
            System.out.println("2. Tambah produk");
            System.out.println("3. Tampilkan pelanggan");
            System.out.println("4. Tambah pelanggan");
            System.out.println("5. Buat transaksi (masuk antrean)");
            System.out.println("6. Proses transaksi berikutnya");
            System.out.println("7. Lihat isi antrean");
            System.out.println("8. Cari produk");
            System.out.println("9. Laporan sederhana");
            System.out.println("0. Keluar");
            int pilih = bacaInt("Pilih menu: ");
            try {
                switch (pilih) {
                    case 1: System.out.println(toko.tampilkanProduk()); break;
                    case 2: tambahProduk(); break;
                    case 3: System.out.println(toko.tampilkanPelanggan()); break;
                    case 4: tambahPelanggan(); break;
                    case 5: buatTransaksi(); break;
                    case 6:
                        Transaksi selesai = toko.prosesTransaksiBerikutnya();
                        System.out.println(selesai == null ? "Antrean kosong." : "Transaksi diproses: " + selesai.tampilkanInfo());
                        break;
                    case 7: System.out.println(toko.getAntrean().tampilkanIsi()); break;
                    case 8:
                        System.out.print("Masukkan kode/nama produk: ");
                        Produk p = toko.cariProduk(input.nextLine());
                        System.out.println(p == null ? "Produk tidak ditemukan." : p.tampilkanInfo());
                        break;
                    case 9: System.out.println(toko.laporan()); break;
                    case 0: jalan = false; System.out.println("Program selesai. Terima kasih."); break;
                    default: System.out.println("Menu tidak tersedia.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Gagal: " + ex.getMessage());
            }
        }
    }

    private static void isiDataAwal() {
        toko.tambahProduk(new Produk("P001", "Beras 5kg", 20, 72000, "Sembako"));
        toko.tambahProduk(new Produk("P002", "Minyak Goreng", 15, 18000, "Sembako"));
        toko.tambahProduk(new Produk("P003", "Gula 1kg", 12, 17000, "Sembako"));
        toko.tambahPelanggan(new Pelanggan("C001", "Andi", "081234567890", 0));
        toko.tambahPelanggan(new Pelanggan("C002", "Siti", "082345678901", 0));
    }
    private static void tambahProduk() {
        System.out.print("Kode: "); String kode = input.nextLine();
        System.out.print("Nama: "); String nama = input.nextLine();
        int stok = bacaInt("Stok: ");
        double harga = bacaDouble("Harga: ");
        System.out.print("Kategori: "); String kategori = input.nextLine();
        toko.tambahProduk(new Produk(kode, nama, stok, harga, kategori));
        System.out.println("Produk berhasil ditambahkan.");
    }
    private static void tambahPelanggan() {
        System.out.print("ID pelanggan: "); String id = input.nextLine();
        System.out.print("Nama: "); String nama = input.nextLine();
        System.out.print("No. HP: "); String hp = input.nextLine();
        toko.tambahPelanggan(new Pelanggan(id, nama, hp, 0));
        System.out.println("Pelanggan berhasil ditambahkan.");
    }
    private static void buatTransaksi() {
        System.out.print("ID pelanggan: "); String id = input.nextLine();
        Pelanggan c = toko.cariPelanggan(id);
        if (c == null) { System.out.println("Pelanggan tidak ditemukan."); return; }
        System.out.print("Kode produk: "); String kode = input.nextLine();
        Produk p = toko.cariProduk(kode);
        if (p == null) { System.out.println("Produk tidak ditemukan."); return; }
        int jumlah = bacaInt("Jumlah: ");
        double diskon = bacaDouble("Diskon (0-50): ");
        Transaksi t = new Transaksi(String.format("T%03d", nomorTransaksi++), c, p, jumlah, diskon);
        if (toko.buatTransaksi(t)) System.out.println("Transaksi masuk antrean: " + t.tampilkanInfo());
        else System.out.println("Antrean penuh, transaksi tidak ditambahkan.");
    }
    private static int bacaInt(String pesan) {
        while (true) {
            System.out.print(pesan);
            try { return Integer.parseInt(input.nextLine().trim()); }
            catch (NumberFormatException ex) { System.out.println("Input harus berupa bilangan bulat."); }
        }
    }
    private static double bacaDouble(String pesan) {
        while (true) {
            System.out.print(pesan);
            try { return Double.parseDouble(input.nextLine().trim()); }
            catch (NumberFormatException ex) { System.out.println("Input harus berupa angka."); }
        }
    }
}