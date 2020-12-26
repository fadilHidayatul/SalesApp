package com.example.sales.hist;

public class ModelHist {

    /**
     * id_transaksi_sales : 2
     * sales_type : TAKING ORDER
     * invoice_id : TOVS-202012-003-1
     * invoice_date : 2020-12-14
     * transaksi_tipe : Credit
     * term_until : 2020-12-26
     * sales_id : SL-00009
     * customer_id : 9
     * note : null
     * totalsales : 46800
     * diskon : 936
     * dp : 936
     * id_user : 3
     * id_warehouse : null
     * status : UNPAID
     * approve : 0
     */

    private String id_transaksi_sales;
    private String sales_type;
    private String invoice_id;
    private String invoice_date;
    private String transaksi_tipe;
    private String term_until;
    private String sales_id;
    private String customer_id;
    private Object note;
    private String totalsales;
    private String diskon;
    private String dp;
    private String id_user;
    private Object id_warehouse;
    private String status;
    private String approve;

    public ModelHist(String id_transaksi_sales, String sales_type, String invoice_id, String invoice_date, String transaksi_tipe, String term_until, String sales_id, String customer_id, Object note, String totalsales, String diskon, String dp, String id_user, Object id_warehouse, String status, String approve) {
        this.id_transaksi_sales = id_transaksi_sales;
        this.sales_type = sales_type;
        this.invoice_id = invoice_id;
        this.invoice_date = invoice_date;
        this.transaksi_tipe = transaksi_tipe;
        this.term_until = term_until;
        this.sales_id = sales_id;
        this.customer_id = customer_id;
        this.note = note;
        this.totalsales = totalsales;
        this.diskon = diskon;
        this.dp = dp;
        this.id_user = id_user;
        this.id_warehouse = id_warehouse;
        this.status = status;
        this.approve = approve;
    }

    public String getId_transaksi_sales() {
        return id_transaksi_sales;
    }

    public void setId_transaksi_sales(String id_transaksi_sales) {
        this.id_transaksi_sales = id_transaksi_sales;
    }

    public String getSales_type() {
        return sales_type;
    }

    public void setSales_type(String sales_type) {
        this.sales_type = sales_type;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    public String getTransaksi_tipe() {
        return transaksi_tipe;
    }

    public void setTransaksi_tipe(String transaksi_tipe) {
        this.transaksi_tipe = transaksi_tipe;
    }

    public String getTerm_until() {
        return term_until;
    }

    public void setTerm_until(String term_until) {
        this.term_until = term_until;
    }

    public String getSales_id() {
        return sales_id;
    }

    public void setSales_id(String sales_id) {
        this.sales_id = sales_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public String getTotalsales() {
        return totalsales;
    }

    public void setTotalsales(String totalsales) {
        this.totalsales = totalsales;
    }

    public String getDiskon() {
        return diskon;
    }

    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public Object getId_warehouse() {
        return id_warehouse;
    }

    public void setId_warehouse(Object id_warehouse) {
        this.id_warehouse = id_warehouse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }
}
