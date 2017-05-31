package com.slash.youth.ui.activity.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.core.op.lib.messenger.Messenger;
import com.slash.youth.R;
import com.slash.youth.ui.activity.MessageActivity;
import com.slash.youth.ui.dialog.offline.OfflineDialog;
import com.slash.youth.ui.dialog.offline.OfflineViewModel;
import com.slash.youth.utils.CommonUtils;
import com.slash.youth.v2.util.MessageKey;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by zhouyifeng on 2017/2/25.
 */
public class BaseActivity extends RxAppCompatActivity {

    private OfflineDialog offlineDialog;

    private boolean isOffline = false;

    View msgIconLayer;
    ImageView ivMsgIcon;
    boolean isAddMsgIconLayer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //向没个Ativity都添加进入消息列表的icon
//        msgIconLayer = View.inflate(CommonUtils.getContext(), R.layout.layer_every_msg_icon, null);
//        ivMsgIcon = (ImageView) msgIconLayer.findViewById(R.id.iv_msg_icon);
//        ivMsgIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentMessageActivity = new Intent(CommonUtils.getContext(), MessageActivity.class);
//                startActivity(intentMessageActivity);
//            }
//        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        Messenger.getDefault().sendNoMsg(MessageKey.HIDE_FLOAT_WINDOW);

    }


    @Override
    protected void onResume() {
        super.onResume();
        Messenger.getDefault().sendNoMsg(MessageKey.SHOW_FLOAT_WINDOW);

    }


    @Override
    protected void onStart() {
        super.onStart();

//        if (msgIconLayer != null) {
//            if (!isAddMsgIconLayer) {
//                if (this instanceof SplashActivity || this instanceof LoginActivity || this instanceof PerfectInfoActivity || this instanceof ChooseSkillActivity || this instanceof MessageActivity || this instanceof GuidActivity || this instanceof ChatActivity) {
//
//                } else {
//                    this.addContentView(msgIconLayer, new ViewGroup.LayoutParams(-1, -1));
//                    isAddMsgIconLayer = true;
//                }
//            }
//        }
    }


    public void offline() {
        if (offlineDialog == null) {
            offlineDialog = new OfflineDialog(this, new OfflineViewModel(this));
        }
        if (!offlineDialog.isShowing()) {
            offlineDialog.show();
        }
        isOffline = true;
    }

    /**
     * 实现点击空白处，软键盘消失事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
