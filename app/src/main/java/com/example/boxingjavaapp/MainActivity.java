package com.example.boxingjavaapp;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static int myCounter = 0;

    public int getCounter() {
        int a=myCounter++;
        return a;
    }

    private long time = 0;

    TextView tvTimer;
    TextView textRest;
    TextView roundCtr;
    Button bStopReset;
    Button bStart;
    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tRest).setVisibility(View.GONE);
        textRest = (TextView) findViewById(R.id.tRest);
        roundCtr = (TextView) findViewById(R.id.roundCounter);
        tvTimer = (TextView) findViewById(R.id.textView);
        bStart = (Button) findViewById(R.id.start);
        bStopReset = (Button) findViewById(R.id.stop_reset);
    }

    public void rest(int n) {
        findViewById(R.id.tRest).setVisibility(View.VISIBLE);
        long restTime = n*1000;
        timer = new CountDownTimer(restTime, 500) {
            @Override
            public void onTick(long millSecondsLeftToFinish) {
                long min = TimeUnit.MILLISECONDS.toMinutes(millSecondsLeftToFinish);
                long sec = TimeUnit.MILLISECONDS.toSeconds(millSecondsLeftToFinish) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millSecondsLeftToFinish));
                tvTimer.setText(String.format("%02d:%02d ", min, sec));
            }

            @Override
            public void onFinish() {
                start();
                timer.cancel();
                findViewById(R.id.tRest).setVisibility(View.GONE);
                int x= getCounter();
                roundCtr.setText(Integer.toString(x));
                roundLength(181);
            }
        };
        timer.start();
    }
    public void roundLength(int n) {

            long adjLength= n *1000;
            timer = new CountDownTimer(adjLength, 500) {
                @Override
                public void onTick(final long millSecondsLeftToFinish) {
                    time = millSecondsLeftToFinish;
                    long min = TimeUnit.MILLISECONDS.toMinutes(time);
                    long sec = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));
                    tvTimer.setText(String.format("%02d:%02d ", min, sec));
                }

                @Override
                public void onFinish() {
                    start();
                    timer.cancel();
                    getCounter();

                    // Vibrate for 1000 milliseconds
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(1000);
                    if ((TimeUnit.MILLISECONDS.toSeconds(0) == 0)) {
                        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 1000 milliseconds
                        v.vibrate(1000);

                    }
                    int x =getCounter();
                    int i=12;
                    if(x<=i){
                        rest(46);
                    }else {
                        timer.cancel();
                        myCounter = 0;
                    }

                }
            };
            timer.start();
        }

    public void startOnClick(View view) {
        findViewById(R.id.roundCounter).setVisibility(View.VISIBLE);
        roundLength(181);

    }

    public void stopOnClick(View view) {
        timer.cancel();
        // reset
        myCounter=0;
        tvTimer.setText("00:00");
    }

}
