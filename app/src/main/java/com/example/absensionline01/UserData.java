package com.example.absensionline01;

public class UserData{
    private String id_karyawan;
    private String nama;
    private  String jabatan;
    private  String sex;
    private String Absen;


    public void setId_karyawan(String id_karyawan) {
        this.id_karyawan = id_karyawan;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAbsen(String absen) {
        Absen = absen;
    }

    public String getId_karyawan() {
        return id_karyawan;
    }

    public String getNama() {
        return nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getSex() {
        return sex;
    }

    public String getAbsen() {
        return Absen;
    }
}
