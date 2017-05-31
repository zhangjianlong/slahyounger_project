package com.slash.youth.utils;

import android.media.MediaPlayer;
import android.media.RingtoneManager;

/**
 * Created by acer on 2017/3/13.
 */

public class RingingUtil {

    public static void playSysRinging() {
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(CommonUtils.getContext(), RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
