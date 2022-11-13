package com.caowei.utowndemo.space;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caowei.utowndemo.lib.ColorUtils;
import com.caowei.utowndemo.R;

import java.util.ArrayList;
import java.util.List;

public class SingleView extends LinearLayout {
    public SingleView(Context context) {
        super(context);
        initView();
    }

    public SingleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SingleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public SingleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.item_view_space_single, null);
        addView(rootView);
        initSingleRecyclerView();
    }

    private void initSingleRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(String.valueOf(i));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        SingleAdapter singleAdapter = new SingleAdapter(data);
        recyclerView.setAdapter(singleAdapter);
    }

    private static class SingleAdapter extends RecyclerView.Adapter<SingleAdapter.ViewHolder> {
        private final List<String> data = new ArrayList<>();

        public SingleAdapter(List<String> data) {
            this.data.clear();
            this.data.addAll(data);
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tv_content);
            }
        }


        @NonNull
        @Override
        public SingleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_card, parent, false);
            return new SingleAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SingleAdapter.ViewHolder holder, int position) {
            holder.textView.setText(data.get(position));
            holder.textView.setBackgroundColor(ColorUtils.getRandomColor());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
