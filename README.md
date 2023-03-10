Umumnya developer akan membuat Form Validation langsung terintegrasi dengan input element.
Menurut saya hal ini memiliki drawback yang cukup besar. 
Misalnya saja, ketika codebase kita sudah di lengkapi dengan Form Validation tetapi designer kita yang sableng itu mendesain input element custom, maka kita harus membuat custom Form Validation juga.

Akan tetapi sekalipun hanya ada 1 buah input elemen baru, kita tetap harus mensuport semua basic input yang ada dalam form validation kita.
Kita harus membuat form validation yang terintegrasi dengan semua input element lainnya juga seperti, edit text, date picker, toggle button, radio button, dan sebagainya.
Menurut saya, pendekatan ini kurang efektiv, oleh karena itu, saya mendesain form validation sederhana dimana formulir tidak terintegrasi dengan view.

Formulir berdiri sendiri sebagai sebuah formulir object, sementara view dibantu oleh fragment hanya merepresentasikan object formulir tersebut.
Pattern model view intent, juga membuat kita lebih fleksible untuk merepresentasikan FormItem ke view, dan menjamin source of truth dari formulir.

Support untuk menghandle Regex secara flawless masi saya pikirkan. Form rule nantinya akan sepenuhnya menggunakan regex sebagai mekanisme validasi.

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

