package com.example.sales.Transaksi.Model;

public class Transaksi {
    private String id_s;
    private String id_p;
    private String nama;
    private String qty;
    private String harga;
    private String invoice;


    public Transaksi(String id_s, String id_p, String nama, String qty, String harga, String invoice) {
        this.id_s = id_s;
        this.id_p = id_p;
        this.nama = nama;
        this.qty = qty;
        this.harga = harga;
        this.invoice = invoice;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getId_s() {
        return id_s;
    }

    public void setId_s(String id_s) {
        this.id_s = id_s;
    }

    public String getId_p() {
        return id_p;
    }

    public void setId_p(String id_p) {
        this.id_p = id_p;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
