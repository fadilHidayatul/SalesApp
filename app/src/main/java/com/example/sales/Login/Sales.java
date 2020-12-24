package com.example.sales.Login;

public class Sales {

    /**
     * data : {"nama_sales":"fadil","id_sales":"SL-00016","id_cabang":1,"status":1}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * nama_sales : fadil
         * id_sales : SL-00016
         * id_cabang : 1
         * status : 1
         */

        private String nama_sales;
        private String id_sales;
        private int id_cabang;
        private int status;

        public String getNama_sales() {
            return nama_sales;
        }

        public void setNama_sales(String nama_sales) {
            this.nama_sales = nama_sales;
        }

        public String getId_sales() {
            return id_sales;
        }

        public void setId_sales(String id_sales) {
            this.id_sales = id_sales;
        }

        public int getId_cabang() {
            return id_cabang;
        }

        public void setId_cabang(int id_cabang) {
            this.id_cabang = id_cabang;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
