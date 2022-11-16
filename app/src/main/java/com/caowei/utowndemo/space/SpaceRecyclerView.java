package com.caowei.utowndemo.space;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.blankj.utilcode.util.ColorUtils;
import com.caowei.utowndemo.R;
import com.caowei.utowndemo.utils.SizeUtils;

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
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        setLayoutManager(layoutManager);
        setAdapter(new SpaceAdapter(getContext(), data));
    }

    public static class SpaceAdapter extends Adapter<ViewHolder> {
        private List<String> data = new ArrayList<>();
        private Context context;

        public SpaceAdapter(Context context, List<String> data) {
            this.context = context;
            this.data.clear();
            this.data.addAll(data);
        }

        @Override
        public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) lp;
                params.setFullSpan(holder.getItemViewType() != 0);
            }
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
                    viewHolder = new SectionViewHolder(new SectionView(parent.getContext()));
                    break;
                default:
                    break;
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (holder instanceof CardViewHolder) {
                if (position % 2 == 0) {
                    ((CardViewHolder) holder).placeholderLeft.getLayoutParams().width = SizeUtils.dip2px(context, 16);
                    ((CardViewHolder) holder).placeholderLeft.setLayoutParams(((CardViewHolder) holder).placeholderLeft.getLayoutParams());
                    ((CardViewHolder) holder).placeholderRight.getLayoutParams().width = SizeUtils.dip2px(context, 4);
                    ((CardViewHolder) holder).placeholderRight.setLayoutParams(((CardViewHolder) holder).placeholderRight.getLayoutParams());
                } else {
                    ((CardViewHolder) holder).placeholderLeft.getLayoutParams().width = SizeUtils.dip2px(context, 4);
                    ((CardViewHolder) holder).placeholderLeft.setLayoutParams(((CardViewHolder) holder).placeholderLeft.getLayoutParams());
                    ((CardViewHolder) holder).placeholderRight.getLayoutParams().width = SizeUtils.dip2px(context, 16);
                    ((CardViewHolder) holder).placeholderRight.setLayoutParams(((CardViewHolder) holder).placeholderRight.getLayoutParams());
                }
                ((androidx.cardview.widget.CardView)((CardViewHolder) holder).cardView).setCardBackgroundColor(ColorUtils.getRandomColor());
                StringBuilder sb = new StringBuilder("a");
                for (int i = 0; i < (position % 4) ; i++) {
                    sb.append("dddddwwwwwwwdd");
                }
                ((CardViewHolder) holder).tvName.setText(sb.toString());
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

    static class SectionViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvTitle;
        public TextView mTvSubtitle;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvSubtitle = itemView.findViewById(R.id.tv_subtitle);
        }
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvName;
        public final View placeholderRight;
        public final View placeholderLeft;
        public final View container;
        public final View cardView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_space_name);
            container = itemView.findViewById(R.id.container);
            cardView = itemView.findViewById(R.id.card_view);
            placeholderLeft = itemView.findViewById(R.id.placeholder_left);
            placeholderRight = itemView.findViewById(R.id.placeholder_right);
        }
    }
}
