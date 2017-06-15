package com.smartjewelry.blue105;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.smart.library.blue.BleCode;
import com.smart.library.blue.BleImp;
import com.smart.library.blue.BleReceiver;
import com.smart.library.blue.BleReply;

import java.util.ArrayList;
import java.util.Map;

public class TestActivity extends Activity implements View.OnClickListener, BleImp {
    private String TAG = TestActivity.class.getSimpleName();

    BleReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mReceiver = new BleReceiver(this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void setTestData(Map map) {
        String action = (String) map.get("action");
        ArrayList<Integer> data = (ArrayList<Integer>) map.get("data");
        Intent intent = new Intent(action);
        intent.putIntegerArrayListExtra("data", data);
        sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                setTestData(BleReply.dealReplyData(DataSet.setData_26(1)));
                break;
            case R.id.btn2:
                setTestData(BleReply.dealReplyData(DataSet.setData_27_0()));
                break;
            case R.id.btn3:
                setTestData(BleReply.dealReplyData(DataSet.setData_27_1()));
                break;
            case R.id.btn4:
                setTestData(BleReply.dealReplyData(DataSet.setData_08(2)));
                break;
            case R.id.btn5:
                setTestData(BleReply.dealReplyData(DataSet.setData_09_0()));
                break;
            case R.id.btn6:
                setTestData(BleReply.dealReplyData(DataSet.setData_09_1()));
                break;
            case R.id.btn7:
                setTestData(BleReply.dealReplyData(DataSet.setData_09_2()));
                break;
            case R.id.button:
                startActivity(new Intent(this, BleControlActivity.class));
                break;
        }
    }

    @Override
    public void onSuccess(BleCode code, Object result) {
        Log.i("BleReceiver", result.toString());
    }

    @Override
    public void onFail(String error) {

    }
}
