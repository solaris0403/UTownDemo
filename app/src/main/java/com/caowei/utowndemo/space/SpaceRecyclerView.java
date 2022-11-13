package com.caowei.utowndemo.space;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.blankj.utilcode.util.ColorUtils;
import com.caowei.utowndemo.R;

import java.util.ArrayList;
import java.util.List;

public class SpaceRecyclerView extends RecyclerView {
    private static final String TAG = SpaceRecyclerView.class.getSimpleName();
    public SpaceRecyclerView(@NonNull Context context) {
        super(context);
        initViews();
    }

    public SpaceRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public SpaceRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add(String.valueOf(i));
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position < 4){
                    return 2;
                }else{
                    return 1;
                }
            }
        });
        setLayoutManager(gridLayoutManager);
        setAdapter(new SpaceAdapter(data));
    }

    private static class SpaceAdapter extends Adapter<ViewHolder> {
        private List<String> data = new ArrayList<>();

        public SpaceAdapter(List<String> data) {
            this.data.clear();
            this.data.addAll(data);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ViewHolder viewHolder = null;
            switch (viewType) {
                case 0:
                    viewHolder = new CardViewHolder(new CardView(parent.getContext()));
                    break;
                case 1:
                    viewHolder = new BannerViewHolder(new BannerView(parent.getContext()));
                    break;
                case 2:
                    viewHolder = new SingleHolder(new SingleView(parent.getContext()));
                    break;
                case 3:
                    viewHolder = new MultiViewHolder(new MultiView(parent.getContext()));
                    break;
                case 4:
                    viewHolder = new TitleViewHolder(new TitleView(parent.getContext()));
                    break;
                default:
                    break;
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (holder instanceof CardViewHolder){
                ((CardViewHolder) holder).textView.setText(data.get(position));
                ((CardViewHolder) holder).textView.setBackgroundColor(ColorUtils.getRandomColor());
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            int viewType = 0;
            switch (position) {
                case 0:
                    viewType = 1;
                    break;
                case 1:
                    viewType = 2;
                    break;
                case 2:
                    viewType = 3;
                    break;
                case 3:
                    viewType = 4;
                    break;
                case 4:
                    viewType = 0;
                    break;
                default:
                    viewType = 0;
            }
            return viewType;
        }
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class SingleHolder extends RecyclerView.ViewHolder {
        public SingleHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class MultiViewHolder extends RecyclerView.ViewHolder {
        public MultiViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder {
        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
