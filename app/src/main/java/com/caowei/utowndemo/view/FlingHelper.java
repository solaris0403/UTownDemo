package com.caowei.utowndemo.view;

import android.content.Context;
import android.view.ViewConfiguration;

/**
 * 惯性计算
 */
public class FlingHelper {
    private static final float DECELERATION_RATE = ((float) (Math.log(0.78d) / Math.log(0.9d)));
    private static final float mFlingFriction = ViewConfiguration.getScrollFriction();
    private static float mPhysicalCoeff;

    public FlingHelper(Context context) {
        mPhysicalCoeff = context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
    }

    private double getSplineDeceleration(int i) {
        return Math.log((double) ((0.35f * ((float) Math.abs(i))) / (mFlingFriction * mPhysicalCoeff)));
    }

    private double getSplineDecelerationByDistance(double d) {
        return ((((double) DECELERATION_RATE) - 1.0d) * Math.log(d / ((double) (mFlingFriction * mPhysicalCoeff)))) / ((double) DECELERATION_RATE);
    }

    //通过初始速度获取最终滑动距离
    public double getSplineFlingDistance(int velocity) {
        return Math.exp(getSplineDeceleration(velocity) * (((double) DECELERATION_RATE) / (((double) DECELERATION_RATE) - 1.0d))) * ((double) (mFlingFriction * mPhysicalCoeff));
    }

    //通过需要滑动的距离获取初始速度
    public int getVelocityByDistance(double distance) {
        return Math.abs((int) (((Math.exp(getSplineDecelerationByDistance(distance)) * ((double) mFlingFriction)) * ((double) mPhysicalCoeff)) / 0.3499999940395355d));
    }
}
