package com.example.sales.Transaksi.Model;


public class Add {
    private String text;
    private int harga;
    private String id_s;
    private String id_p;

    public Add(String text, int harga, String id_s, String id_p) {
        this.text = text;
        this.harga = harga;
        this.id_s = id_s;
        this.id_p = id_p;
    }

    public String getId_p() {
        return id_p;
    }

    public void setId_p(String id_p) {
        this.id_p = id_p;
    }

    public String getId_s() {
        return id_s;
    }

    public void setId_s(String id_s) {
        this.id_s = id_s;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
