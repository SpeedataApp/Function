package com.spd.function;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author :Reginer in  2017/12/28 9:19.
 * 联系方式:QQ:282921012
 * 功能描述:
 */
public class MenuActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initView();
    }

    private void initView() {
        findViewById(R.id.finger).setOnClickListener(this);
        findViewById(R.id.g_2_4).setOnClickListener(this);
        findViewById(R.id.serial).setOnClickListener(this);
        findViewById(R.id.qr).setOnClickListener(this);
        findViewById(R.id.r6).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finger:
                startActivity(new Intent(this, FingerActivity.class));
                break;
            case R.id.g_2_4:
                startActivity(new Intent(this, G24Activity.class));
                break;
            case R.id.serial:
                startActivity(new Intent(this, SerialActivity.class));
                break;
            case R.id.qr:
                startActivity(new Intent(this, QrActivity.class));
                break;
            case R.id.r6:
                startActivity(new Intent(this, R6Activity.class));
                break;
            default:
                break;
        }
    }
}
