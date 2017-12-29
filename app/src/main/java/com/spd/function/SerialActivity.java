package com.spd.function;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.serialport.SerialPort;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.spd.function.power.PowerConstant;
import com.spd.function.utils.FunUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author :Reginer in  2017/12/28 11:59.
 * 联系方式:QQ:282921012
 * 功能描述:串口
 */
public class SerialActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial);
        initView();
    }

    private void initView() {
        findViewById(R.id.get_data).setOnClickListener(this);
        mResult = findViewById(R.id.result);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_data:
                try {
                    SerialPort serialPort = new SerialPort();
                    serialPort.openSerial(PowerConstant.PORT_SERIAL, PowerConstant.BAUD_RATE_SERIAL);
                    serialPort.writeSerialByte(serialPort.getFd(), FunUtils.getWriteByte());
                    Observable.timer(5, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(aLong -> {
                                byte[] serialData = serialPort.readSerial(serialPort.getFd(), 99999999);
                                mResult.setText(FunUtils.bytesToHexString(serialData));
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }

}
