package com.caowei.utowndemo.now;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.ColorUtils;
import com.caowei.utowndemo.R;

import java.util.ArrayList;
import java.util.List;

public class NowRecyclerView extends RecyclerView {
    public NowRecyclerView(@NonNull Context context) {
        super(context);
        initView();
    }

    public NowRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public NowRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add(String.valueOf(i));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(linearLayoutManager);
        setAdapter(new NowRecyclerView.NowAdapter(data));
    }


    private static class NowAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<String> data = new ArrayList<>();

        public NowAdapter(List<String> data) {
            this.data.clear();
            this.data.addAll(data);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_now_card, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < position % 3; i++) {
                sb.append("dddd");
            }
            holder.tvContent.setVisibility(TextUtils.isEmpty(sb.toString()) ? View.GONE : View.VISIBLE);
            holder.tvContent.setText(sb.toString());
            holder.imgBigAvatar.setBackgroundColor(ColorUtils.getRandomColor());
            holder.imgSmallAvatar.setBackgroundColor(ColorUtils.getRandomColor());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvContent;
        public ImageView imgBigAvatar;
        public ImageView imgSmallAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
            imgBigAvatar = itemView.findViewById(R.id.img_big_avatar);
            imgSmallAvatar = itemView.findViewById(R.id.img_small_avatar);
        }
    }
}
