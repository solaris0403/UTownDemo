package com.caowei.utowndemo.now;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
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
import com.caowei.utowndemo.AttitudeActivity;
import com.caowei.utowndemo.R;

import java.util.ArrayList;
import java.util.List;

public class NowRecyclerView extends RecyclerView {
    private static final String TAG = NowRecyclerView.class.getSimpleName();
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
        setAdapter(new NowRecyclerView.NowAdapter(getContext(), data));
    }


    private static class NowAdapter extends RecyclerView.Adapter<ViewHolder> {
        private final List<String> data = new ArrayList<>();
        private final Context context;
        public NowAdapter(Context context, List<String> data) {
            this.context = context;
            this.data.clear();
            this.data.addAll(data);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_now_card, parent, false);
            return new ViewHolder(itemView);
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
            holder.imgAttitude.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "imgAttitude:" + position);
                    context.startActivity(new Intent(context, AttitudeActivity.class));
                }
            });
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
        public ImageView imgAttitude;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
            imgAttitude = itemView.findViewById(R.id.img_attitude);
            imgBigAvatar = itemView.findViewById(R.id.img_big_avatar);
            imgSmallAvatar = itemView.findViewById(R.id.img_small_avatar);
        }
    }
}
