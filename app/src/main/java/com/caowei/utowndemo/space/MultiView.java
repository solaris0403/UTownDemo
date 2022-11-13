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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caowei.utowndemo.lib.ColorUtils;
import com.caowei.utowndemo.R;

import java.util.ArrayList;
import java.util.List;

public class MultiView extends LinearLayout {
    public MultiView(Context context) {
        super(context);
        initView();
    }

    public MultiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MultiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public MultiView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.item_view_space_multi, null);
        addView(rootView);
        initMultiRecyclerView();
    }

    private void initMultiRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(String.valueOf(i));
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        MultiAdapter multiAdapter = new MultiAdapter(data);
        recyclerView.setAdapter(multiAdapter);
    }

    private static class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.ViewHolder> {
        private List<String> data = new ArrayList<>();

        public MultiAdapter(List<String> data) {
            this.data.clear();
            this.data.addAll(data);
        }

        @NonNull
        @Override
        public MultiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_multi_card, parent, false);
            return new MultiAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MultiAdapter.ViewHolder holder, int position) {
            holder.textView.setText(data.get(position));
            holder.textView.setBackgroundColor(ColorUtils.getRandomColor());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);
            }
        }
    }
}
