package com.spd.function;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.serialport.SerialPort;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.spd.function.power.PowerConstant;
import com.spd.function.power.PowerUtils;
import com.spd.function.utils.FunUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author :Reginer in  2017/12/28 11:55.
 * 联系方式:QQ:282921012
 * 功能描述:2.4G
 */
public class G24Activity extends BaseActivity implements View.OnClickListener {

    private SerialPort mSerialPort;
    private Disposable mDisposable;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g24);
        initView();
    }

    private void initView() {
        findViewById(R.id.box).setOnClickListener(this);
        mResult = findViewById(R.id.result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.box:
                mSerialPort = new SerialPort();
                PowerUtils.powerOn(PowerConstant.PATH2_4, PowerConstant.GPIO2_4);
                PowerUtils.openSerial(mSerialPort, PowerConstant.PORT2_4, PowerConstant.BAUD_RATE2_4);
                mDisposable = Observable.interval(0, 200, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aLong -> readSerialPort());
                break;

            default:
                break;
        }
    }

    /**
     * 读取2.4G数据
     */
    @SuppressLint("SetTextI18n")
    private void readSerialPort() {
        mSerialPort.writeSerialByte(mSerialPort.getFd(), FunUtils.hexString2Bytes(PowerConstant.POST_DATA_2_4));
        try {
            byte[] bytes = mSerialPort.readSerial(mSerialPort.getFd(), 1024);
            Log.d("Reginer", "readSerialPort: " + FunUtils.bytesToHexString(bytes));
            Set<SerialEntity> entities = FunUtils.getParityDataList(bytes);
            if (entities.size() > 0) {
                StringBuilder builder = new StringBuilder();
                for (SerialEntity entity : entities) {
                    builder.append("号:").append(entity.getSealNo()).append("---").append("码:").append(entity.getSealCode()).append("---").append("异常：").append(entity.isException()).append("\n");
                }
                Log.d("Reginer", "data: "+builder.toString());
                mResult.setText(builder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        super.onDestroy();
    }
}
