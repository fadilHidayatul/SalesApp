package com.example.sales.Transaksi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sales.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.viewHolder> {
    int count = 0;

    private Context context;

    public TransaksiAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TransaksiAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_transaksi,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.viewHolder holder, int position) {
        holder.txtNamaBarang.setText("Nama Barang");
        holder.txtHargaBarang.setText("Rp.999.999.999");
        holder.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.layout.setVisibility(View.VISIBLE);
                holder.btnTambah.setVisibility(View.GONE);
                holder.banyakBarang.setText("1");
                count = 1;
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(holder.banyakBarang.getText().toString()) <= 1){
                    holder.btnTambah.setVisibility(View.VISIBLE);
                    holder.layout.setVisibility(View.GONE);
                }else{
                    count = count-1;
                    Toast.makeText(context, ""+count, Toast.LENGTH_SHORT).show();
                    holder.banyakBarang.setText(""+count);
                }

            }
        });

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count+1;
                Toast.makeText(context, ""+count, Toast.LENGTH_SHORT).show();
                holder.banyakBarang.setText(""+count);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btnTambah)
        Button btnTambah;
        @BindView(R.id.add)
        Button btnAdd;
        @BindView(R.id.minus)
        Button btnMinus;

        @BindView(R.id.banyakBarang)
        TextView banyakBarang;
        @BindView(R.id.txtNamaBarang)
        TextView txtNamaBarang;
        @BindView(R.id.txtHargaBarang)
        TextView txtHargaBarang;

        @BindView(R.id.linearTambahKurang)
        LinearLayout layout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
