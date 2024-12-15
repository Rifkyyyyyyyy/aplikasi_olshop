# View
### Auth view
![2024-12-15-103424_1366x768_scrot](https://github.com/user-attachments/assets/3309ed0d-af2f-4c1d-baf0-dac1a6919003)
### Admin Dashboard
![2024-12-15-103440_1366x768_scrot](https://github.com/user-attachments/assets/d2c5fc6a-0270-4f62-91cb-272b909aa3d3)
### Homepage 
![2024-12-15-103537_1366x768_scrot](https://github.com/user-attachments/assets/ce3e0a22-1971-4eb2-a3d6-0734c87d353b)



# Deskripsi Proyek

Proyek ini mengikuti arsitektur modular yang terstruktur dengan baik. Kode proyek diorganisir dalam berbagai folder berdasarkan fungsinya, yang memudahkan pemeliharaan dan pengembangan lebih lanjut. Aplikasi ini dibangun dengan prinsip pemisahan tanggung jawab yang jelas, sehingga memungkinkan pengembang untuk dengan mudah memahami dan mengembangkan aplikasi ini.

## Struktur Folder

Workspace ini terdiri dari beberapa folder utama, di antaranya:

- **`App.class`**: File bytecode hasil kompilasi dari `App.java`.
- **`App.java`**: File utama yang berfungsi sebagai titik masuk aplikasi.

Selain itu, terdapat beberapa folder yang menyimpan kode berdasarkan fungsinya, yaitu:

### `components`
Folder ini berisi komponen UI yang digunakan kembali di seluruh aplikasi. Tujuannya adalah untuk memisahkan bagian UI menjadi komponen-komponen kecil yang dapat dipelihara dengan mudah.

### `constant`
Berisi nilai-nilai konstan yang digunakan di seluruh aplikasi untuk menjaga konsistensi.
- **`helper`**: Kelas dan fungsi pembantu yang digunakan di berbagai bagian aplikasi.
- **`role`**: Menyimpan konstan terkait peran pengguna (misalnya, admin, user).
- **`style`**: Berisi konstan yang terkait dengan gaya aplikasi (seperti warna, ukuran font, dll).

### `core`
Berisi logika inti aplikasi, termasuk operasi bisnis utama yang diperlukan.
- **`usecase`**: Berisi logika dan operasi bisnis utama yang dijalankan oleh aplikasi.

### `datasources`
Menangani interaksi dengan sumber data eksternal, seperti API atau basis data.
- **`repository`**: Kelas yang menangani operasi penyimpanan dan pengambilan data.
- **`service`**: Layanan yang menghubungkan aplikasi dengan sumber data eksternal.

### `domain`
Berisi model dan logika bisnis yang lebih spesifik untuk domain aplikasi.
- **`model`**: Model data yang mewakili entitas dalam aplikasi.
- **`repository`**: Repository untuk operasi terkait data di domain.
- **`usecase`**: Berisi logika bisnis yang berfokus pada domain aplikasi.

### `presentation`
Menangani antarmuka pengguna dan logika presentasi aplikasi.
- **`view`**: Komponen tampilan (UI) aplikasi.
- **`viewModel`**: Berisi logika yang menghubungkan tampilan dengan model data dan memastikan alur data yang benar.

### `utils`
Berisi fungsi-fungsi utilitas yang digunakan di berbagai bagian aplikasi.
- **`BaseFunc.java`**: Kelas utilitas yang berisi fungsi pembantu umum yang digunakan di seluruh proyek.

## Persiapan dan Penggunaan

Berikut adalah langkah-langkah untuk mempersiapkan dan menjalankan aplikasi:

1. **Install MySQL Connector**
   - Unduh MySQL Connector sesuai dengan sistem operasi Anda:
     - Untuk Linux: [mysql-connector-j-9.1.0.tar.gz](https://cdn.mysql.com//Downloads/Connector-J/mysql-connector-j-9.1.0.tar.gz)
     - Untuk Windows, [mysql-connector-j-9.1.0.zip](https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-j-9.1.0.zip)

2. **Buat Database**
   - Buat database `shopping` atau Anda bisa membuka file `/assets/db/shopping.db` untuk melihat query yang digunakan untuk membuatnya.

3. **Jalankan Aplikasi**
   - Setelah semua persiapan selesai, jalankan aplikasi dengan membuka file `App.java` dan menjalankan kode berikut:

### 1. Clone Repository
Untuk memulai, clone repository ini dengan perintah berikut:

```bash
git clone https://github.com/Rifkyyyyyyyy/aplikasi_olshop
