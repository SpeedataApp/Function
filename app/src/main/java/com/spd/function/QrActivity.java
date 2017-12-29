package com.spd.function;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.scandecode.ScanDecode;
import com.scandecode.inf.ScanInterface;

/**
 * @author :Reginer in  2017/12/28 12:00.
 * 联系方式:QQ:282921012
 * 功能描述:二维码
 */
public class QrActivity extends AppCompatActivity implements View.OnClickListener, ScanInterface.OnScanListener {

    private TextView mResult;
    private ScanInterface mScanDecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        initView();
    }

    private void initView() {
        findViewById(R.id.get_qr).setOnClickListener(this);
        mResult = findViewById(R.id.result);
        mScanDecode = new ScanDecode(this);
        mScanDecode.initService("true");
        mScanDecode.getBarCode(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_qr:
                mScanDecode.starScan();
                break;

            default:
                break;
        }
    }

    @Override
    public void getBarcode(String s) {
        mResult.setText(s);
    }

    @Override
    protected void onStop() {
        mScanDecode.stopScan();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mScanDecode.onDestroy();
        super.onDestroy();
    }
}
