package com.spd.function.power;


import android.serialport.SerialPort;

import java.io.IOException;

/**
 * @author :Reginer in  2017/11/24 9:31.
 *         联系方式:QQ:282921012
 *         功能描述:串口、上下电工具类
 */
public class PowerUtils {
    /**
     * 打开串口
     *
     * @param serialPort 串口操作类 SerialPort mSerialPort = new SerialPort();
     * @param dev        端口   "/dev/ttyUSB0"
     * @param brd        波特率   115200
     * @return 打开结果
     */
    public static boolean openSerial(SerialPort serialPort, String dev, int brd) {
        try {
            serialPort.openSerial(dev, brd);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void closeSerial(SerialPort serialPort) {
        serialPort.closeSerial(serialPort.getFd());
    }

    /**
     * 串口写入数据
     *
     * @param serialPort serialPort 串口操作类  SerialPort mSerialPort = new SerialPort();
     * @param fd         串口fd
     * @param data       要写入的数据
     */
    public static void writeSerialByte(SerialPort serialPort, int fd, byte[] data) {
        serialPort.writeSerialByte(fd, data);
    }

    /**
     * 串口读取数据
     *
     * @param serialPort 串口操作类  SerialPort mSerialPort = new SerialPort();
     * @param fd         串口fd
     * @return 读取的数据
     */
    public static byte[] readSerial(SerialPort serialPort, int fd) {
        try {
            return serialPort.readSerial(fd, 1024);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 上电
     *
     * @param path 上电路径
     * @param gpio gpio
     * @return 上电结果
     */
    public static boolean powerOn(String path, int... gpio) {
        try {
            FingerGpio fingerGpio = new FingerGpio(path);
            return fingerGpio.powerOnDevice(gpio);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 下电
     *
     * @param path 下电路径
     * @param gpio gpio
     * @return 下电结果
     */
    public static boolean powerOff(String path, int... gpio) {
        try {
            FingerGpio fingerGpio = new FingerGpio(path);
            return fingerGpio.powerOffDevice(gpio);
        } catch (Exception e) {
            return false;
        }
    }
}
