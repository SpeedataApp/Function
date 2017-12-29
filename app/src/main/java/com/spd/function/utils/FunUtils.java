package com.spd.function.utils;

import android.text.TextUtils;

import com.spd.function.power.PowerConstant;

import java.math.BigInteger;

/**
 * @author :Reginer in  2017/12/29 10:54.
 * 联系方式:QQ:282921012
 * 功能描述:
 */
public class FunUtils {
    public static byte[] hexString2Bytes(String src) {
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < src.length() / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    private static byte uniteBytes(byte src0, byte src1) {
        try {
            byte b0 = Byte.decode("0x" + new String(new byte[]{src0}));
            b0 = (byte) (b0 << 4);
            byte b1 = Byte.decode("0x" + new String(new byte[]{src1}));
            return (byte) (b0 ^ b1);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 2.4G校验数据
     *
     * @param bytes 串口数据
     * @return 经过校验的数据
     */
    public static byte[] getParityData(byte[] bytes) {
        final int length = 14;
        if (bytes.length >= length) {
            for (int i = 0; i < bytes.length; i++) {
                if (bytes.length - i >= length && PowerConstant.PARITY_DATA_2_4.equals
                        (binary(sub(bytes, i, 4), 16))) {
                    return sub(bytes, i, 14);
                }
            }
        }
        return null;
    }

    /**
     * 进制转换
     *
     * @param bytes 需要转换的数组
     * @param radix 进制数
     * @return 字符串
     */
    public static String binary(byte[] bytes, int radix) {
        return new BigInteger(1, bytes).toString(radix);
    }

    /**
     * 截取数组
     *
     * @param bytes  数据源
     * @param start  从第几个开始(0是第一位)
     * @param length 截几个
     * @return 返回数据
     */
    public static byte[] sub(byte[] bytes, int start, int length) {
        byte[] res = new byte[length];
        System.arraycopy(bytes, start, res, 0, length);
        return res;
    }

    /**
     * byte数组转换成String
     *
     * @param src 数组
     * @return string
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder(" ");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte aSrc : src) {
            int v = aSrc >= 0 ? aSrc : aSrc + 256;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv).append(" ");
        }
        return stringBuilder.toString();
    }

    /**
     * 获取封签码
     *
     * @param bytes 返回数据
     * @return 封签码
     */
    public static String getSealCode(byte[] bytes) {
        String code = bytesToHexString(sub(bytes, 9, 4));
        return TextUtils.isEmpty(code) ? code : code.replaceAll(" ", "");
    }

    /**
     * 获取封签号
     *
     * @param bytes 2.4G返回数组
     * @return 封签号
     */
    public static String getSealNo(byte[] bytes) {
        byte[] temp = new byte[3];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = sub(bytes, 5, 3)[2 - i];
        }
        return binary(temp, 10);
    }

    /**
     * 钱箱是否异常
     *
     * @param bytes 2.4G返回数组
     * @return 是否异常
     */
    public static boolean isException(byte[] bytes) {
        return 0 == Long.parseLong(binary(sub(bytes, 9, 4), 10));
    }

    /**
     * 获取写入数据
     *
     * @return 待写入数据
     */
    public static byte[] getWriteByte() {
        String postData = "02116148523063446F764C7A49784F4334324E4334784E7934784F5463364F4441354D43396959573572646D463162485176596D467A5A576C755A6D387659323974615735305A584A6D59574E6C4C32646C6446426B595570725158523059574E6F5647467A617A397162334E756333527950587369596D6C7362465235634755694F694977496977695A47567764453576496A6F694D5441774F444177496E303D03E5C2";
        return toByteArray(postData);
    }

    /**
     * 16进制的字符串表示转成字节数组
     *
     * @param hexString 16进制格式的字符串
     * @return 转换后的字节数组
     **/
    private static byte[] toByteArray(String hexString) {
        if (TextUtils.isEmpty(hexString)) {
            throw new IllegalArgumentException("this hexString must not be empty");
        }
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        //因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
        for (int i = 0; i < byteArray.length; i++) {
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }
}
