package com.za.finger;


import java.io.DataOutputStream;

/**
 * @author :Reginer in  2017/11/27 14:22.
 *         联系方式:QQ:282921012
 *         功能描述:指纹操作
 */
public class FingerOperation {
    private static final int DEV_ADDRESS = 0xffffffff;
    private static final int IMG_SIZE = 0;


    /**
     * 打开指纹设备
     *
     * @param a6 ZAandroid
     * @return 打开结果
     */
    public static boolean openDev(ZAandroid a6) {
        byte[] pPassword = new byte[4];
        int status;
        checkEuq();
        status = a6.ZAZOpenDeviceEx(-1, 4, 12, 6, 0, 0);
        if (status == 1 && a6.ZAZVfyPwd(DEV_ADDRESS, pPassword) == 0) {
            status = 1;
        } else {
            status = 0;
        }
        a6.ZAZSetImageSize(IMG_SIZE);
        if (status == 1) {
            return true;
        } else {
            ZA_finger power = new ZA_finger();
            power.finger_power_off();
            return false;
        }
    }

    /**
     * 关闭指纹设备
     */
    public static void closeDev() {
        ZA_finger power = new ZA_finger();
        power.finger_power_off();
    }



    private static void checkEuq() {
        Process process;
        DataOutputStream os;
        String path = "/dev/bus/usb/00*/*";
        String command = "chmod 777 " + path;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
        }
    }
}
