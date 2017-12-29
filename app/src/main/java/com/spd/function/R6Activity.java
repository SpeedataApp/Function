package com.spd.function;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.speedata.r6lib.IMifareManager;
import com.speedata.r6lib.R6Manager;

import static com.speedata.r6lib.R6Manager.CardType.MIFARE;

/**
 * @author :Reginer in  2017/12/28 12:00.
 * 联系方式:QQ:282921012
 * 功能描述:高频
 */
public class R6Activity extends AppCompatActivity implements View.OnClickListener {
    private IMifareManager dev;
    private Button mReadCard;
    private TextView mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r6);
        initView();
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {

        }

        dev = R6Manager.getMifareInstance(MIFARE);
        if (dev.InitDev() != 0) {

            mReadCard.setText("初始化失败，请检查设备");
            return;
        }

        mMessage.setText("初始化成功");

    }

    private void initView() {
        mReadCard = findViewById(R.id.get_rfid);
        mReadCard.setOnClickListener(this);
        mMessage = findViewById(R.id.result);
        mMessage.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.get_rfid:

                byte[] mID = dev.SearchCard();
                if (mID == null) {
                    mMessage.setText("没有读取到卡片");
                    return;
                }
                StringBuilder mIDString = new StringBuilder(" 0x");
                for (byte a : mID) {
                    mIDString.append(String.format("%02X", a));
                }
                //得到了卡号，进行下一步处理。
                Toast.makeText(this,"读取卡号成功",Toast.LENGTH_SHORT).show();
                String card = mIDString.toString();
                mMessage.setText(card);

                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        dev.ReleaseDev();
        super.onDestroy();
    }
}
