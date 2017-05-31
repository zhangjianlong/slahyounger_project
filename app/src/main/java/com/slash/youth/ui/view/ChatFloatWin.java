package com.slash.youth.ui.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.slash.youth.R;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.v2.feature.message.MessageActivity;

import java.lang.reflect.Field;


/**
 * @author: jl
 * @Time: 2017/1/10
 * @Desc:浮动窗口
 */

public class ChatFloatWin extends LinearLayout {
    /**
     * 记录小悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录小悬浮窗的高度
     */
    public static int viewHeight;

    /**
     * 用于更新小悬浮窗的位置
     */
    private WindowManager windowManager;

    private WindowManager.LayoutParams wmParams;
    /**
     * 小悬浮窗的参数
     */
    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;
    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;

    private float currentY;
    private float lastY;
    private float distance;


    private static int statusBarHeight;//状态栏高度
    private ImageView chatIv;
    private int screenWidth;
    private int screenHeight;

    public ChatFloatWin(Context context) {
        super(context);
        LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layer_every_msg_icon, this);
        int viewWidth = view.getWidth();
        int viewHeight = view.getHeight();
        wmParams = new WindowManager.LayoutParams();
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();
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
        wmParams.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        wmParams.x = 0;
        wmParams.y = 0;
        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        windowManager.addView(view, wmParams);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        chatIv = (ImageView) view.findViewById(R.id.iv_msg_icon);
        chatIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMessageActivity = new Intent(CommonUtils.getContext(), MessageActivity.class);
                intentMessageActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CommonUtils.getContext().startActivity(intentMessageActivity);

            }
        });

        chatIv.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                xInScreen = event.getRawX();
                yInScreen = event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        xInView = event.getX();
                        yInView = event.getY();
                        xDownInScreen = event.getRawX();
                        yDownInScreen = event.getRawY() - getStatusBarHeight();
                        xInScreen = event.getRawX();
                        yInScreen = event.getRawY();
                        lastY = event.getY();
                        distance = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        xInScreen = event.getRawX();
                        yInScreen = event.getRawY();
                        currentY = event.getY();
                        distance = Math.abs(currentY - lastY);
                        lastY = currentY;
                        //手指一动的时候就更新悬浮窗位置
                        updateViewPosition();
                        break;
                }
                if (distance > 3) {
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    /**
     * 用于获取状态栏高度
     *
     * @return 返回状态栏高度的像素值
     */
    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }


    public void remove() {
        windowManager.removeViewImmediate(ChatFloatWin.this);
    }

    public void changeIvColor(boolean isRead) {
        if (null == chatIv) {
            return;
        }
        if (isRead) {
            chatIv.setImageResource(R.mipmap.news_default);
        } else {
            chatIv.setImageResource(R.mipmap.news_activation);
        }
    }

    public void showChatIv(boolean isShow) {
        if (null == chatIv) {
            return;
        }

        if (isShow) {
            chatIv.setVisibility(VISIBLE);
        } else {
            chatIv.setVisibility(GONE);
        }

    }


    /**
     * 更新小悬浮窗在屏幕中的位置。0
     */

    private void updateViewPosition() {
        int tempY = (int) (yInScreen - yInView - screenHeight / 2);
        if (tempY < 0) {
            tempY = 0;
        } else if (tempY >= screenHeight / 2) {
            tempY = screenHeight / 2;
        }
        wmParams.x = 0;
        wmParams.y = tempY;
        windowManager.updateViewLayout(this, wmParams);
    }


}
