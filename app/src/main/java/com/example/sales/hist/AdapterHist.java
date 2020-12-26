package com.example.sales.hist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        final ModelHist model = hists.get(position);
        holder.invoice.setText(model.getInvoice_id());
        holder.tgl.setText(model.getInvoice_date());
        holder.harga.setText(formatRupiah.format(model.getTotalsales()));
    }

    @Override
    public int getItemCount() {
        return hists.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView invoice, tgl, harga;
        RelativeLayout card;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            invoice = itemView.findViewById(R.id.invoice);
            tgl = itemView.findViewById(R.id.tglHist);
            harga = itemView.findViewById(R.id.hargaHist);
        }
    }
}
