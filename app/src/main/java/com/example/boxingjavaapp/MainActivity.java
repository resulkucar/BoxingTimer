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
        int restTime = n*1000;
        timer = new CountDownTimer(restTime, 1000) {
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
                getCounter();
                roundLength(5);
            }
        };
        timer.start();
    }
    public void roundLength(int n) {

            int adjLength= n *1000;
            timer = new CountDownTimer(adjLength, 1000) {
                @Override
                public void onTick(final long millSecondsLeftToFinish) {
                    long min = TimeUnit.MILLISECONDS.toMinutes(millSecondsLeftToFinish);
                    long sec = TimeUnit.MILLISECONDS.toSeconds(millSecondsLeftToFinish) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millSecondsLeftToFinish));
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
                    int i=4;
                    if(x>=i){
                        timer.cancel();
                    }
                    rest(10);

                }
            };
            timer.start();
        }

    public void startOnClick(View view) {
        roundLength(5);

    }

    public void stopOnClick(View view) {
        timer.cancel();
        // reset
        tvTimer.setText("00:00");
    }

}
