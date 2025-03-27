# Bismillahirrahmanirrahim

## Janji
Saya Muhammad Naufal Arbanin dengan NIM 2310850 mengerjakan soal Tugas Praktikum 5 dalam mata kuliah Desain Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Penjelasan Kodingan / Program

#### 1. Program Dimulai (Menu.java)

- Program dijalankan melalui method main() di kelas Menu.

- Membuat window GUI menggunakan JFrame.

- Menyiapkan komponen-komponen UI seperti:

   - Form input (JTextField, JComboBox).

   - Tabel (JTable) untuk menampilkan data mahasiswa.

   - Tombol (JButton) seperti Add/Update, Delete, Delete All, dan Cancel.

- Membuat koneksi ke database melalui objek Database.

#### 2. Menampilkan Data Mahasiswa di Tabel
- Saat program dijalankan:

  - Memanggil method setTable(), yang mengambil data dari database (mahasiswa table).

  - Hasil query dari database disimpan dalam DefaultTableModel, lalu ditampilkan di JTable.

#### 3. Menambahkan Mahasiswa (insertData)
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

#### 4. Mengubah Data Mahasiswa (updateData)
- User memilih satu baris di tabel mahasiswa.

- Data mahasiswa diisi kembali ke form input.

- User mengedit lalu klik Update.

- Validasi dilakukan untuk memastikan data tidak kosong.

- Query UPDATE dijalankan untuk mengubah data di database:

        UPDATE mahasiswa 
        SET nama = ?, jenis_kelamin = ?, angkatan = ? 
        WHERE nim = ?;

- Tabel diperbarui setelah perubahan dilakukan.

#### 5. Menghapus Satu Data Mahasiswa (deleteData)
- User memilih satu baris di tabel.

- Klik Delete, lalu muncul konfirmasi dialog.

- Jika user menekan Yes, maka semua data di dalamnya akan terhapus.

- Tabel diperbarui untuk menghapus data yang telah dihapus.

#### 6. Menghapus Semua Data Mahasiswa (Delete All)
- User menekan tombol "Delete All".

- Jika daftar mahasiswa tidak kosong, muncul konfirmasi dialog.

- Jika user menekan Yes:

  - Semua data dihapus dari database menggunakan query:

        DELETE FROM mahasiswa;

- Tabel diperbarui dan form dikosongkan.

#### 7. Membersihkan Form Input (clearForm)
- Saat user klik Cancel atau selesai operasi:

  - Semua input dikosongkan.

  - Tombol Delete disembunyikan.

  - Tombol Update berubah kembali menjadi Add.

  - selectedIndex direset ke -1.

## Dokumentasi

![Image](https://github.com/user-attachments/assets/3ec46d75-8a95-48da-b7ae-4757d4e12354)

![Image](https://github.com/user-attachments/assets/23e5b527-a7ba-4469-ab22-df2df3787fc6)

![Image](https://github.com/user-attachments/assets/2d2c2897-d9d9-4cfe-a30e-6af81a30e70f)

![Image](https://github.com/user-attachments/assets/116d3718-d91d-42ae-827e-e05c47e4d21a)

![Image](https://github.com/user-attachments/assets/0b136906-f9a0-4e47-ac5c-30f59efb160f)

![Image](https://github.com/user-attachments/assets/df00c040-5eb0-4165-a193-c300693c935e)

![Image](https://github.com/user-attachments/assets/aff7d7f7-9101-41eb-80f7-c131aae71def)

![Image](https://github.com/user-attachments/assets/8e764472-11f0-48cc-9168-8c7b6ede0eee)

![Image](https://github.com/user-attachments/assets/fcee5f42-527d-40e6-accf-d598b7edf519)

![Image](https://github.com/user-attachments/assets/9805d7c6-fab1-4847-9f24-ff819a724e0d)

![Image](https://github.com/user-attachments/assets/f819f110-2b96-4537-8d89-7221a99a3b49)

![Image](https://github.com/user-attachments/assets/1fa45933-6dda-4775-822d-3592dc284781)

![Image](https://github.com/user-attachments/assets/c3e24865-9c5c-41cc-80aa-877ba31240e6)

![Image](https://github.com/user-attachments/assets/46aeae6b-9f54-4c47-a9f8-194f0f257321)

