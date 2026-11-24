package tokokelontong;

public class AntrianTransaksi {
    private Transaksi[] elemen;
    private int front;
    private int rear;
    private int jumlah;
    private final int kapasitas;

    public AntrianTransaksi() { this(10); }

    public AntrianTransaksi(int kapasitas) {
        if (kapasitas <= 0) throw new IllegalArgumentException("Kapasitas antrean harus lebih dari 0.");
        this.kapasitas = kapasitas;
        this.elemen = new Transaksi[kapasitas];
        this.front = 0;
        this.rear = -1;
        this.jumlah = 0;
    }

    public boolean enqueue(Transaksi transaksi) {
        if (transaksi == null) throw new IllegalArgumentException("Transaksi tidak boleh null.");
        if (isFull()) return false;
        rear = (rear + 1) % kapasitas;
        elemen[rear] = transaksi;
        jumlah++;
        return true;
    }
    public Transaksi dequeue() {
        if (isEmpty()) return null;
        Transaksi hasil = elemen[front];
        elemen[front] = null;
        front = (front + 1) % kapasitas;
        jumlah--;
        if (jumlah == 0) { front = 0; rear = -1; }
        return hasil;
    }
    public Transaksi front() { return isEmpty() ? null : elemen[front]; }
    public boolean isEmpty() { return jumlah == 0; }
    public boolean isFull() { return jumlah == kapasitas; }
    public int size() { return jumlah; }
    public int getKapasitas() { return kapasitas; }

    public String tampilkanIsi() {
        if (isEmpty()) return "Antrean masih kosong.";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jumlah; i++) {
            int index = (front + i) % kapasitas;
            sb.append(i + 1).append(". ").append(elemen[index].tampilkanInfo()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
