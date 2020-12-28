package com.example.sales.Transaksi.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sales.History.HistoryActivity;
import com.example.sales.R;
import com.example.sales.SharedPreferences.PrefManager;
import com.example.sales.Transaksi.Model.Add;
import com.example.sales.Transaksi.Model.ModelKranjang;
import com.example.sales.Transaksi.Model.Transaksi;
import com.example.sales.UtilsApi.InterfaceBridge;
import com.example.sales.UtilsApi.TransaksiInterface;
import com.example.sales.UtilsApi.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.viewHolder> implements TransaksiInterface {

    Context context;
    List<Transaksi>transaksi;
    InterfaceBridge interfaceBridge;

    int quty =0;
    int harga=0;
    double satuans;

    int statClick= -20;

    public TransaksiAdapter(List<Transaksi> transaksi, InterfaceBridge interfaceBridge) {
        this.transaksi = transaksi;
        this.interfaceBridge = interfaceBridge;
    }

    @NonNull
    @Override
    public TransaksiAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transaksi, parent, false);
        TransaksiAdapter.viewHolder holder = new TransaksiAdapter.viewHolder(v);
        return holder;
    }


    int selectItem = -1;
    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.viewHolder holder,final int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        final Transaksi model = transaksi.get(position);
        holder.txtNamaBarang.setText(model.getNama());
        holder.txtHargaBarang.setText(formatRupiah.format(Integer.parseInt(model.getHarga())));
        holder.banyakBarang.setText(model.getQty());

//        int a = holder.stat.getVisibility();
//        if(a!=0){
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAddRow(holder, model.getId_s(), holder.adds, model, position);

                }

            });
//        }else {
//            holder.card.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "Kamu sudah menambah item itu", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }

//        Log.d( "Visibiliti check", "code ;"+a );

        if (selectItem==position){
            holder.stat.setVisibility(View.VISIBLE);
        }

    }


    private void showDialog(List<Add> adds, final int position, Transaksi model) {
        PrefManager prefManager = new PrefManager(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Tambah Barang ?");
        builder.setMessage("Masukan jumlah item");

        final RecyclerView list = new RecyclerView(context.getApplicationContext());
        list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        list.setClipToPadding(true);
        list.setHasFixedSize(true);

        builder.setView(list);

        AddAdapter adapter = new AddAdapter(adds, this::onUpdateBarang);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        builder.setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectItem = position;
                notifyDataSetChanged();
                addBarang(model);
                interfaceBridge.onUpdateBarang(0, "tambah");
                Log.d("code", "amaont :"+ harga+"qty : "+quty+"satuan = "+satuans+" user :"+prefManager.getIdCabang()+"invoice :"+model.getInvoice()+"id produk :"+model.getId_p()+"harga :"+model.getHarga()+"stok id"+ model.getId_s());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void addBarang(Transaksi model) {
        PrefManager prefManager = new PrefManager(context);
        AndroidNetworking.post(UtilsApi.baseUrl+"addkeranjang")
                .addBodyParameter("amount", "0")
                .addBodyParameter("disc", "0")
                .addBodyParameter("id_user", ""+prefManager.getIdCabang())
                .addBodyParameter("invoiceid", model.getInvoice())
                .addBodyParameter("prices", model.getHarga())
                .addBodyParameter("produkid", model.getId_p())
                .addBodyParameter("quantity", quty+"")
                .addBodyParameter("satuan", satuans+"")
                .addBodyParameter("stockId", model.getId_s())
                .addBodyParameter("id_sales", prefManager.getIdSales())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("data").length()>0){
                                Toast.makeText(context, "data di tambah", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, HistoryActivity.class);
                                context.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }


    private void getAddRow(viewHolder holder, String id_s, List<Add> adds, Transaksi model,final int position) {
        holder.alertDialog.show();
        PrefManager prefManager = new PrefManager(context);
        AndroidNetworking.post(UtilsApi.baseUrl+"apistok")
                .addBodyParameter("stok_id", id_s)
                .addBodyParameter("cabang", String.valueOf(prefManager.getIdCabang()))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("200")){
                                holder.alertDialog.hide();

                                JSONObject isi = response.getJSONObject("stokdata");
                                JSONArray d = isi.getJSONArray("isi");
                                adds.clear();
                                for (int i =0; i<d.length(); i++){
                                    String data = d.getString(i);
                                    Log.d("ulang", "data : "+data);
                                    adds.add(new Add(
                                            data,
                                            Integer.valueOf(model.getHarga()),
                                            model.getId_s(),
                                            model.getId_p()
                                    ));
                                }

                                showDialog(adds, position, model);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        holder.alertDialog.hide();
                        Log.d("eror","code :"+anError);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return transaksi.size();
    }

    @Override
    public void onUpdateBarang(int val, int qty, double satuan, String jenis) {
        PrefManager prefManager = new PrefManager(context);
        interfaceBridge.onUpdateBarang(val, jenis);
        harga = val;
        if (jenis.equalsIgnoreCase("tambah")){
            quty = quty+qty;
        }else {
            quty = quty-qty;
        }

        satuans = satuan;

//        Log.d("code", "amaont :"+ harga+"qty : "+quty+"satuan = "+satuan+" user :"+prefManager.getIdCabang()+"invoice :"+);
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.banyakBarang)
        TextView banyakBarang;
        @BindView(R.id.txtNamaBarang)
        TextView txtNamaBarang;
        @BindView(R.id.txtHargaBarang)
        TextView txtHargaBarang;
        @BindView(R.id.cardTransaksi)
        RelativeLayout card;

        ImageView stat;
        List<Add> adds;

        android.app.AlertDialog alertDialog;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            context = itemView.getContext();
            stat = itemView.findViewById(R.id.stat);
            adds = new ArrayList<>();
            alertDialog =new SpotsDialog.Builder().setContext(context).setMessage("Sedang Mengambil Data ....").setCancelable(false).build();
        }
    }
}

