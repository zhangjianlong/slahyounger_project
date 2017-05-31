package com.slash.youth.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.slash.youth.R;

/**
 * Created by acer on 2017/3/10.
 */
public class MessageHintView extends RelativeLayout {
    public MessageHintView(Context context) {
        this(context, null);
    }

    public MessageHintView(Context context, AttributeSet attrs) {
        super(context, null, 0);
        initView(context);
    }


    private void initView(Context context) {
        View.inflate(context, R.layout.item_push_info, MessageHintView.this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
