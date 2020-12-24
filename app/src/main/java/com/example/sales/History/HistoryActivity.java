package com.example.sales.History;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sales.R;
import com.example.sales.SharedPreferences.PrefManager;
import com.example.sales.Transaksi.TransaksiActivity;
import com.example.sales.UtilsApi.InterfaceBridge;
import com.example.sales.UtilsApi.OnDelete;
import com.example.sales.UtilsApi.OnGetHarga;
import com.example.sales.UtilsApi.TransaksiInterface;
import com.example.sales.UtilsApi.UtilsApi;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

public class HistoryActivity extends AppCompatActivity implements OnDelete, OnGetHarga, TransaksiInterface, InterfaceBridge {
    RecyclerView recyclerView;
    List<ModelHistori>histori;
    ImageView back;
    android.app.AlertDialog alertDialog;
    PrefManager prefManager;
    TextView total;
    String id_customer, invoice;
    RelativeLayout rekap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Sedang Mengambil Data ....").setCancelable(false).build();
        AndroidNetworking.initialize(this);
        Intent intent = new Intent(getIntent());
        invoice =  intent.getStringExtra("id_invoice");
        id_customer = intent.getStringExtra("id_customer");
        prefManager = new PrefManager(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, TransaksiActivity.class);
                intent.putExtra("id", id_customer);
                startActivity(intent);
            }
        });

        rekap = findViewById(R.id.rekapBtn);
        rekap.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                simpanData();
            }
        });


        total = findViewById(R.id.hargaTotal);

        recyclerView = findViewById(R.id.rvHistori);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        getData();
    }
    int totalI = 0;
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void simpanData() {
        alertDialog.show();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
//        System.out.println(dtf.format(now));
        alertDialog.setMessage("Simpan data...");
        AndroidNetworking.post(UtilsApi.baseUrl+"rekaptransaksi")
                .addBodyParameter("customerid", id_customer)
                .addBodyParameter("dp", "")
                .addBodyParameter("id_user", ""+prefManager.getIdCabang())
                .addBodyParameter("invoicedate", dtf.format(now))
                .addBodyParameter("invoiceid", invoice)
                .addBodyParameter("note","")
                .addBodyParameter("potongan", "")
                .addBodyParameter("radiocash", "Cash")
                .addBodyParameter("salesmanid", prefManager.getIdSales())
                .addBodyParameter("salestype", "CANVASING")
                .addBodyParameter("term_util", dtf.format(now))
                .addBodyParameter("totalsales", totalI+"")
                .addBodyParameter("warehouse", "")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            alertDialog.hide();
                            if (response.getString("status").equalsIgnoreCase("200")){
                                Toast.makeText(getApplicationContext(), "Transaksi selesai", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), "Transaksi gagal", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        alertDialog.hide();
                        Toast.makeText(getApplicationContext(), "Transaksi gagal", Toast.LENGTH_SHORT).show();

                        Log.d("eror", "code :"+anError+"| idc ; "+id_customer+"| id_u ="+prefManager.getIdCabang()+"| date :"+dtf.format(now)+"| invc :"+invoice+"| ");
                    }
                });
    }

    private void getData() {
        alertDialog.show();
        AndroidNetworking.post(UtilsApi.baseUrl+"apidatatable")
                .addBodyParameter("id_sales", prefManager.getIdSales())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            alertDialog.hide();
                            if (response.getString("data").length()>0){
                                JSONArray d = response.getJSONArray("data");
                                histori = new ArrayList<>();
                                histori.clear();
                                Gson gson = new Gson();
                                for (int i = 0; i < d.length(); i++) {
                                    JSONObject data = d.getJSONObject(i);
                                    ModelHistori his = gson.fromJson(data + "", ModelHistori.class);
                                    histori.add(his);
                                }
                                getAdapter();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        alertDialog.hide();
                    }
                });
    }

    private void getAdapter() {
        AdapterHistori adapterHistori = new AdapterHistori(histori, this::deleteItem,this::getHarga, this::onUpdateBarang);
        recyclerView.setAdapter(adapterHistori);
        adapterHistori.notifyDataSetChanged();
    }

    @Override
    public void deleteItem() {
        getData();
        totalI=0;
    }


    @Override
    public void getHarga(int amount, String s) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        if (s.equalsIgnoreCase("1")) {
            totalI = totalI + amount;
            total.setText(formatRupiah.format(totalI));
        }else {
            totalI = totalI - amount;
            total.setText(formatRupiah.format(totalI));
        }

    }

    @Override
    public void onUpdateBarang(int val, int qty, double satuan, String jenis) {
        getData();
    }

    @Override
    public void onUpdateBarang(int val, String jenis) {
        getData();
    }
}
