package com.caowei.utowndemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caowei.utowndemo.utils.ColorUtils;

import java.util.List;

public class AttitudeAdapter extends RecyclerView.Adapter<AttitudeAdapter.AttitudeViewHolder> {
    private final Context context;
    private final List<String> data;

    public AttitudeAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AttitudeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_detail_card, parent, false);
        return new AttitudeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttitudeViewHolder holder, int position) {
        holder.tvName.setText(this.data.get(position));
        holder.tvLevel.setBackgroundColor(ColorUtils.getRandomColor());
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    static class AttitudeViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgAvatar;
        public TextView tvName;
        public TextView tvTime;
        public TextView tvLevel;

        public AttitudeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvLevel = itemView.findViewById(R.id.tv_level);
        }
    }
}
