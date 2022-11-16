package com.caowei.utowndemo.space;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ColorUtils;
import com.caowei.utowndemo.R;
import com.caowei.utowndemo.lib.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

public class BannerView extends LinearLayout {
    private static final String TAG = BannerView.class.getSimpleName();


    public BannerView(Context context) {
        super(context);
        initView();
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.item_view_space_banner, null);
        addView(rootView);
        initBannerViewPager();
    }


    @SuppressLint("ResourceType")
    private void initBannerViewPager() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(String.valueOf(i));
        }
        ViewPager viewPager = findViewById(R.id.viewPager);
        BannerAdapter adapter = new BannerAdapter(getContext(), data);
        viewPager.setAdapter(adapter);
        ViewGroup viewGroup = findViewById(R.id.banner_indicator);
        for (int i = 0; i < data.size(); i++) {
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
            viewGroup.addView(imageView);
        }
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    ImageView imageView = (ImageView) viewGroup.getChildAt(i);
                    imageView.setAlpha(position == i ? 1f : 0.5f);
                }
//                getRootView().getHandler().removeCallbacksAndMessages(null);
//                getRootView().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (viewPager.getCurrentItem() == adapter.getCount() - 1) {
//                            viewPager.setCurrentItem(0, true);
//                        } else {
//                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
//                        }
//                    }
//                }, 5000);
            }
        });
//        getRootView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (viewPager.getCurrentItem() == adapter.getCount() - 1) {
//                    viewPager.setCurrentItem(0, true);
//                } else {
//                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
//                }
//            }
//        }, 5000);
        adapter.setViewPageEvent(new BannerAdapter.IViewPageEvent() {
            @Override
            public void onClick(int position) {
                Log.d(TAG, "onClick:" + position);
            }

            @Override
            public void onInstantiateItem(int position, View view) {
                Log.d(TAG, "onInstantiateItem:" + position);
            }
        });
    }

    private static class BannerAdapter extends PagerAdapter {
        private Context context;
        private List<String> data;
        private IViewPageEvent mViewPageEvent;

        public BannerAdapter(Context context, List<String> data) {
            this.context = context;
            this.data = data;
        }

        public void setViewPageEvent(IViewPageEvent viewPageEvent) {
            this.mViewPageEvent = viewPageEvent;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_viewpager_card, container, false);
            if (mViewPageEvent != null) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPageEvent.onClick(position);
                    }
                });
                mViewPageEvent.onInstantiateItem(position, view);
            }
            view.setBackgroundColor(ColorUtils.getRandomColor());
            container.addView(view);
            return view;
        }

        public interface IViewPageEvent {
            void onClick(int position);

            void onInstantiateItem(int position, View view);
        }
    }
}
