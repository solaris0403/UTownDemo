package com.caowei.utowndemo;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.caowei.utowndemo.view.StickyNestedScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private ViewPager2 viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView btnSpace = findViewById(R.id.btn_space);
        TextView btnNow = findViewById(R.id.btn_now);
        TextView btnReset = findViewById(R.id.btn_reset);
        btnSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0, true);
            }
        });
        btnNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1, true);
            }
        });
        StickyNestedScrollView scrollView = findViewById(R.id.scroll_view);
        scrollView.setStickyPin(true);
        scrollView.setOnStickyListener(new StickyNestedScrollView.OnStickyListener() {
            @Override
            public void onSticky(View view, float state) {
                if (state == 1){
                    btnReset.setVisibility(View.VISIBLE);
                }else{
                    btnReset.setVisibility(View.GONE);
                }
            }
        });
        SpaceFragment spaceFragment = new SpaceFragment();
        NowFragment nowFragment = new NowFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(spaceFragment);
        fragments.add(nowFragment);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(new FragmentAdapter(this, fragments));
    }
}
