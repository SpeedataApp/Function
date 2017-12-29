package com.spd.function;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.spd.function.power.PowerConstant;
import com.spd.function.power.PowerUtils;
import com.za.finger.FingerOperation;
import com.za.finger.ZA_finger;
import com.za.finger.ZAandroid;

import java.util.Arrays;

/**
 * @author :Reginer in  2017/12/28 11:54.
 * 联系方式:QQ:282921012
 * 功能描述:指纹
 */
public class FingerActivity extends BaseActivity implements View.OnClickListener {
    private final int DEV_ADDRESS = 0xffffffff;

    private TextView mTvFinger;
    private ZAandroid a6 = new ZAandroid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);
        initView();
        PowerUtils.powerOn(PowerConstant.PATH, PowerConstant.GPIO);
    }

    private void initView() {
        findViewById(R.id.get_finger).setOnClickListener(this);
        mTvFinger = findViewById(R.id.tv_finger);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_finger:
                openDev();
                break;
            default:
                break;
        }
    }

    private void openDev() {
        byte[] pPassword = new byte[4];
        int status;
        status = a6.ZAZOpenDeviceEx(-1, 4, 12, 6, 0, 0);
        if (status == 1 && a6.ZAZVfyPwd(DEV_ADDRESS, pPassword) == 0) {
            status = 1;
        } else {
            status = 0;
        }
        int imageSize = 0;
        a6.ZAZSetImageSize(imageSize);
        if (status == 1) {
            mTvFinger.setText(R.string.init_success);
            charaTask.run();
        } else {
            mTvFinger.setText(R.string.init_failed);
            ZA_finger power = new ZA_finger();
            power.finger_power_off();
        }
    }
    private Runnable charaTask = new Runnable() {
        @Override
        public void run() {
            int nRet;
            nRet = a6.ZAZGetImage(DEV_ADDRESS);
            if (nRet == 0) {
                nRet = a6.ZAZGetImage(DEV_ADDRESS);
            }
            if (nRet == 0) {
                nRet = a6.ZAZGenChar(DEV_ADDRESS, a6.CHAR_BUFFER_A);
                if (nRet == a6.PS_OK) {
                    int[] iTempLength = {0, 0};
                    byte[] pTemp = new byte[2304];
                    nRet = a6.ZAZUpChar(DEV_ADDRESS, a6.CHAR_BUFFER_A, pTemp, iTempLength);
                    if (nRet == a6.PS_OK) {
                        new Handler(Looper.getMainLooper()).post(() -> mTvFinger.setText(Arrays.toString(pTemp)));
                    }
                } else {
                    new Handler(Looper.getMainLooper()).post(() -> mTvFinger.setText(R.string.get_chara_failed));
                }
            } else if (nRet == a6.PS_NO_FINGER) {
                charaTask.run();
            } else {
                new Handler(Looper.getMainLooper()).post(() -> mTvFinger.setText(R.string.get_chara_failed));
            }
        }
    };
    @Override
    protected void onDestroy() {
        PowerUtils.powerOff(PowerConstant.PATH, PowerConstant.GPIO);
        FingerOperation.closeDev();
        super.onDestroy();
    }
}
