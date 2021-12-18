package com.example.uaslabmpr;


public class Users {

    String fullName, age, study, gender, spProvinsi, spKotaKabupaten, spKecamatan, spKelurahan;

    public Users() {
    }

    public Users(String fullName, String age, String study, String gender, String prov, String kota, String kecamatan, String kelurahan) {
        this.fullName = fullName;
        this.age = age;
        this.study = study;
        this.gender = gender;
        this.spProvinsi = prov;

        this.spKotaKabupaten = kota;
        this.spKecamatan = kecamatan;
        this.spKelurahan = kelurahan;
    }

//    public Users(String fullName, String age, String study, String gender) {
//    }
//
//    public Users(String fullName, String age, String study, String gender) {
//
//
//    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpProvinsi() {
        return spProvinsi;
    }

    public void setSpProvinsi(String spProvinsi) {
        this.spProvinsi = spProvinsi;
    }

    public String getSpKotaKabupaten() {
        return spKotaKabupaten;
    }

    public void setSpKotaKabupaten(String spKotaKabupaten) {
        this.spKotaKabupaten = spKotaKabupaten;
    }

    public String getSpKecamatan() {
        return spKecamatan;
    }

    public void setSpKecamatan(String spKecamatan) {
        this.spKecamatan = spKecamatan;
    }

    public String getSpKelurahan() {
        return spKelurahan;
    }

    public void setSpKelurahan(String spKelurahan) {
        this.spKelurahan = spKelurahan;
    }
}

