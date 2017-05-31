package com.core.op.lib.weight.cookiebar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.core.op.lib.R;
import com.core.op.lib.databinding.WeightBannerBinding;
import com.core.op.lib.databinding.WeightCookieBinding;


/**
 * Created by Eric on 2017/3/2.
 */
final class Cookie extends LinearLayout {

    private Animation slideInAnimation;
    private Animation slideOutAnimation;
    LayoutInflater inflater;
    private WeightCookieBinding binding;

    private Context mContext;

    private Scroller mScroller;

    private int mScreenWidth = 0;

    private int mScreenHeigh = 300;

    private int mLastDownY = 0;

    private int mCurryY;

    private int mDelY;

    private boolean mCloseFlag = false;

    private long duration = 1500;
    private int layoutGravity = Gravity.BOTTOM;

    public Cookie(@NonNull final Context context) {
        this(context, null);
    }

    public Cookie(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Cookie(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        inflater = LayoutInflater.from(context);
        initViews();
    }

    public int getLayoutGravity() {
        return layoutGravity;
    }

    private void initViews() {
        setupView();
        binding = DataBindingUtil.inflate(inflater, R.layout.weight_cookie, this, true);
    }

    public void setParams(final CookieBar.Params params) {
        if (params != null) {
            duration = params.duration;
            layoutGravity = params.layoutGravity;

            //Icon
            if (params.iconResId != 0) {
                binding.ivIcon.setVisibility(VISIBLE);
                binding.ivIcon.setBackgroundResource(params.iconResId);
            }

            //Title
            if (!TextUtils.isEmpty(params.title)) {
                binding.tvTitle.setVisibility(VISIBLE);
                binding.setTitle(params.title);
                if (params.titleColor != 0) {
                    binding.tvTitle.setTextColor(ContextCompat.getColor(getContext(), params.titleColor));
                }
            }

            //Message
            if (!TextUtils.isEmpty(params.message)) {
                binding.tvMessage.setVisibility(VISIBLE);
                binding.setMessage(params.message);
                if (params.messageColor != 0) {
                    binding.tvMessage.setTextColor(ContextCompat.getColor(getContext(), params.messageColor));
                }
            }

            //uri
            if (!TextUtils.isEmpty(params.uri)) {
                binding.ivIcon.setVisibility(VISIBLE);
                binding.setUri(params.uri);
            }

            //Action
            if (!TextUtils.isEmpty(params.action) && params.onActionClickListener != null) {
                binding.btnAction.setVisibility(VISIBLE);
                binding.btnAction.setText(params.action);
                binding.btnAction.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        params.onActionClickListener.onClick();
                        dismiss();
                    }
                });

                //Action Color
                if (params.actionColor != 0) {
                    binding.btnAction.setTextColor(ContextCompat.getColor(getContext(), params.actionColor));
                }
            }

            //Background
            if (params.backgroundColor != 0) {
                binding.cookie.setBackgroundColor(ContextCompat.getColor(getContext(), params.backgroundColor));
            }

            int padding = getContext().getResources().getDimensionPixelSize(R.dimen.default_padding);
            if (layoutGravity == Gravity.BOTTOM) {
                binding.cookie.setPadding(padding, padding, padding, padding);
            }


            createInAnim();
            createOutAnim();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (layoutGravity == Gravity.TOP) {
            super.onLayout(changed, l, 0, r, binding.cookie.getMeasuredHeight());
        } else {
            super.onLayout(changed, l, t, r, b);
        }
    }

    private void createInAnim() {
        slideInAnimation = AnimationUtils.loadAnimation(getContext(),
                layoutGravity == Gravity.BOTTOM ? R.anim.slide_in_from_bottom : R.anim.slide_in_from_top);
        slideInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mScreenHeigh = Cookie.this.getMeasuredHeight();
                if (duration != -1)
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    }, duration);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        setAnimation(slideInAnimation);
    }

    private void createOutAnim() {
        slideOutAnimation = AnimationUtils.loadAnimation(getContext(),
                layoutGravity == Gravity.BOTTOM ? R.anim.slide_out_to_bottom : R.anim.slide_out_to_top);
        slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void dismiss() {
        slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                destroy();
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {
            }
        });
        startAnimation(slideOutAnimation);
    }

    // 推进门的动画
    public void startBounceAnim(int startY, int dy, int duration) {
        mScroller.startScroll(0, startY, 0, dy, duration);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastDownY = (int) event.getY();
                System.err.println("ACTION_DOWN=" + mLastDownY);
                return true;
            case MotionEvent.ACTION_MOVE:
                mCurryY = (int) event.getY();
                System.err.println("ACTION_MOVE=" + mCurryY);
                mDelY = mCurryY - mLastDownY;
                // 只准上滑有用
                if (mDelY < 0) {
                    scrollTo(0, -mDelY);
                }
                System.err.println("-------------  " + mDelY);
                break;
            case MotionEvent.ACTION_UP:
                mCurryY = (int) event.getY();
                mDelY = mCurryY - mLastDownY;
                if (mDelY < 0) {
                    if (Math.abs(mDelY) > mScreenHeigh / 2) {
                        // 向上滑动凌驾半个屏幕高的时间 开启向上消逝动画
                        startBounceAnim(this.getScrollY(), mScreenHeigh, 450);
                        mCloseFlag = true;
                    } else {
                        // 向上滑动未凌驾半个屏幕高的时间 开启向下弹动动画
                        startBounceAnim(this.getScrollY(), -this.getScrollY(), 1000);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // 不要遗忘更新界面
            postInvalidate();
        } else {
            if (mCloseFlag) {
                this.setVisibility(View.GONE);
                destroy();
            }
        }
    }

    @SuppressLint("NewApi")
    private void setupView() {
        // 这个Interpolator你可以设置此外 我这里选择的是有弹跳后果的Interpolator
        Interpolator polator = new BounceInterpolator();
        mScroller = new Scroller(mContext, polator);
        // 这里你必定要设置成透明配景,否则会影响你看到底层结构
        this.setBackgroundColor(Color.argb(0, 0, 0, 0));
    }

    private void destroy() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewParent parent = getParent();
                if (parent != null) {
                    Cookie.this.clearAnimation();
                    ((ViewGroup) parent).removeView(Cookie.this);
                }
            }
        }, 200);
    }

}
