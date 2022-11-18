package com.caowei.utowndemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AttitudeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
    }

    private void initView() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add(String.valueOf(i));
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        AttitudeAdapter attitudeAdapter = new AttitudeAdapter(this, data);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(attitudeAdapter);
    }
}