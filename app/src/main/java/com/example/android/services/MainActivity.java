package com.example.android.services;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button start;
    private Button stop, intentService;
    private TextView startResult;
    private TextView intentResult;
    private Handler handler;
    private Button btnBoundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        start = (Button) findViewById(R.id.button);
        start.setOnClickListener(this);
        stop = (Button) findViewById(R.id.button2);
        stop.setOnClickListener(this);
        intentService = findViewById(R.id.intent_service);
        intentService.setOnClickListener(this);
        startResult = (TextView) findViewById(R.id.startResult);
        intentResult = (TextView) findViewById(R.id.intentResult);
        handler = new Handler();
        btnBoundService = (Button) findViewById(R.id.btn_boundService);
        btnBoundService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == start)
            startService(new Intent(MainActivity.this, MyStartedService.class));
        if (v == stop)
            stopService(new Intent(MainActivity.this, MyStartedService.class));
        if (v == intentService) {
            MyResultReciver myResultReciver = new MyResultReciver(null);
            Intent intent = new Intent(MainActivity.this, MyIntentService.class);
            intent.putExtra("sleepTime", 10);
            intent.putExtra("reciver", myResultReciver);
            startService(intent);
        }
        if (v==btnBoundService)
            startActivity(new Intent(MainActivity.this,SecondActivity.class));
    }

    private class MyResultReciver extends ResultReceiver {

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public MyResultReciver(Handler handler) {
            super(handler);
        }

        /* bet3ml lw al data f nfs al app bas
          httnfz f worker thread 34an keda menf3sh n7ot gwaha set text lazm handler
        to Recive data from MyIntentService */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            Log.i("MyResultReciver ", Thread.currentThread().getName());
            if (resultCode == 18 && resultData != null) {
                final String result = resultData.getString("intentResult");
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        Log.i("MyHandler ", Thread.currentThread().getName());
                        intentResult.setText(result);

                    }
                });
            }

        }
    }
}
