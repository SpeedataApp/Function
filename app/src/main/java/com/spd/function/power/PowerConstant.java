package com.spd.function.power;

/**
 * @author :Reginer in  2017/12/28 16:23.
 * 联系方式:QQ:282921012
 * 功能描述:
 */
public class PowerConstant {
    public static final String PATH = "/sys/class/misc/mtgpio/pin";
    public static final int []GPIO = {94,93};

    /**
     * 2.4G上电路径，其他页面也要用到2.4G的话，可以直接使用这个，不要再创建
     */
    public static final String PATH2_4 = "/sys/class/misc/mtgpio/pin";

    /**
     * 2.4G上电gpio，其他页面也要用到2.4G的话，可以直接使用这个，不要再创建
     */
    public static final int[] GPIO2_4 = {93};

    /**
     * 2.4G端口，其他页面也要用到2.4G的话，可以直接使用这个，不要再创建
     */
    public static final String PORT2_4 = "/dev/ttyMT1";
    /**
     * 2.4G波特率，其他页面也要用到2.4G的话，可以直接使用这个，不要再创建
     */
    public static final int BAUD_RATE2_4 = 115200;

    /**
     * 2.4G模块发送的数据，其他页面也要用到2.4G的话，可以直接使用这个，不要再创建
     */
    public static final String POST_DATA_2_4 = "AABB023133";

    /**
     * 2.4G模块校验码，其他页面也要用到2.4G的话，可以直接使用这个，不要再创建
     */
    public static final String PARITY_DATA_2_4 = "aabb0b11";

    /**
     * 串口路径，可以直接使用这个，不要再创建
     */
    public static final String PORT_SERIAL = "/dev/ttyMT3";

    /**
     * 串口波特率，其他页面也要用到的话，可以直接使用这个，不要再创建
     */
    public static final int BAUD_RATE_SERIAL = 115200;
}
