package com.example.sales.hist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sales.History.AdapterHistori;
import com.example.sales.History.ModelHistori;
import com.example.sales.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterHist extends RecyclerView.Adapter<AdapterHist.ListViewHolder> {
    Context context;
    List<ModelHist>hists;

    public AdapterHist(List<ModelHist> hists) {
        this.hists = hists;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_hist, parent, false);
        AdapterHist.ListViewHolder holder = new AdapterHist.ListViewHolder(v);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        final ModelHist model = hists.get(position);
        String harga = formatRupiah.format(Integer.parseInt(model.getTotalsales()));
        String tgl = model.getInvoice_date().substring(8,10);
        String bln = model.getInvoice_date().substring(5,7);
        String thn = model.getInvoice_date().substring(0,4);
        holder.invoice.setText(model.getInvoice_id());
        holder.tgl.setText(tgl+"-"+bln+"-"+thn);
        holder.harga.setText(harga.substring(2));
        holder.status.setText(model.getStatus());
    }

    @Override
    public int getItemCount() {
        return hists.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView invoice, tgl, harga, status;
        RelativeLayout card;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            invoice = itemView.findViewById(R.id.invoice);
            tgl = itemView.findViewById(R.id.tglHist);
            harga = itemView.findViewById(R.id.hargaHist);
            status = itemView.findViewById(R.id.status);
        }
    }
}
