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
        String postData = "02126148523063446f764c7a49784f4334324e4334784e7934784f5463364f4441354d43396959573572646d4631624851766432566963325679646d6c6a5a5339335a574a7a5a584a3261574e6c56584e6c636a3958633252731303573373695155315056553555496a6f694e5334314e534973496b6c7a565842736232466b496a6f694d434973496b355052455650535551694f6949784d5755334c57566a4d7a67744d7a4d77595751775a5745744f5463334f43316d4d574d794d4759305a44526b4d7a41694c434a4f5431524a54555654496a6f694e534973496b39535230394a52434936496a55784d4441774d794973496b39535230394f51553146496a6f6935593272354c69633536755a496977695645465453306c45496a6f694d54466c4e79316c597a4d344c544a6c4e324d325a6a49314c546b334e7a67745a6a466a4d6a426d4e4751305a444d7749697769564546545331525a554555694f69493049697769596d393454476c7a6443493657337369516b3959526c6843543167694f6949694c434a435431684f54794936496a55784d4441774d7a41774d534973496b4a505745394a52434936496a45785a5463745932497a4d69316d4d5455774d5455345a6931694d4459324c54677a596d49354e6d566c4e5452684d434973496b4e4952554e4c535551694f6949784d5755334c574e694d7a59744e7a67784f54566a596d5574596a41324e6930344d324a694f545a6c5a545530595441694c434a476247466e496a6f694e434973496b6c5452566844525642555355394f496a6f694d694973496b6c54553156435256684452564255496a6f694969776953584e51636d6c7564434936496a41694c434a4a63315677624739685a434936496a41694c434a50535551694f6949694c434a5352554e4653565a4656456c4e52555242564555694f6949794d4445334c5445794c544935494445784f6a51354f6a493049697769556b564452556c575256524a54555646546b524551565246496a6f694d6a41784e7930784d6930794f5341784d546f314e7a6f7a4e694973496c4e55515652565531525a554555694f6a4973496c52425530744f5430524654306c45496a6f694d54466c4e79316c597a4d344c544d7a4d47466b4d4756684c546b334e7a67745a6a466a4d6a426d4e4751305a444d774969776956564e46556b6c45496a6f694d54466c4e79316a596a4d324c545a6c4e6d46694f54646a4c5749774e6a59744f444e69596a6b325a5755314e4745774969776959304653546b38694f6949694c434a6a6147566a6179493664484a315a5831646656303d035847";
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
