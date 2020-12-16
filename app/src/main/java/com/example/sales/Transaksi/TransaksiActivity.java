package com.example.sales.Transaksi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sales.History.HistoryActivity;
import com.example.sales.R;
import com.example.sales.SharedPreferences.PrefManager;
import com.example.sales.Transaksi.Model.Customer;
import com.example.sales.UtilsApi.ApiInterface;
import com.example.sales.UtilsApi.UtilsApi;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        ButterKnife.bind(this);
        context = this;
        apiInterface = UtilsApi.getApiService();
        manager = new PrefManager(context);

        txtusername.setText(manager.getNamaSales());

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if (TextUtils.isEmpty(id)) {
            txtNamaToko.setText("Toko belum di dapatkan");
            recyclerToko.setVisibility(View.INVISIBLE);
        } else {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearBackground.getLayoutParams();
            params.height = 180;
            linearBackground.setLayoutParams(params);

            linearScan.setVisibility(View.GONE);

            fetchDataToko(id);
        }


        cardScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToScan();
            }
        });

    }

    private void fetchDataToko(String id) {
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

                            recyclerToko.setLayoutManager(new LinearLayoutManager(context));
                            transaksiAdapter = new TransaksiAdapter(context);
                            recyclerToko.setAdapter(transaksiAdapter);
                            recyclerToko.setHasFixedSize(true);
                            recyclerToko.setNestedScrollingEnabled(false);

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
                Toast.makeText(context, "Koneksi Internet", Toast.LENGTH_SHORT).show();
            }
        });
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
                        finish();
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
}
