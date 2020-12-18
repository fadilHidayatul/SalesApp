package com.example.sales.Transaksi;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sales.R;
import com.example.sales.SharedPreferences.PrefManager;
import com.example.sales.Transaksi.Model.Add;
import com.example.sales.Transaksi.Model.Transaksi;
import com.example.sales.UtilsApi.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.viewHolder> {

    Context context;
    List<Transaksi>transaksi;

    public TransaksiAdapter(List<Transaksi> transaksi) {
        this.transaksi = transaksi;
    }

    @NonNull
    @Override
    public TransaksiAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transaksi, parent, false);
        TransaksiAdapter.viewHolder holder = new TransaksiAdapter.viewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.viewHolder holder, int position) {
        final Transaksi model = transaksi.get(position);
        holder.txtNamaBarang.setText(model.getNama());
        holder.txtHargaBarang.setText(model.getHarga());



        holder.rcAdd.setHasFixedSize(true);
        holder.rcAdd.setNestedScrollingEnabled(false);
        holder.rcAdd.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.btnAdd.setVisibility(View.GONE);
                holder.delete.setVisibility(View.VISIBLE);
                getAddRow(holder, model.getId_s(), holder.adds);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.rcAdd.setVisibility(View.GONE);
                holder.btnAdd.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.GONE);
            }
        });


    }

    private void getAddRow(viewHolder holder, String id_s, List<Add> adds) {
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
                                JSONObject isi = response.getJSONObject("stokdata");
                                JSONArray d = isi.getJSONArray("isi");
                                for (int i =0; i<d.length(); i++){
                                    String data = d.getString(i);
                                    Log.d("ulang", "data : "+data);
                                    adds.add(new Add(
                                            data
                                    ));
                                }

                                AddAdapter addAdapter = new AddAdapter(adds);
                                holder.rcAdd.setAdapter(addAdapter);
                                addAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("eror","code :"+anError);
                    }
                });


        holder.rcAdd.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return transaksi.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btnAdd)
        TextView btnAdd;

        @BindView(R.id.rvAdd)
        RecyclerView rcAdd;

        @BindView(R.id.banyakBarang)
        TextView banyakBarang;
        @BindView(R.id.txtNamaBarang)
        TextView txtNamaBarang;
        @BindView(R.id.txtHargaBarang)
        TextView txtHargaBarang;

        @BindView(R.id.deleteItem)
        ImageView delete;

        List<Add> adds;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            context = itemView.getContext();
            adds = new ArrayList<>();
        }
    }
}

