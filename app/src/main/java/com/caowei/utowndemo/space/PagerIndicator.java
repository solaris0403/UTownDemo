package com.caowei.utowndemo.space;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.caowei.utowndemo.R;
import com.caowei.utowndemo.utils.SizeUtils;

/**
 * Banner切换图片时候底部指示器联动
 */
public class PagerIndicator extends LinearLayout {
    private static final String TAG = PagerIndicator.class.getSimpleName();

    public PagerIndicator(Context context) {
        super(context);
        initView();
    }

    public PagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public PagerIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutParams);
        }
    }

    public void attachViewPager(ViewPager viewPager) {
        viewPager.addOnAdapterChangeListener(new ViewPager.OnAdapterChangeListener() {
            @Override
            public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
                initIndicator(viewPager);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateIndex(position);
            }
        });
        initIndicator(viewPager);
    }

    private void initIndicator(ViewPager viewPager) {
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null) {
            removeAllViews();
            for (int i = 0; i < adapter.getCount(); i++) {
                ImageView imageView = new ImageView(getContext());
                imageView.setBackgroundResource(R.drawable.bg_banner_indicator);
                LayoutParams lp = new LayoutParams(SizeUtils.dip2px(getContext(), 8), SizeUtils.dip2px(getContext(), 2));
                lp.leftMargin = SizeUtils.dip2px(getContext(), 2);
                lp.rightMargin = SizeUtils.dip2px(getContext(), 2);
                imageView.setLayoutParams(lp);
                imageView.setAlpha(0.5f);
                if (i == 0) {
                    imageView.setAlpha(1f);
                }
                this.addView(imageView);
            }
            updateIndex(viewPager.getCurrentItem());
        }
    }

    private void updateIndex(int index) {
        for (int i = 0; i < getChildCount(); i++) {
            ImageView imageView = (ImageView) getChildAt(i);
            imageView.setAlpha(index == i ? 1f : 0.5f);
        }
    }
}
