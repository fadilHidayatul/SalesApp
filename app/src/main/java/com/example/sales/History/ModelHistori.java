package com.example.sales.History;

import java.util.List;

public class ModelHistori {


        private int stok_id;
        private String produk_id;
        private String nama_type_produk;
        private String produk_brand;
        private String produk_nama;
        private String produk_harga;
        private String total;
        private String diskon;
        private int tot;
        private int amount;
        private int id_transaksi_tmp;
        private String quantity;

        public ModelHistori(int stok_id, String produk_id, String nama_type_produk, String produk_brand, String produk_nama, String produk_harga, String total, String diskon, int tot, int amount, int id_transaksi_tmp, String quantity) {
            this.stok_id = stok_id;
            this.produk_id = produk_id;
            this.nama_type_produk = nama_type_produk;
            this.produk_brand = produk_brand;
            this.produk_nama = produk_nama;
            this.produk_harga = produk_harga;
            this.total = total;
            this.diskon = diskon;
            this.tot = tot;
            this.amount = amount;
            this.id_transaksi_tmp = id_transaksi_tmp;
            this.quantity = quantity;
        }

        public int getStok_id() {
            return stok_id;
        }

        public void setStok_id(int stok_id) {
            this.stok_id = stok_id;
        }

        public String getProduk_id() {
            return produk_id;
        }

        public void setProduk_id(String produk_id) {
            this.produk_id = produk_id;
        }

        public String getNama_type_produk() {
            return nama_type_produk;
        }

        public void setNama_type_produk(String nama_type_produk) {
            this.nama_type_produk = nama_type_produk;
        }

        public String getProduk_brand() {
            return produk_brand;
        }

        public void setProduk_brand(String produk_brand) {
            this.produk_brand = produk_brand;
        }

        public String getProduk_nama() {
            return produk_nama;
        }

        public void setProduk_nama(String produk_nama) {
            this.produk_nama = produk_nama;
        }

        public String getProduk_harga() {
            return produk_harga;
        }

        public void setProduk_harga(String produk_harga) {
            this.produk_harga = produk_harga;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getDiskon() {
            return diskon;
        }

        public void setDiskon(String diskon) {
            this.diskon = diskon;
        }

        public int getTot() {
            return tot;
        }

        public void setTot(int tot) {
            this.tot = tot;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getId_transaksi_tmp() {
            return id_transaksi_tmp;
        }

        public void setId_transaksi_tmp(int id_transaksi_tmp) {
            this.id_transaksi_tmp = id_transaksi_tmp;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }

