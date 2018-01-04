package com.spd.function.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.spd.function.R;

/**
 * @author :Reginer in  2018/1/4 18:02.
 * 联系方式:QQ:282921012
 * 功能描述:
 */
public class SoundUtils {
    public static void playFound(Context context) {
        MediaPlayer mp = MediaPlayer.create(context, R.raw.found);
        mp.start();
    }

    public static void playError(Context context) {
        MediaPlayer mp = MediaPlayer.create(context, R.raw.error);
        mp.start();
    }
}
