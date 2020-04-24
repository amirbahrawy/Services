package com.example.android.services;

import android.app.Service;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyStartedService extends Service {
   private static final String TAG=MyStartedService.class.getSimpleName();
    // created once
    @Override
    public void onCreate() {
        Log.i(TAG,"on Create, Thread Name "+Thread.currentThread().getName());
        super.onCreate();
    }
    //every time i start the service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand, Thread Name " + Thread.currentThread().getName());
        // here we perform the task[short duration task :don't block ui]
        //the solution is ise background thread
        AsyncTask<Integer, String, Void> asyncTask = new AsyncTask<Integer, String, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.i(TAG, "onpre, Thread Name " + Thread.currentThread().getName());

            }

            @Override
            protected Void doInBackground(Integer... params) {
                Log.i(TAG, "onbackground, Thread Name " + Thread.currentThread().getName());
                int sleepTime=params[0];
                int ctr=1;
                //long operation
                while(ctr<=sleepTime){
                    publishProgress("Conter is now "+ctr);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                   ctr++;
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                Toast.makeText(MyStartedService.this, values[0], Toast.LENGTH_SHORT).show();
                Log.i(TAG, values[0]+" onUpdate, Thread Name " + Thread.currentThread().getName());

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.i(TAG, "onpost, Thread Name " + Thread.currentThread().getName());
                 stopSelf();
            }
        };
        asyncTask.execute(10);
        return super.onStartCommand(intent,flags,startId);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
  //when the service stoped
    @Override
    public void onDestroy() {
        Log.i(TAG,"on Destroy, Thread Name "+Thread.currentThread().getName());
        super.onDestroy();
    }
}
