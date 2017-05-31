package com.core.op.lib.weight.cookiebar;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * CookieBar is a lightweight library for showing a brief message at the top or bottom of the screen.
 * <p>
 * <pre>
 * new CookieBar
 *      .Builder(MainActivity.this)
 *      .setTitle("TITLE")
 *      .setMessage("MESSAGE")
 *      .setAction("ACTION", new OnActionClickListener() {})
 *      .show();
 * </pre>
 * <p>
 * Created by Eric on 2017/3/2.
 */
public class CookieBar {

    private static final String TAG = "cookie";

    private Cookie cookieView;
    private FrameLayout rootView;
    private Context context;

    private CookieBar() {
    }

    private CookieBar(Context context, Params params) {
        this.context = context;
        cookieView = new Cookie(context);
        cookieView.setParams(params);

        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags =
//          LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//          LayoutParams.FLAG_NOT_TOUCHABLE
        ;

        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.TOP;
        wmParams.x = 0;
        wmParams.y = 0;
        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        rootView = new FrameLayout(context);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        windowManager.addView(rootView, wmParams);
    }

    public void show() {
        if (cookieView != null) {
//            final ViewGroup decorView = (ViewGroup) context.getWindow().getDecorView();
//            final ViewGroup content = (ViewGroup) decorView.findViewById(android.R.id.content);

            rootView.addView(cookieView);
//            if (cookieView.getParent() == null) {
//                if (cookieView.getLayoutGravity() == Gravity.BOTTOM) {
//                    content.addView(cookieView);
//                } else {
//                    decorView.addView(cookieView);
//                }
//            }
        }
    }

    public static class Builder {

        private Params params = new Params();

        public Context context;

        public Builder(Context activity) {
            this.context = activity;
        }

        public Builder setIcon(@DrawableRes int iconResId) {
            params.iconResId = iconResId;
            return this;
        }

        public Builder setTitle(String title) {
            params.title = title;
            return this;
        }

        public Builder setTitle(@StringRes int resId) {
            params.title = context.getString(resId);
            return this;
        }

        public Builder setMessage(String message) {
            params.message = message;
            return this;
        }

        public Builder setMessage(@StringRes int resId) {
            params.message = context.getString(resId);
            return this;
        }

        public Builder setUri(String uri) {
            params.uri = uri;
            return this;
        }

        public Builder setDuration(long duration) {
            params.duration = duration;
            return this;
        }

        public Builder setOnClickListener(View.OnClickListener onClickListener) {
            params.onClickListener = onClickListener;
            return this;
        }

        public Builder setTitleColor(@ColorRes int titleColor) {
            params.titleColor = titleColor;
            return this;
        }

        public Builder setMessageColor(@ColorRes int messageColor) {
            params.messageColor = messageColor;
            return this;
        }

        public Builder setBackgroundColor(@ColorRes int backgroundColor) {
            params.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setActionColor(@ColorRes int actionColor) {
            params.actionColor = actionColor;
            return this;
        }

        public Builder setAction(String action, OnActionClickListener onActionClickListener) {
            params.action = action;
            params.onActionClickListener = onActionClickListener;
            return this;
        }

        public Builder setAction(@StringRes int resId, OnActionClickListener onActionClickListener) {
            params.action = context.getString(resId);
            params.onActionClickListener = onActionClickListener;
            return this;
        }

        public Builder setLayoutGravity(int layoutGravity) {
            params.layoutGravity = layoutGravity;
            return this;
        }

        public CookieBar create() {
            CookieBar cookie = new CookieBar(context, params);
            return cookie;
        }

        public CookieBar show() {
            final CookieBar cookie = create();
            cookie.show();
            return cookie;
        }
    }

    final static class Params {

        public String title;

        public String message;

        public String action;

        public OnActionClickListener onActionClickListener;

        public View.OnClickListener onClickListener;
        public int iconResId;

        public int backgroundColor;

        public int titleColor;

        public int messageColor;

        public int actionColor;

        public String uri;

        public long duration = 2000;

        public int layoutGravity = Gravity.TOP;

    }

}
