

---

# 🧬 Deteksi Kanker (Cancer Detection)

**Deteksi Kanker** adalah aplikasi Android yang memanfaatkan teknologi **Machine Learning** untuk membantu mendeteksi potensi kanker dari gambar. Aplikasi ini dikembangkan menggunakan *starter project* dari Dicoding dan telah diintegrasikan dengan **model ML berbasis TensorFlow Lite**.

---

## ✅ Fitur Utama

### 1. 📁 Menggunakan Starter Project

Aplikasi ini dikembangkan menggunakan starter project resmi dari Dicoding, sebagai fondasi utama pengembangan, yang memudahkan struktur awal proyek serta implementasi fitur tambahan.

### 2. 📷 Mengambil dan Menampilkan Gambar

* Pengguna dapat **mengambil gambar** melalui kamera atau memilih gambar dari **galeri perangkat**.
* Gambar yang dipilih langsung ditampilkan di halaman utama aplikasi sebelum proses prediksi dilakukan.

### 3. 🤖 Integrasi Model Machine Learning dari Dicoding

* Aplikasi menggunakan **model klasifikasi kanker** berbasis **.tflite** yang telah disediakan oleh Dicoding.
* Model ini digunakan untuk menganalisis gambar dan menghasilkan prediksi kondisi medis berdasarkan dataset pelatihan.

### 4. 🧠 Prediksi Gambar Menggunakan TensorFlow Lite

* Proses inferensi dilakukan di perangkat secara langsung menggunakan **TensorFlow Lite**.
* Tidak memerlukan koneksi internet, sehingga dapat bekerja secara **offline** dan cepat.

### 5. 📊 Menampilkan Hasil Prediksi

* Hasil klasifikasi ditampilkan secara **jelas dan informatif** di halaman **ResultActivity**.
* Menampilkan label penyakit yang terdeteksi dan tingkat **confidence** dari model terhadap gambar yang diuji.

---

## 🛠️ Teknologi dan Library

| Komponen           | Teknologi / Library                |
| ------------------ | ---------------------------------- |
| Bahasa Pemrograman | Kotlin                             |
| Framework UI       | Android Jetpack (Activity, Intent) |
| ML Framework       | TensorFlow Lite (.tflite)          |
| Image Capture      | CameraX, Intent Gallery Picker     |
| Image Processing   | Bitmap Conversion, TensorImage     |
| Navigasi           | Intent antar Activity              |

---

## 🚀 Cara Menjalankan Aplikasi

1. **Clone project:**

   ```bash
   git clone https://github.com/username/cancer-detection-app.git
   ```

2. **Buka project** di Android Studio.

3. **Jalankan aplikasi** di emulator atau perangkat fisik Android.

4. **Ambil gambar**, lakukan prediksi, dan lihat hasil klasifikasi kanker.

---

## 📸 Screenshot

| Ambil Gambar                                                     | Hasil Prediksi                                                     |
| ---------------------------------------------------------------- | ------------------------------------------------------------------ |
| ![Ambil](https://via.placeholder.com/200x400?text=Camera+Screen) | ![Hasil](https://via.placeholder.com/200x400?text=Result+Activity) |

---

## 📌 Catatan Tambahan

* Model `.tflite` harus diletakkan di folder `assets` atau `ml` sesuai konfigurasi project.
* Aplikasi telah disesuaikan untuk **penggunaan offline** tanpa memerlukan API eksternal.
* Hasil prediksi hanya bersifat **informasi awal**, bukan diagnosis medis final.

---

## 👨‍💻 Developer

* **Nama**: Muhammad Rafli Nurfathan
* **Email**: [nurfathanrafli85@gmail.com](mailto:nurfathanrafli85@gmail.com)
* **LinkedIn**: [linkedin.com/in/mhmmdraflin](https://www.linkedin.com/in/mhmmdraflin)

---
