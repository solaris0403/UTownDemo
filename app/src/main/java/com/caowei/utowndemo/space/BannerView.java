package com.caowei.utowndemo.space;

import android.content.Context;
import android.os.Handler;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 顶部Banner
 */
public class BannerView extends LinearLayout {
    private static final String TAG = BannerView.class.getSimpleName();
    private Timer mTimer;
    private TimerTask mTimerTask;
    private ViewPager viewPager;
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
        this.addView(rootView);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(String.valueOf(i));
        }
        viewPager = findViewById(R.id.viewPager);
        PagerIndicator pagerIndicator = findViewById(R.id.banner_indicator);
        pagerIndicator.attachViewPager(viewPager);
        BannerAdapter adapter = new BannerAdapter(getContext(), data);
        viewPager.setAdapter(adapter);
        adapter.setViewPageEvent(new BannerAdapter.IViewPageEvent() {
            @Override
            public void onClick(int position) {
                Log.d(TAG, "onClick:" + position);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
//                start();
            }
        });
//        start();
    }

    private void start(){
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                autoPage();
            }
        };
    }

    private void autoPage(){
        int position = (viewPager.getCurrentItem() + 1) % viewPager.getAdapter().getCount();
        viewPager.setCurrentItem(position, true);
    }

    private static class BannerAdapter extends PagerAdapter {
        private final Context context;
        private final List<String> data;
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
            ImageView imageView = view.findViewById(R.id.img_banner);
            imageView.setBackgroundColor(ColorUtils.getRandomColor());
            if (mViewPageEvent != null) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPageEvent.onClick(position);
                    }
                });
            }
            container.addView(view);
            return view;
        }

        public interface IViewPageEvent {
            void onClick(int position);
        }
    }
}
