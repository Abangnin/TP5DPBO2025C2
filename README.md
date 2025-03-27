# Bismillahirrahmanirrahim

## Janji
Saya Muhammad Naufal Arbanin dengan NIM 2310850 mengerjakan soal Tugas Praktikum 5 dalam mata kuliah Desain Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Penjelasan Kodingan / Program

1. Program Dimulai (Menu.java)

- Program dijalankan melalui method main() di kelas Menu.

- Membuat window GUI menggunakan JFrame.

- Menyiapkan komponen-komponen UI seperti:

   - Form input (JTextField, JComboBox).

   - Tabel (JTable) untuk menampilkan data mahasiswa.

   - Tombol (JButton) seperti Add/Update, Delete, Delete All, dan Cancel.

- Membuat koneksi ke database melalui objek Database.

2. Menampilkan Data Mahasiswa di Tabel
- Saat program dijalankan:

  - Memanggil method setTable(), yang mengambil data dari database (mahasiswa table).

  - Hasil query dari database disimpan dalam DefaultTableModel, lalu ditampilkan di JTable.

3. Menambahkan Mahasiswa (insertData)
- User mengisi form input:

  - NIM

  - Nama

  - Jenis Kelamin

  - Angkatan

- Validasi dilakukan:

  - Pastikan tidak ada field yang kosong.

  - Cek apakah NIM sudah ada di database.

- Jika valid, data dimasukkan ke database dengan query:

        INSERT INTO mahasiswa VALUES (null, 'nim', 'nama', 'jenis_kelamin', 'angkatan');

- Tabel diperbarui untuk menampilkan data baru.

4. Mengubah Data Mahasiswa (updateData)
- User memilih satu baris di tabel mahasiswa.

- Data mahasiswa diisi kembali ke form input.

- User mengedit lalu klik Update.

- Validasi dilakukan untuk memastikan data tidak kosong.

- Query UPDATE dijalankan untuk mengubah data di database:

        UPDATE mahasiswa 
        SET nama = ?, jenis_kelamin = ?, angkatan = ? 
        WHERE nim = ?;

- Tabel diperbarui setelah perubahan dilakukan.

5. Menghapus Satu Data Mahasiswa (deleteData)
- User memilih satu baris di tabel.

- Klik Delete, lalu muncul konfirmasi dialog.

- Jika user menekan Yes, maka:

  - Query DELETE dijalankan untuk menghapus data:

        DELETE FROM mahasiswa WHERE nim = 'nim';

- Tabel diperbarui untuk menghapus data yang telah dihapus.

6. Menghapus Semua Data Mahasiswa (Delete All)
- User menekan tombol "Delete All".

- Jika daftar mahasiswa tidak kosong, muncul konfirmasi dialog.

- Jika user menekan Yes:

  - Semua data dihapus dari database menggunakan query:

        DELETE FROM mahasiswa;

- Tabel diperbarui dan form dikosongkan.

7. Membersihkan Form Input (clearForm)
- Saat user klik Cancel atau selesai operasi:

  - Semua input dikosongkan.

  - Tombol Delete disembunyikan.

  - Tombol Update berubah kembali menjadi Add.

  - selectedIndex direset ke -1.

## Dokumentasi

  
