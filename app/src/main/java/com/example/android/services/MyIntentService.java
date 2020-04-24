package com.example.android.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {
     private static final String TAG=MyIntentService.class.getSimpleName();
    // esm el thread
    public MyIntentService() {
        super("MyThread");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate Thread name: "+Thread.currentThread().getName());
    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG,"onHnadleIntent Thread name: "+Thread.currentThread().getName());
        int sleepTime=intent.getIntExtra("sleepTime",1);
        ResultReceiver resultReceiver=intent.getParcelableExtra("reciver");
        int ctr=1;
        //long operation
        while(ctr<=sleepTime){
            Log.i(TAG,"Conter is now "+ctr);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            ctr++;
        }
        Bundle bundle=new Bundle();
        bundle.putString("intentResult","Counter Stoped at "+ctr+" Seconds");
        resultReceiver.send(18,bundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy Thread name: "+Thread.currentThread().getName());

    }
}
