package com.caowei.utowndemo.space;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.caowei.utowndemo.R;

public class TitleView extends LinearLayout {
    public TitleView(Context context) {
        super(context);
        initView();
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }


    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.item_view_space_title, null);
        addView(rootView);
    }
}
