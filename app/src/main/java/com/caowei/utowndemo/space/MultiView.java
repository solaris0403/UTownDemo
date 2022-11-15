package com.caowei.utowndemo.space;

import android.content.Context;
import android.graphics.Rect;
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
import com.caowei.utowndemo.lib.utils.SizeUtils;

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
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(String.valueOf(i));
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.addItemDecoration(new MultiItemDecoration(getContext()));
        recyclerView.setLayoutManager(gridLayoutManager);
        MultiAdapter multiAdapter = new MultiAdapter(getContext(), data);
        recyclerView.setAdapter(multiAdapter);
    }

    private static class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.ViewHolder> {
        private List<String> data = new ArrayList<>();
        private Context context;
        public MultiAdapter(Context context, List<String> data) {
            this.context = context;
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
            if (position == 0 || position == 1){
                holder.placeholderLeft.getLayoutParams().width = SizeUtils.dip2px(context, 16);
            }else{
                holder.placeholderLeft.getLayoutParams().width = SizeUtils.dip2px(context, 0);
            }
            holder.placeholderLeft.setLayoutParams(holder.placeholderLeft.getLayoutParams());
//
//            if (position % 2 == 0){
//                holder.placeholderTop.getLayoutParams().height = SizeUtils.dip2px(context, 0);
//            }else{
//                holder.placeholderTop.getLayoutParams().height = SizeUtils.dip2px(context, 16);
//            }
//            holder.placeholderTop.setLayoutParams(holder.placeholderTop.getLayoutParams());

            holder.container.setBackgroundColor(ColorUtils.getRandomColor());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public View placeholderLeft;
            public View placeholderTop;
            public View container;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                container = itemView.findViewById(R.id.container);
//                placeholderTop = itemView.findViewById(R.id.placeholder_top);
                placeholderLeft = itemView.findViewById(R.id.placeholder_left);
            }
        }
    }

    private static class MultiItemDecoration extends RecyclerView.ItemDecoration{
        private Context context;
        public MultiItemDecoration(Context context) {
            this.context = context;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = SizeUtils.dip2px(context, 16);
            outRect.right = SizeUtils.dip2px(context, 16);
        }
    }
}
