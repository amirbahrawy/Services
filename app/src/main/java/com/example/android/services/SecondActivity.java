package com.example.android.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView result;
    private EditText firstNum;
    private EditText secNum;
    private boolean isBound=false;
    private ServiceConnection serviceConnection;
    private MyBoundService myBoundService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this,MyBoundService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound){
            unbindService(serviceConnection);
            isBound=false;
        }
    }

    public void onClickEvent(View view) {
        int numOne=Integer.valueOf(firstNum.getText().toString());
        int numSec=Integer.valueOf(secNum.getText().toString());
        String res="";
        if (isBound){
            switch (view.getId()){
                case R.id.btn_add:
                    res=myBoundService.add(numOne,numSec)+"";
                    break;
                case R.id.btn_sub:
                    res=myBoundService.sub(numOne,numSec)+"";
                    break;
                case R.id.btn_mul:
                    res=myBoundService.mul(numOne,numSec)+"";
                    break;
                case R.id.btn_div:
                    res=myBoundService.divide(numOne,numSec)+"";
                    break;
            }
            result.setText(res);
        }
    }

    private void initView() {
        result = (TextView) findViewById(R.id.result);
        firstNum = (EditText) findViewById(R.id.first_num);
        secNum = (EditText) findViewById(R.id.sec_num);
        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder iBinder) {
                isBound=true;
                MyBoundService.MyLocalBinder myLocalBinder = (MyBoundService.MyLocalBinder) iBinder;
                myBoundService=myLocalBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isBound=false;
            }
        };
    }
}
