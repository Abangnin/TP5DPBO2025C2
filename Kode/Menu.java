import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Menu extends JFrame{
    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();

        // atur ukuran window
        window.setSize(500, 600);

        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);

        // isi window
        window.setContentPane(window.mainPanel);

        // ubah warna background
        window.getContentPane().setBackground(Color.white);

        // tampilkan window
        window.setVisible(true);

        // agar program ikut berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;

    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;

    private Database database;

    private JPanel mainPanel;

    private JTextField nimField;

    private JTextField namaField;

    private JTable mahasiswaTable;

    private JButton addUpdateButton;

    private JButton cancelButton;

    private JComboBox jenisKelaminComboBox;

    private JButton deleteButton;

    private JLabel titleLabel;

    private JLabel nimLabel;

    private JLabel namaLabel;

    private JLabel jenisKelaminLabel;

    private JLabel angkatanLabel;

    private JComboBox angkatanComboBox;

    private JButton deleteAllButton;

    public Menu(){
        // inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();

        // buat objek database
        database = new Database();

//        // isi listMahasiswa
//        populateList();

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] jenisKelaminData = {"", "Laki-Laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel(jenisKelaminData));

        // untuk angkatan
        String[] tahunAngkatan = {"", "2020", "2021", "2022", "2023", "2024"};
        angkatanComboBox.setModel(new DefaultComboBoxModel<>(tahunAngkatan));

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1){
                    insertData();
                }else{
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0){
                    // ini buat munculin konfirmasi prompt
                    int konfirmasi = JOptionPane.showConfirmDialog(null, "Mau Hapus Data Ini?", "Konfirmasi Hapus Data", JOptionPane.YES_NO_OPTION);
                    if (konfirmasi == JOptionPane.YES_OPTION) {
                        deleteData();
                    }
                }
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // simpan value textfield dan combo box
                String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString();
                String selectedAngkatan = mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString();

                // ubah isi textfield dan combo box
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);
                angkatanComboBox.setSelectedItem(selectedAngkatan);

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });

        // Saat tombol Delete All ditekan
        deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mahasiswaTable.getRowCount() > 0) { // Cek apakah tabel berisi data
                    int konfirmasi = JOptionPane.showConfirmDialog(
                            null, "Apakah yakin ingin menghapus semua data ini?",
                            "Konfirmasi Hapus Semua", JOptionPane.YES_NO_OPTION
                    );

                    if (konfirmasi == JOptionPane.YES_OPTION) {
                        // Hapus semua data dari database
                        String sql = "DELETE FROM mahasiswa";
                        database.insertUpdateDeleteQuery(sql);

                        // Perbarui tabel setelah penghapusan
                        mahasiswaTable.setModel(setTable());

                        // Bersihkan form
                        clearForm();

                        // Feedback ke user
                        JOptionPane.showMessageDialog(null, "Semua data berhasil dihapus!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Tidak ada data untuk dihapus!", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Angkatan"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(null, column);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");

            int i = 0;
            while (resultSet.next()){
                Object[] row = new Object[5];

                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("angkatan");

                temp.addRow(row);
                i++;
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

          // isi tabel dengan listMahasiswa
//        for (int i = 0; i < listMahasiswa.size(); i++){
//            Object[] row = new Object[5];
//            row[0] = i + 1;
//            row[1] = listMahasiswa.get(i).getNim();
//            row[2] = listMahasiswa.get(i).getNama();
//            row[3] = listMahasiswa.get(i).getJenisKelamin();
//            row[4] = listMahasiswa.get(i).getAngkatan();
//
//            temp.addRow(row);
//        }

        return temp; // return juga harus diganti
    }

    public void insertData() {
        // ambil value dari textfield dan combobox
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String angkatan = angkatanComboBox.getSelectedItem().toString();

        // Validasi input kosong
        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || angkatan.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cek apakah NIM sudah ada di database
        ResultSet rs = database.selectQuery("SELECT * FROM mahasiswa WHERE nim = '" + nim + "'");
        try {
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "NIM sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO mahasiswa VALUES (null, '" + nim + "', '" + nama + "', '" + jenisKelamin + "', '" + angkatan + "');";
        database.insertUpdateDeleteQuery(sql);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Insert Berhasil!");
        JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan");

    }

    public void updateData() {
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang ingin diubah!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ambil data dari form
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String angkatan = angkatanComboBox.getSelectedItem().toString();

        // Validasi input kosong
        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || angkatan.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Query update
        String sql = "UPDATE mahasiswa SET nama = ?, jenis_kelamin = ?, angkatan = ? WHERE nim = ?";

        try {
            Connection conn = Database.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Koneksi database gagal!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nama);
            pstmt.setString(2, jenisKelamin);
            pstmt.setString(3, angkatan);
            pstmt.setString(4, nim);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                mahasiswaTable.setModel(setTable()); // Pastikan setTable() benar
                mahasiswaTable.repaint(); // Paksa refresh tabel
                clearForm();
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            } else {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan atau gagal diupdate!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengupdate data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData() {
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ambil NIM dari tabel
        String nim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();

        // Konfirmasi penghapusan
        int konfirmasi = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            // Query delete
            String sql = "DELETE FROM mahasiswa WHERE nim = '" + nim + "'";
            database.insertUpdateDeleteQuery(sql);

            // Perbarui tabel
            mahasiswaTable.setModel(setTable());

            // Bersihkan form
            clearForm();

            // feedback
            System.out.println("Delete Berhasil!");
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
        }
    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");
        angkatanComboBox.setSelectedItem("");

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

//    private void populateList() {
//        listMahasiswa.add(new Mahasiswa("2203999", "Amelia Zalfa Julianti", "Perempuan", "2022"));
//        listMahasiswa.add(new Mahasiswa("2102292", "Muhammad Iqbal Fadhilah", "Laki-laki", "2021"));
//        listMahasiswa.add(new Mahasiswa("2202346", "Muhammad Rifky Afandi", "Laki-laki", "2022"));
//        listMahasiswa.add(new Mahasiswa("2310239", "Muhammad Hanif Abdillah", "Laki-laki", "2023"));
//        listMahasiswa.add(new Mahasiswa("2402046", "Nurainun", "Perempuan", "2024"));
//        listMahasiswa.add(new Mahasiswa("2005101", "Kelvin Julian Putra", "Laki-laki", "2020"));

//        listMahasiswa.add(new Mahasiswa("2200163", "Rifanny Lysara Annastasya", "Perempuan"));
//        listMahasiswa.add(new Mahasiswa("2202869", "Revana Faliha Salma", "Perempuan"));
//        listMahasiswa.add(new Mahasiswa("2209489", "Rakha Dhifiargo Hariadi", "Laki-laki"));
//        listMahasiswa.add(new Mahasiswa("2203142", "Roshan Syalwan Nurilham", "Laki-laki"));
//        listMahasiswa.add(new Mahasiswa("2200311", "Raden Rahman Ismail", "Laki-laki"));
//        listMahasiswa.add(new Mahasiswa("2200978", "Ratu Syahirah Khairunnisa", "Perempuan"));
//        listMahasiswa.add(new Mahasiswa("2204509", "Muhammad Fahreza Fauzan", "Laki-laki"));
//        listMahasiswa.add(new Mahasiswa("2205027", "Muhammad Rizki Revandi", "Laki-laki"));
//        listMahasiswa.add(new Mahasiswa("2203484", "Arya Aydin Margono", "Laki-laki"));
//        listMahasiswa.add(new Mahasiswa("2200481", "Marvel Ravindra Dioputra", "Laki-laki"));
//        listMahasiswa.add(new Mahasiswa("2209889", "Muhammad Fadlul Hafiizh", "Laki-laki"));
//        listMahasiswa.add(new Mahasiswa("2206697", "Rifa Sania", "Perempuan"));
//        listMahasiswa.add(new Mahasiswa("2207260", "Imam Chalish Rafidhul Haque", "Laki-laki"));
//        listMahasiswa.add(new Mahasiswa("2204343", "Meiva Labibah Putri", "Perempuan"));
//    }
}
