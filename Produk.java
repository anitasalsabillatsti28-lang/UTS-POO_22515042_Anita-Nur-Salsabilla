package tokokelontong;

import java.util.Scanner;

public class Main {
    private static final Scanner INPUT = new Scanner(System.in);

    private static final TokoKelontong TOKO =
            new TokoKelontong(
                    "Toko Kelontong",
                    "Kendari",
                    5
            );

    private static int nomorTransaksi = 1;

    public static void main(String[] args) {
        isiDataAwal();

        boolean berjalan = true;

        while (berjalan) {
            tampilkanMenu();

            int pilihan = bacaInt("Pilih menu: ");

            try {
                switch (pilihan) {
                    case 1:
                        System.out.println(TOKO.tampilkanProduk());
                        break;

                    case 2:
                        tambahProduk();
                        break;

                    case 3:
                        System.out.println(TOKO.tampilkanPelanggan());
                        break;

                    case 4:
                        tambahPelanggan();
                        break;

                    case 5:
                        buatTransaksi();
                        break;

                    case 6:
                        prosesTransaksi();
                        break;

                    case 7:
                        System.out.println(TOKO.tampilkanAntrean());
                        break;

                    case 8:
                        cariProduk();
                        break;

                    case 9:
                        System.out.println(TOKO.laporan());
                        break;

                    case 0:
                        berjalan = false;
                        System.out.println(
                                "Program selesai. Terima kasih.");
                        break;

                    default:
                        System.out.println(
                                "Pilihan menu tidak tersedia.");
                }

            } catch (IllegalArgumentException ex) {
                System.out.println("Gagal: " + ex.getMessage());
            }
        }
    }
    
    private static void tampilkanMenu() {
        System.out.println();
        System.out.println("==================================");
        System.out.println("   " + TOKO.getNamaToko());
        System.out.println("==================================");

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
    }
    
    private static void isiDataAwal() {
        TOKO.tambahProduk(
                new Produk("P001", "Beras 5kg",
                        20, 72000, "Sembako"));

        TOKO.tambahProduk(
                new Produk("P002", "Minyak Goreng",
                        15, 18000, "Sembako"));

        TOKO.tambahProduk(
                new Produk("P003", "Gula 1kg",
                        12, 17000, "Sembako"));

        TOKO.tambahPelanggan(
                new Pelanggan("C001", "Andi",
                        "081234567890", 0));

        TOKO.tambahPelanggan(
                new Pelanggan("C002", "Siti",
                        "082345678901", 0));
    }
    
    private static void tambahProduk() {
        System.out.println("\n--- TAMBAH PRODUK ---");

        System.out.print("Kode produk: ");
        String kode = INPUT.nextLine();

        System.out.print("Nama produk: ");
        String nama = INPUT.nextLine();

        int stok = bacaInt("Stok awal: ");
        double harga = bacaDouble("Harga: ");

        System.out.print("Kategori: ");
        String kategori = INPUT.nextLine();

        Produk produk = new Produk(
                kode, nama, stok, harga, kategori);

        TOKO.tambahProduk(produk);

        System.out.println("Produk berhasil ditambahkan.");
    }
    
    private static void tambahPelanggan() {
        System.out.println("\n--- TAMBAH PELANGGAN ---");

        System.out.print("ID pelanggan: ");
        String id = INPUT.nextLine();

        System.out.print("Nama pelanggan: ");
        String nama = INPUT.nextLine();

        System.out.print("Nomor HP: ");
        String noHp = INPUT.nextLine();

        Pelanggan pelanggan = new Pelanggan(
                id, nama, noHp, 0);

        TOKO.tambahPelanggan(pelanggan);

        System.out.println("Pelanggan berhasil ditambahkan.");
    }
    
    private static void buatTransaksi() {
        System.out.println("\n--- BUAT TRANSAKSI ---");

        System.out.print("ID pelanggan: ");
        String idPelanggan = INPUT.nextLine();

        Pelanggan pelanggan =
                TOKO.cariPelanggan(idPelanggan);

        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        System.out.print("Kode produk: ");
        String kodeProduk = INPUT.nextLine();

        Produk produk = TOKO.cariProduk(kodeProduk);

        if (produk == null) {
            System.out.println("Produk tidak ditemukan.");
            return;
        }

        int jumlah = bacaInt("Jumlah pembelian: ");

        double diskon =
                bacaDouble("Diskon (0-50 persen): ");

        String kodeTransaksi =
                String.format("T%03d", nomorTransaksi);

        Transaksi transaksi = new Transaksi(
                kodeTransaksi,
                pelanggan,
                produk,
                jumlah,
                diskon
        );

        if (TOKO.buatTransaksi(transaksi)) {
            nomorTransaksi++;

            System.out.println(
                    "Transaksi berhasil masuk antrean.");

            System.out.println(transaksi.tampilkanInfo());

        } else {
            System.out.println(
                    "Antrean penuh. Transaksi tidak ditambahkan.");
        }
    }
    
    private static void prosesTransaksi() {
        Transaksi transaksi =
                TOKO.prosesTransaksiBerikutnya();

        if (transaksi == null) {
            System.out.println(
                    "Antrean kosong. Tidak ada transaksi untuk diproses.");

        } else {
            System.out.println(
                    "Transaksi berhasil diproses:");

            System.out.println(transaksi.tampilkanInfo());
        }
    }
    
    private static void cariProduk() {
        System.out.print("Masukkan kode atau nama produk: ");

        String kunci = INPUT.nextLine();

        Produk produk = TOKO.cariProduk(kunci);

        if (produk == null) {
            System.out.println("Produk tidak ditemukan.");

        } else {
            System.out.println("Produk ditemukan:");
            System.out.println(produk.tampilkanInfo());
        }
    }
    
    private static int bacaInt(String pesan) {
        while (true) {
            System.out.print(pesan);

            String teks = INPUT.nextLine();

            try {
                return Integer.parseInt(teks.trim());

            } catch (NumberFormatException ex) {
                System.out.println(
                        "Input harus berupa bilangan bulat.");
            }
        }
    }
    
    private static double bacaDouble(String pesan) {
        while (true) {
            System.out.print(pesan);

            String teks = INPUT.nextLine();

            try {
                return Double.parseDouble(teks.trim());

            } catch (NumberFormatException ex) {
                System.out.println(
                        "Input harus berupa angka. Contoh: 15000 atau 10.5");
            }
        }
    }
}
