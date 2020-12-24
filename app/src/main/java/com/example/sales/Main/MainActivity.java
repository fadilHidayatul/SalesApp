package com.example.sales.Main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sales.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    MainAdapter adapter;
    @BindView(R.id.recycler_main)
    RecyclerView recyclerMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String[] data = {"Transaksi", "Sales", "Test1", "Test2"};

        recyclerMain.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new MainAdapter(this,data);
        recyclerMain.setAdapter(adapter);
        recyclerMain.setHasFixedSize(true);
        recyclerMain.setNestedScrollingEnabled(false);



    }
}
