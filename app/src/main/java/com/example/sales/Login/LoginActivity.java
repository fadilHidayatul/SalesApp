package com.example.sales.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sales.R;
import com.example.sales.SharedPreferences.PrefManager;
import com.example.sales.Transaksi.TransaksiActivity;
import com.example.sales.UtilsApi.ApiInterface;
import com.example.sales.UtilsApi.UtilsApi;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    Context context;
    PrefManager manager;


    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.inputUsername)
    EditText inputUsername;
    @BindView(R.id.inputPassword)
    EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AndroidNetworking.initialize(this);
        ButterKnife.bind(this);
        context = this;
        apiInterface = UtilsApi.getApiService();
        manager = new PrefManager(context);

        if (!manager.getIdSales().equalsIgnoreCase("")){
            Intent intent = new Intent(getApplicationContext(), TransaksiActivity.class);
            startActivity(intent);
            finish();
        }

        fetchLogin();
    }

    private void fetchLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputUsername.getText().toString())) {
                    inputUsername.setError("Masukkan Username");
                    return;
                } else if (TextUtils.isEmpty(inputPassword.getText().toString())) {
                    inputPassword.setError("Masukkan Password");
                    return;
                } else {
//                    apiInterface.loginSales(inputUsername.getText().toString(), inputPassword.getText().toString()).enqueue(new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            if (response.isSuccessful()){
//                                try {
//                                    JSONObject object = new JSONObject(response.body().string());
//                                    JSONObject data = object.getJSONObject("data");
//
//                                    //addToModel
//                                    Gson gson = new Gson();
//                                    Sales.DataBean sales = gson.fromJson(data+"",Sales.DataBean.class);
//
//                                    //saveSession
//                                    manager.saveSession();
//                                    manager.setNamaSales(PrefManager.NAMA_SALES,sales.getNama_sales());
//                                    manager.setIdSales(PrefManager.ID_SALES,sales.getId_sales());
//                                    manager.setIdCabang(PrefManager.ID_CABANG,sales.getId_cabang());
//
//                                    Intent intent = new Intent(getApplicationContext(), TransaksiActivity.class);
//                                    startActivity(intent);
//                                    finish();
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }else{
//                                try {
//                                    JSONObject object = new JSONObject(response.errorBody().string());
//                                    Toast.makeText(context, ""+object.getString("status"), Toast.LENGTH_SHORT).show();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//                            Toast.makeText(context, "Cek Koneksi Internet", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    Log.d("test", "jalan");
                    AndroidNetworking.post(UtilsApi.baseUrl + "loginsales")
                            .addBodyParameter("username", inputUsername.getText().toString())
                            .addBodyParameter("password", inputPassword.getText().toString())
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        Log.d("sukses", "code :" + response);
                                        JSONObject data = response.getJSONObject("data");
                                        if (data.getString("status").equalsIgnoreCase("1")) {
                                            Gson gson = new Gson();
                                            Sales.DataBean sales = gson.fromJson(data + "", Sales.DataBean.class);

                                            //saveSession
                                            manager.saveSession();
                                            manager.setNamaSales(PrefManager.NAMA_SALES, sales.getNama_sales());
                                            manager.setIdSales(PrefManager.ID_SALES, sales.getId_sales());
                                            manager.setIdCabang(PrefManager.ID_CABANG, sales.getId_cabang());

                                            Intent intent = new Intent(getApplicationContext(), TransaksiActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Log.d("sukses", "code :" + response);
                                            Toast.makeText(context, "" + data.getString("status"), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Log.d("eror", "code : " + anError);
                                }
                            });
                }
            }
        });
    }


}
