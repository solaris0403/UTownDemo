package com.caowei.utowndemo.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.caowei.utowndemo.R;

import java.util.Objects;

/**
 * 1. NestedScrollView嵌套RecyclerView
 * 2. NestedScrollView先行滑动，当达到吸附状态时再进行RecyclerView滑动
 */
public class StickyNestedScrollView extends NestedScrollView {
    private static final String TAG = StickyNestedScrollView.class.getSimpleName();
    private ViewGroup mHeadView;//顶部控件，需要被隐藏的控件
    private ViewGroup mContentView;//内容控件，通常是吸顶状态的视图
    private int mVelocityY;//惯性滚动速度
    private FlingHelper mFlingHelper;//处理惯性滑动速度与距离的转化
    private int mConsumedY;//记录自身已经滚动的距离
    private boolean isNeedPin = false;//是否需要固定吸附的控件
    private boolean hasPined = false;
    private OnStickyListener mStickyListener;//吸附状态监听器
    private int mHeadOriginHeight;//顶部控件高度

    @RequiresApi(api = Build.VERSION_CODES.M)
    public StickyNestedScrollView(@NonNull Context context) {
        super(context);
        initViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public StickyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public StickyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initViews() {
        mFlingHelper = new FlingHelper(getContext());
        post(new Runnable() {
            @Override
            public void run() {
                mHeadOriginHeight = mHeadView.getMeasuredHeight();
            }
        });
        setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //scrollY == 0 即还未滚动
                //v.getMeasuredHeight() = NestedScrollView的固定高度
                //getChildAt(0).getMeasuredHeight()是NestedScrollView内 mHeadView + mContentView 的高度
                //scrollY == getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()即滚动到顶部了
                //判断NestedScrollView是否滚动到顶部，若滚动到顶部，判断子控件是否需要继续滚动滚动
//                if (scrollY == getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                    //达到吸附状态之后分发惯性到子控件
//                    dispatchChildFling();
//                }
                if (scrollY == mHeadView.getMeasuredHeight()) {
                    //达到吸附状态之后分发惯性到子控件
                    dispatchChildFling();
                }
                //累加自身滚动的距离
                mConsumedY += scrollY - oldScrollY;

                //tab高度
                int tabHeight = mContentView.getChildAt(0).getMeasuredHeight();
                float state = 0;
                if (scrollY <= (mHeadView.getMeasuredHeight() - tabHeight)) {
                    hasPined = false;
                    state = 0;
                } else if (scrollY > (mHeadView.getMeasuredHeight() - tabHeight) && scrollY < mHeadView.getMeasuredHeight()) {
                    //进入吸附阶段
                    state = (scrollY - (mHeadView.getMeasuredHeight() - tabHeight)) / (float) tabHeight;
                } else if (scrollY >= mHeadView.getMeasuredHeight()) {
                    state = 1;
                    hasPined = true;
                }

                if (mStickyListener != null) {
                    mStickyListener.onSticky(mContentView.getChildAt(0), state);
                }
                //固定之后将Head隐藏，为了下拉不会把Head拉下来
                if (isNeedPin && hasPined){
                    mHeadView.getLayoutParams().height = 0;
                    mHeadView.setLayoutParams(mHeadView.getLayoutParams());
                }
            }
        });
        setOnStickyListener(new OnStickyListener() {
            @Override
            public void onSticky(View view, float state) {
                Log.d(TAG, "onSticky:" + state);
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeadView = (ViewGroup) ((ViewGroup) getChildAt(0)).getChildAt(0);
        mContentView = (ViewGroup) ((ViewGroup) getChildAt(0)).getChildAt(1);
        mContentView.findViewById(R.id.btn_reset).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSticky();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //将内容布局的高度设置为NestedScrollView的高度，即滑到顶了，显示为吸顶状态
        ViewGroup.LayoutParams layoutParams = mContentView.getLayoutParams();
        layoutParams.height = getMeasuredHeight();
        mContentView.setLayoutParams(layoutParams);
        //如果mContentView包含两个child，则说明第一个为需要固定的view，则底下的列表高度=mContentView-child(0)
        if (mContentView.getChildCount() == 2) {
            ViewGroup.LayoutParams lp = mContentView.getChildAt(1).getLayoutParams();
            lp.height = getMeasuredHeight() - mContentView.getChildAt(0).getMeasuredHeight();
            mContentView.getChildAt(1).setLayoutParams(lp);
        }
    }

    /**
     * 子控件触发dispatchNestedPreScroll时会先调用父控件的onNestedPreScroll，
     * 再执行自身的dispatchNestedScroll
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        //由于NestedScrollView既实现了NestedScrollingParent又实现了NestedScrollingChild，会出现NestedScrollView无法滑动到顶部，
        //所以不能调用super，只需要实现当前NestedScrollView的滑动
        //向上滚动并且滚动的距离小于mHeadView控件的高度，则此时NestedScrollView先滚动并记录消费的滚动距离
        boolean hideHead = dy > 0 && getScrollY() < mHeadView.getMeasuredHeight();
        if (hideHead) {
            //滚动到相应的滑动距离
            scrollBy(dx, dy);
            //记录父控件消费的滚动记录，防止子控件重复滚动
            consumed[1] = dy;
        }
    }

    /**
     * 惯性滑动，父控件在滑动完成后，在通知子控件滑动,此时不是嵌套滚动
     * 解决方法:
     * 1.记录惯性滑动的速度
     * 2.将速度转化成距离
     * 3.计算子控件应该滑动的距离 = 惯性滑动距离 - 已滑距离
     * 4.将剩余滑动距离转化成速度交给子控件进行惯性滑动
     */
    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        //记录NestedScrollView惯性滚动的速度，在吸顶之后传递给RecyclerView继续滚动
        mVelocityY = Math.max(0, velocityY);
    }

    //将惯性滑动剩余的距离分发给子控件，继续惯性滑动
    private void dispatchChildFling() {
        if (mVelocityY != 0) {
            //将惯性滑动速度转化成距离
            double distance = mFlingHelper.getSplineFlingDistance(mVelocityY);
            //计算子控件应该滑动的距离 = 惯性滑动距离 - 已滑距离
            if (distance > mConsumedY) {
                long startTime = System.currentTimeMillis();
                RecyclerView recyclerView = getChildRecyclerView(mContentView);
                long endTime = System.currentTimeMillis();
                Log.d(TAG, "getChildRecyclerView period:" + (endTime - startTime));
                if (recyclerView != null) {
                    //将剩余滑动距离转化成速度交给子控件进行惯性滑动
                    int velocityY = mFlingHelper.getVelocityByDistance(distance - mConsumedY);
                    recyclerView.fling(0, velocityY);
                }
            }
        }
        //一次滑动完成之后重置
        mConsumedY = 0;
        mVelocityY = 0;
    }

    //递归获取ViewGroup当中的RecyclerView
    // TODO: 2022/11/13 需要优化，如果有多个RecyclerView切换显示，那么需要获取到当前的RecyclerView
    private RecyclerView getChildRecyclerView(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof RecyclerView && Objects.requireNonNull(((RecyclerView) view).getLayoutManager()).canScrollVertically()) {
                return (RecyclerView) view;
            } else if (view instanceof ViewGroup) {
                //递归获取子控件RecyclerView
                RecyclerView recyclerView = getChildRecyclerView((ViewGroup) view);
                if (recyclerView != null && Objects.requireNonNull((recyclerView).getLayoutManager()).canScrollVertically()) {
                    return recyclerView;
                }
            }
        }
        return null;
    }


    //设置是否达到吸附状态之后需要固定
    public void setStickyPin(boolean pin) {
        isNeedPin = pin;
    }

    //重置到最开始的状态
    public void resetSticky(){
        mHeadView.getLayoutParams().height = mHeadOriginHeight;
        mHeadView.setLayoutParams(mHeadView.getLayoutParams());
        scrollTo(0, 0);
        if (mStickyListener != null) {
            mStickyListener.onSticky(mContentView.getChildAt(0), 0);
        }
//        fullScroll(View.FOCUS_UP);
//        Objects.requireNonNull(getChildRecyclerView(mContentView)).smoothScrollToPosition(0);
    }

    //设置吸附开始到吸附结束的状态监听
    public void setOnStickyListener(OnStickyListener onStickyListener) {
        mStickyListener = onStickyListener;
    }

    public interface OnStickyListener {
        /**
         * @param view  需要吸附的View
         * @param state 当吸附控件的top达到最终位置的bottom时开始state=0，当彻底达到吸附状态state=1
         */
        void onSticky(View view, float state);
    }
}
