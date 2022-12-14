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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caowei.utowndemo.utils.ColorUtils;
import com.caowei.utowndemo.R;
import com.caowei.utowndemo.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 单行
 */
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
        this.addView(rootView);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(String.valueOf(i));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        SingleAdapter singleAdapter = new SingleAdapter(getContext(), data);
        recyclerView.setAdapter(singleAdapter);
    }

    private static class SingleAdapter extends RecyclerView.Adapter<SingleAdapter.ViewHolder> {
        private final List<String> data = new ArrayList<>();
        private Context context;

        public SingleAdapter(Context context, List<String> data) {
            this.context = context;
            this.data.clear();
            this.data.addAll(data);
        }


        @NonNull
        @Override
        public SingleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_card, parent, false);
            return new SingleAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SingleAdapter.ViewHolder holder, int position) {
            if (position == 0) {
                holder.placeholderLeft.getLayoutParams().width = SizeUtils.dip2px(context, 16);
                holder.placeholderLeft.setLayoutParams(holder.placeholderLeft.getLayoutParams());
            } else {
                holder.placeholderLeft.getLayoutParams().width = SizeUtils.dip2px(context, 0);
                holder.placeholderLeft.setLayoutParams(holder.placeholderLeft.getLayoutParams());
            }
            holder.spaceCover.setBackgroundColor(ColorUtils.getRandomColor());
//            holder.content.setBackgroundColor(Color.WHITE);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final CardView cardView;
            public View placeholderLeft;
            public View placeholderRight;
            public TextView content;
            public View spaceCover;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                placeholderLeft = itemView.findViewById(R.id.placeholder_left);
                placeholderRight = itemView.findViewById(R.id.placeholder_right);
                cardView = itemView.findViewById(R.id.card_view);
                content = itemView.findViewById(R.id.content);
                spaceCover = itemView.findViewById(R.id.lyt_space_cover);
            }
        }
    }
}
