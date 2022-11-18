package com.caowei.utowndemo.space;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
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
        initView();
    }

    public SpaceRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SpaceRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add(String.valueOf(i));
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        addItemDecoration(new SpaceItemDecoration(getContext()));
        setLayoutManager(layoutManager);
        setHasFixedSize(true);
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
                ((androidx.cardview.widget.CardView)((CardViewHolder) holder).cardView).setCardBackgroundColor(ColorUtils.getRandomColor());
                StringBuilder sb = new StringBuilder("a");
                if ((position % 2) == 0){
                    sb.append(position + "dddddwww");
                }else if(position % 2 == 1){
                    sb.append(position + "dddddwwwwwwwdddddddddddddddwwwwwwwdddddddddddddddwwwwwwwdddddddddddddddwwwwwwwdddddddddddddddwwwwwwwdddddddddd");
                }else{
                    sb.append("");
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
//        public final View placeholderRight;
//        public final View placeholderLeft;
        public final View container;
        public final View cardView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_space_name);
            container = itemView.findViewById(R.id.container);
            cardView = itemView.findViewById(R.id.card_view);
//            placeholderLeft = itemView.findViewById(R.id.placeholder_left);
//            placeholderRight = itemView.findViewById(R.id.placeholder_right);
        }
    }

    static class SpaceItemDecoration extends RecyclerView.ItemDecoration{
        private Context context;

        public SpaceItemDecoration(Context context){
            this.context = context;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int position = parent.getChildAdapterPosition(view);
            if (position > 3){
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                int spanIndex = params.getSpanIndex();
                Log.e(TAG, "getItemOffsets:" + spanIndex);
                if (spanIndex % 2 == 0 ){
                    //左边
                    outRect.left = SizeUtils.dip2px(context, 16);
                    outRect.right = SizeUtils.dip2px(context, 4);
                }else{
                    //右边
                    outRect.left = SizeUtils.dip2px(context, 4);
                    outRect.right = SizeUtils.dip2px(context, 16);
                }
            }
        }
    }
}
