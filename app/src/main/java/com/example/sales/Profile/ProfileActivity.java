package com.example.sales.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sales.Login.LoginActivity;
import com.example.sales.R;
import com.example.sales.SharedPreferences.PrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    AlertDialog dialog;
    PrefManager manager;
    Context context;

    @BindView(R.id.logout)
    LinearLayout logout;
    @BindView(R.id.txtProfileNama)
    TextView txtProfileNama;
    @BindView(R.id.txtProfileIdSales)
    TextView txtProfileIdSales;
    @BindView(R.id.txtProfileIdCabang)
    TextView txtProfileIdCabang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        context = this;
        manager = new PrefManager(context);

        //dialog = new SpotsDialog.Builder().setContext(ProfileActivity.this).setMessage("Harap Tunggu ...").setCancelable(false).build();
        txtProfileNama.setText(manager.getNamaSales().toString());
        txtProfileIdSales.setText(manager.getIdSales().toString());
        txtProfileIdCabang.setText(manager.getIdCabang()+"");
    }

    public void backToPrev(View view) {
        finish();
    }

    public void logout(View view) {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Keluar Aplikasi?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                manager.setIdSales(PrefManager.ID_SALES, "");
                                manager.setNamaSales(PrefManager.NAMA_SALES, "");
                                manager.setIdCabang(PrefManager.ID_CABANG, 0);
                                Intent doLogout = new Intent(v.getContext(), LoginActivity.class);
                                doLogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                finish();
                                startActivity(doLogout);
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
        });
    }
}
