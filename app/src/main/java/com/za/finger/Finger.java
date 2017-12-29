package com.za.finger;

import android.graphics.Bitmap;

/**
 * @author :Reginer in  2017/11/27 15:11.
 *         联系方式:QQ:282921012
 *         功能描述:
 */
public class Finger {

    public interface MatchListener {
        /**
         * 指纹匹配结果
         *
         * @param result 匹配结果
         * @param msg    附加消息
         */
        void match(boolean result, String msg);
    }

    public interface ImageListener {
        /**
         * 指纹样本
         *
         * @param finger 指纹
         */
        void image(Bitmap finger);
    }
}
