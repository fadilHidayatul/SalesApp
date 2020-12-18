package com.example.sales.Transaksi;

import android.content.Context;
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
import com.example.sales.Transaksi.Model.Transaksi;

import java.util.List;

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.ListViewHolder> {
    Context context;
    List<Add> adds;

    public AddAdapter(List<Add> adds) {
        this.adds = adds;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add, parent, false);
        AddAdapter.ListViewHolder holder = new AddAdapter.ListViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final Add model = adds.get(position);
        holder.qty.setText(model.getText());

        holder.tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int banyak = Integer.valueOf(holder.qty.getText().toString())+1;
                holder.jml.setText(banyak);
            }
        });

        holder.kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(holder.jml.getText().toString())>=0){
                    int banyak = Integer.valueOf(holder.qty.getText().toString())-1;
                    holder.jml.setText(banyak);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return adds.size()  ;
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
