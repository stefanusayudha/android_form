# Singularity Code Present

Library untuk melakukan validasi formulir android.
Formulir berupa Formulir object yang tidak terintegrasi pada view, hal ini memungkinkan pengaplikasian yang lebih fleksible.

Untuk menggunakan library ini tambahkan dalam build.gradle sebagai berikut:

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
  
dependencies {
    implementation 'com.github.stefanusayudha:android_form:1.0.0'
}

```

Note: Untuk saat ini belum berbasis pada regex, our chef still working on it.
Spoilers: Validasi berbasis regex memungkinkan kita untuk mengirimkan rules berupa pattern dari server. Sehingga kita bisa membuat form custom dengan rules yang dapat disesuaikan sewaktu-waktu.
