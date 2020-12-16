package com.example.sales.Transaksi.Model;

public class Customer {

    /**
     * data : {"id_customer":9,"nama_customer":"Et minus duis volupt","nama_perusahaan":"Quis voluptatem non","credit_plafond":72,"alamat":"Ex irure rerum cupid","negara":"Soluta Nam dolore do","kota":"Aut quibusdam dolore","telepon":"76","kartu_kredit":"98","fax":"2","id_sales":"SL-00014","note":"Dolorum labore offic","id_cabang":1}
     * status : 200
     */

    private DataBean data;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * id_customer : 9
         * nama_customer : Et minus duis volupt
         * nama_perusahaan : Quis voluptatem non
         * credit_plafond : 72
         * alamat : Ex irure rerum cupid
         * negara : Soluta Nam dolore do
         * kota : Aut quibusdam dolore
         * telepon : 76
         * kartu_kredit : 98
         * fax : 2
         * id_sales : SL-00014
         * note : Dolorum labore offic
         * id_cabang : 1
         */

        private int id_customer;
        private String nama_customer;
        private String nama_perusahaan;
        private int credit_plafond;
        private String alamat;
        private String negara;
        private String kota;
        private String telepon;
        private String kartu_kredit;
        private String fax;
        private String id_sales;
        private String note;
        private int id_cabang;

        public int getId_customer() {
            return id_customer;
        }

        public void setId_customer(int id_customer) {
            this.id_customer = id_customer;
        }

        public String getNama_customer() {
            return nama_customer;
        }

        public void setNama_customer(String nama_customer) {
            this.nama_customer = nama_customer;
        }

        public String getNama_perusahaan() {
            return nama_perusahaan;
        }

        public void setNama_perusahaan(String nama_perusahaan) {
            this.nama_perusahaan = nama_perusahaan;
        }

        public int getCredit_plafond() {
            return credit_plafond;
        }

        public void setCredit_plafond(int credit_plafond) {
            this.credit_plafond = credit_plafond;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getNegara() {
            return negara;
        }

        public void setNegara(String negara) {
            this.negara = negara;
        }

        public String getKota() {
            return kota;
        }

        public void setKota(String kota) {
            this.kota = kota;
        }

        public String getTelepon() {
            return telepon;
        }

        public void setTelepon(String telepon) {
            this.telepon = telepon;
        }

        public String getKartu_kredit() {
            return kartu_kredit;
        }

        public void setKartu_kredit(String kartu_kredit) {
            this.kartu_kredit = kartu_kredit;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getId_sales() {
            return id_sales;
        }

        public void setId_sales(String id_sales) {
            this.id_sales = id_sales;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public int getId_cabang() {
            return id_cabang;
        }

        public void setId_cabang(int id_cabang) {
            this.id_cabang = id_cabang;
        }
    }
}
