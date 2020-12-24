package com.example.sales.Transaksi.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.renderscript.Type;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sales.R;
import com.example.sales.Transaksi.Model.Add;
import com.example.sales.Transaksi.Model.ModelKranjang;
import com.example.sales.UtilsApi.TransaksiInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.ListViewHolder> {
    Context context;
    List<Add> adds;
    TransaksiInterface transaksiInterface;

    public AddAdapter(List<Add> adds, TransaksiInterface transaksiInterface) {
        this.adds = adds;
        this.transaksiInterface = transaksiInterface;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add, parent, false);
        AddAdapter.ListViewHolder holder = new AddAdapter.ListViewHolder(v);
        return holder;
    }
    double satuan;
    int qty;
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final Add model = adds.get(position);
        ModelKranjang modelKranjang;

        String ap = model.getText();
        String[] parts = ap.split(" ");
        String ready = parts[0];
        String text = parts[1];
        String value = parts[2];


        if (position == (adds.size()-1)) {
            satuan = Integer.valueOf(model.getHarga())/Integer.parseInt(value);
        }

        holder.qty.setText(text);

        holder.tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty = Integer.valueOf(value);
                int banyak =Integer.valueOf(holder.jml.getText().toString()) + 1;
                holder.jml.setText(banyak+"");

                double val = Integer.valueOf(value)*satuan;
                transaksiInterface.onUpdateBarang(Integer.valueOf(Math.round(val)+""),qty, satuan,"tambah");
                Log.d("tambah ", "jml :"+satuan);

            }
        });

        holder.kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(holder.jml.getText().toString()) > 0) {
                    qty = Integer.valueOf(value);
                    int banyak = Integer.valueOf(holder.jml.getText().toString()) - 1;
                    holder.jml.setText(banyak+"");

                    Double val = 1 * Integer.valueOf(value)*satuan;
                    transaksiInterface.onUpdateBarang(Integer.valueOf(Math.round(val)+""), qty, satuan,"kurang");
                    Log.d("kurang ", "jml :"+val);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return adds.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView qty;
        Button tambah, kurang;
        EditText jml;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            qty = itemView.findViewById(R.id.namaQty);
            tambah = itemView.findViewById(R.id.tambah);
            kurang = itemView.findViewById(R.id.kurang);
            jml = itemView.findViewById(R.id.banyak);

        }
    }
}
