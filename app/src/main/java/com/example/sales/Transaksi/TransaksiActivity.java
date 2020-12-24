package com.example.sales.Transaksi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sales.History.HistoryActivity;
import com.example.sales.History.ModelHistori;
import com.example.sales.Profile.ProfileActivity;
import com.example.sales.R;
import com.example.sales.SharedPreferences.PrefManager;
import com.example.sales.Transaksi.Model.Customer;
import com.example.sales.Transaksi.Model.Transaksi;
import com.example.sales.Transaksi.adapter.TransaksiAdapter;
import com.example.sales.UtilsApi.ApiInterface;
import com.example.sales.UtilsApi.InterfaceBridge;
import com.example.sales.UtilsApi.TransaksiInterface;
import com.example.sales.UtilsApi.UtilsApi;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiActivity extends AppCompatActivity implements InterfaceBridge, TransaksiInterface {
    TransaksiAdapter transaksiAdapter;
    Context context;
    ApiInterface apiInterface;
    PrefManager manager;

    @BindView(R.id.recycler_toko)
    RecyclerView recyclerToko;
    @BindView(R.id.cardScan)
    CardView cardScan;
    @BindView(R.id.txtNamaToko)
    TextView txtNamaToko;
    @BindView(R.id.txtusername)
    TextView txtusername;
    @BindView(R.id.linear_scan)
    LinearLayout linearScan;
    @BindView(R.id.linear_background)
    LinearLayout linearBackground;
    @BindView(R.id.linearProfile)
    LinearLayout linearProfile;
    @BindView(R.id.jmlItem)
    TextView jmlItem;
    @BindView(R.id.btnProses)
    LinearLayout btnProses;

    List<Transaksi> transaksi;


    android.app.AlertDialog alertDialog;
    String invoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        AndroidNetworking.initialize(this);
        ButterKnife.bind(this);
        context = this;
        apiInterface = UtilsApi.getApiService();
        manager = new PrefManager(context);
        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Sedang Mengambil Data ....").setCancelable(false).build();

        getInvoice();
        getData();


        txtusername.setText(manager.getNamaSales());

        recyclerToko.setHasFixedSize(true);
        recyclerToko.setNestedScrollingEnabled(false);
        recyclerToko.setLayoutManager(new LinearLayoutManager(context));
        transaksi = new ArrayList<>();

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if (TextUtils.isEmpty(id)) {
            txtNamaToko.setText("Toko belum di dapatkan");
            recyclerToko.setVisibility(View.INVISIBLE);
            btnProses.setVisibility(View.GONE);
        } else {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearBackground.getLayoutParams();
            params.height = 180;
            linearBackground.setLayoutParams(params);
            btnProses.setVisibility(View.VISIBLE);
            linearScan.setVisibility(View.GONE);

            fetchDataToko(id);
        }

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(TransaksiActivity.this, HistoryActivity.class);
                intent1.putExtra("id_customer", id);
                intent1.putExtra("id_invoice", invoice);
                startActivity(intent1);
            }
        });


        cardScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToScan();
            }
        });

    }

    private void fetchDataToko(String id) {
        alertDialog.show();
        apiInterface.getDataCustomer(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equals("200")) {
                            JSONObject data = object.getJSONObject("data");

                            Gson gson = new Gson();
                            Customer.DataBean customer = gson.fromJson(data + "", Customer.DataBean.class);
                            txtNamaToko.setText(customer.getNama_perusahaan());



                        } else {
                            Toast.makeText(context, "Respon Gagal", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject object = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, "" + object.getString("status"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                alertDialog.hide();
                Toast.makeText(context, "Koneksi Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getInvoice() {
        AndroidNetworking.post(UtilsApi.baseUrl+"invoice")
                .addBodyParameter("type","CANVASING")
                .addBodyParameter("user", manager.getIdCabang()+"")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("sukse", "invoice"+response);
                            if (response.getString("status").equalsIgnoreCase("200")) {
                                invoice = response.getString("inv");
                                getListItem();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("sukse", "code : "+anError + manager.getIdSales());
                        alertDialog.hide();
                    }
                });
    }

    private void getListItem() {
        AndroidNetworking.get(UtilsApi.baseUrl + "apiproduk/" + manager.getIdCabang())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("200")) {
                                alertDialog.hide();
                                Log.d("sukses", "code :" + response + manager.getIdCabang() +" - "+invoice);
                                JSONArray d = response.getJSONArray("produk");
                                transaksi.clear();
                                for (int i = 0; i < d.length(); i++) {
                                    JSONObject data = d.getJSONObject(i);
                                    transaksi.add(new Transaksi(
                                            data.getString("stok_id"),
                                            data.getString("produk_id"),
                                            data.getString("produk_nama"),
                                            data.getString("quantity"),
                                            data.getString("capital_price"),
                                            invoice
                                    ));
                                }
                                getAdapter();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, "Koneksi Internet", Toast.LENGTH_SHORT).show();
                        Log.d("Error", "code :" + anError);
                        alertDialog.hide();
                    }
                });
    }

    private void getAdapter() {
        transaksiAdapter = new TransaksiAdapter(transaksi, this);
        recyclerToko.setAdapter(transaksiAdapter);
        transaksiAdapter.notifyDataSetChanged();
    }

    private void moveToScan() {
        Intent moveScan = new Intent(context, ScanActivity.class);
        startActivity(moveScan);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning!!")
                .setCancelable(false)
                .setMessage("Data scan akan hilang jika keluar!\nApakah yakin anda keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent goHome = new Intent(context, MainActivity.class);
//                        goHome.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                        startActivity(goHome);
                        Intent intent = new Intent(TransaksiActivity.this, TransaksiActivity.class);
                        startActivity(intent);
                        closeOptionsMenu();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void moveToHistory(View view) {
        Intent moveHistory = new Intent(context, HistoryActivity.class);
        startActivity(moveHistory);
    }

    public void moveToProfile(View view) {
        Intent moveProfile = new Intent(context, ProfileActivity.class);
        startActivity(moveProfile);
    }

    private void getData() {
        PrefManager prefManager = new PrefManager(this);
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
                                btnProses.setVisibility(View.VISIBLE);
                                if (d.length() == 0){
                                    btnProses.setVisibility(View.GONE);
                                }
                                for (int i = 0; i < d.length(); i++) {
                                    JSONObject data = d.getJSONObject(i);
                                    jmlItem.setText((d.length())+" item");
                                }

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

    String def = "0 ,-";
    @Override
    public void onUpdateBarang(int val, String jenis) {
//        Locale localeID = new Locale("in", "ID");
//        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
//
//        if (jenis.equalsIgnoreCase("tambah")) {
//            String split = def;
//            String[] jml = split.split(" ");
//            String item = jml[0];
//            String text = jml[1];
//            int a = Integer.valueOf(item) + val;
//            int show = Integer.valueOf(item) + val;
//
//            jmlItem.setText(formatRupiah.format((double)show) + " ,-");
//            def = a+ " ,-";
//
//        }else {
//            String split = def;
//            String[] jml = split.split(" ");
//            String item = jml[0];
//            String text = jml[1];
//            int show = Integer.valueOf(item) - val;
//            int a = Integer.valueOf(item) - val;
//            jmlItem.setText(formatRupiah.format((double)show) + " ,-");
//            def = a+" -,";
//        }
        getData();
    }

    @Override
    public void onUpdateBarang(int val, int qty, double satuan, String jenis) {
        getData();
    }
}
