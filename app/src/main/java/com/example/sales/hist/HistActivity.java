package com.example.sales.hist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sales.History.AdapterHistori;
import com.example.sales.History.ModelHistori;
import com.example.sales.R;
import com.example.sales.SharedPreferences.PrefManager;
import com.example.sales.Transaksi.TransaksiActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class HistActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    android.app.AlertDialog alertDialog;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView back;
    List<ModelHist>hists;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hist);
        prefManager = new PrefManager(this);
        alertDialog =new SpotsDialog.Builder().setContext(this).setMessage("Sedang Mengambil Data ....").setCancelable(false).build();
        AndroidNetworking.initialize(this);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.rvHist);
        swipeRefreshLayout = findViewById(R.id.swHist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getHist();
    }

    private void getHist() {
        alertDialog.show();
        AndroidNetworking.post("http://192.168.100.35/distribusi/get_histori.php")
                .addBodyParameter("id", prefManager.getIdSales())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            alertDialog.hide();
                            if (response.getString("kode").equalsIgnoreCase("1")){
                                JSONArray d = response.getJSONArray("data");
                                hists = new ArrayList<>();
                                hists.clear();
                                Gson gson = new Gson();
                                for (int i = 0; i < d.length(); i++) {
                                    JSONObject data = d.getJSONObject(i);
                                    ModelHist his = gson.fromJson(data + "", ModelHist.class);
                                    hists.add(his);
                                }
                                getAdapter();
                            }else {
                                Toast.makeText(getApplicationContext(), response.getString("pesan"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        alertDialog.hide();
                        Toast.makeText(getApplicationContext(), ""+anError, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void getAdapter() {
        AdapterHist adapterHist = new AdapterHist(hists);
        recyclerView.setAdapter(adapterHist);
        adapterHist.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), TransaksiActivity.class);
        intent.putExtra("id","");
        startActivity(intent);
    }

    public void backToTransaksi(View view) {
        Intent intent = new Intent(getApplicationContext(), TransaksiActivity.class);
        intent.putExtra("id","");
        startActivity(intent);
    }
}
