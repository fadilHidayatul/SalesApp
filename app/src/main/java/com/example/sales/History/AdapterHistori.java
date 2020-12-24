package com.example.sales.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sales.R;
import com.example.sales.Transaksi.Model.Add;
import com.example.sales.Transaksi.Model.Transaksi;
import com.example.sales.Transaksi.adapter.AddAdapter;
import com.example.sales.UtilsApi.OnDelete;
import com.example.sales.UtilsApi.OnGetHarga;
import com.example.sales.UtilsApi.TransaksiInterface;
import com.example.sales.UtilsApi.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterHistori extends RecyclerView.Adapter<AdapterHistori.ListViewHolder> {
    Context context;
    List<ModelHistori> histori;
    OnDelete delete;
    OnGetHarga getHarga;
    TransaksiInterface transaksiInterface;

    public AdapterHistori(List<ModelHistori> histori, OnDelete delete, OnGetHarga getHarga, TransaksiInterface transaksiInterface) {
        this.histori = histori;
        this.delete = delete;
        this.getHarga = getHarga;
        this.transaksiInterface = transaksiInterface;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history, parent, false);
        AdapterHistori.ListViewHolder holder = new AdapterHistori.ListViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final ModelHistori model = histori.get(position);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        getHarga.getHarga(model.getAmount(), "1");

        holder.harga.setText(formatRupiah.format(model.getAmount()));
        holder.nama.setText(model.getProduk_nama());
        holder.qty.setText(model.getQuantity());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDelete(model);
                getHarga.getHarga(model.getAmount(), "0");
            }
        });

    }

    private void setDelete(ModelHistori model) {
        AndroidNetworking.post(UtilsApi.baseUrl+"deleteitem")
                .addBodyParameter("id_transksi", String.valueOf(model.getId_transaksi_tmp()))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("200")){
                                Toast.makeText(context, "terhapus "+model.getId_transaksi_tmp(), Toast.LENGTH_SHORT).show();
                                delete.deleteItem();
                                transaksiInterface.onUpdateBarang(0, 0,Double.valueOf("0"), "tambah");
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

    @Override
    public int getItemCount() {
        return histori.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView nama, harga, qty;
        ImageView delete;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            harga = itemView.findViewById(R.id.hargaProduk);
            nama = itemView.findViewById(R.id.namaProduk);
            qty = itemView.findViewById(R.id.quantiti);
            delete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
