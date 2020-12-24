package com.example.sales.Transaksi.Model;

public class ModelKranjang {
    private String idProduk;
    private String idUser;
    private String idStok;
    private String totalHarga;
    private String hargaSatuan;
    private String banyakSatuan;

    public ModelKranjang(String idProduk, String idUser, String idStok, String totalHarga, String hargaSatuan, String banyakSatuan) {
        this.idProduk = idProduk;
        this.idUser = idUser;
        this.idStok = idStok;
        this.totalHarga = totalHarga;
        this.hargaSatuan = hargaSatuan;
        this.banyakSatuan = banyakSatuan;
    }

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdStok() {
        return idStok;
    }

    public void setIdStok(String idStok) {
        this.idStok = idStok;
    }

    public String getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(String totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(String hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public String getBanyakSatuan() {
        return banyakSatuan;
    }

    public void setBanyakSatuan(String banyakSatuan) {
        this.banyakSatuan = banyakSatuan;
    }
}
