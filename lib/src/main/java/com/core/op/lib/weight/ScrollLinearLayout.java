package com.core.op.lib.weight;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by acer on 2017/3/16.
 */

public class ScrollLinearLayout extends LinearLayout {
    private Context context;

    private Scroller mScroller;

    public ScrollLinearLayout(Context context) {
        this(context, null);
    }

    public ScrollLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        // 这个Interpolator你可以设置此外 我这里选择的是有弹跳后果的Interpolator
        Interpolator polator = new OvershootInterpolator();
        mScroller = new Scroller(context, polator);
        // 这里你必定要设置成透明配景,否则会影响你看到底层结构
        this.setBackgroundColor(Color.argb(0, 0, 0, 0));
    }

    // 推进门的动画
    public void startBounceAnim(int startY, int dy, int duration) {
        mScroller.startScroll(0, startY, 0, dy, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // 不要遗忘更新界面
            postInvalidate();
        }
    }
}
