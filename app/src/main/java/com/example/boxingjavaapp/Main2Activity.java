package com.example.boxingjavaapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity
{
    private static int InputMinRound = 0;
    private static int InputSecRound = 10;
    private static int InputMinRest = 0;
    private static int InputSecRest = 5;
    private static int myCounter = 1;
    private static int InputDelayMin = 0;
    private static int InputDelaySec = 3;
    private static long RestMinutes = InputMinRest * 60000;
    private static long RestSeconds = InputSecRest * 1000;
    private static long RestTimeInMills = RestMinutes+RestSeconds;
    private static long DelayMin = InputDelayMin * 60000;
    private static long DelaySec = InputDelaySec * 1000;
    private static  long DelayTimeInMills = DelayMin + DelaySec;
    private long delayLeftInMills = DelayTimeInMills;
    private long restLeftInMills = RestTimeInMills;
    private long roundLeftInMills=StartTimeInMills;
    private static long minutes = InputMinRound * 60000;
    private static long seconds = (InputSecRound) * 1000;
    private static  long StartTimeInMills = minutes + seconds;
    private boolean DelayRunning=true;
    private boolean RoundRunning=true;
    private boolean RestRunning =true;
    public static boolean delayFin=false;
    public static boolean roundFin=false;
    public static boolean restFin=false;
    private static String CurrentTimer="Delay";
    public static boolean Reset=false;


    TextView tvTimer;
    TextView textRest;
    TextView roundCtr;
    Button bStopReset;
    Button bStart;
    Button bPause;

    CountDownTimer RoundTimer;
    CountDownTimer DelayTimer;
    CountDownTimer RestTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.tRest).setVisibility(View.GONE);
        textRest = (TextView) findViewById(R.id.tRest);
        roundCtr = (TextView) findViewById(R.id.roundCounter);
        tvTimer = (TextView) findViewById(R.id.textView);
        bStart = (Button) findViewById(R.id.start);
        bPause = (Button) findViewById(R.id.pause);
        bStopReset = (Button) findViewById(R.id.stop_reset);
    }
    public void delay(long mili) {

        DelayTimer = new CountDownTimer(mili, 500)
        {
            @Override
            public void onTick(long millSecondsLeftToFinish)
            {
                DelayRunning=true;
                delayLeftInMills=millSecondsLeftToFinish;
                long min = TimeUnit.MILLISECONDS.toMinutes(millSecondsLeftToFinish);
                long sec = TimeUnit.MILLISECONDS.toSeconds(millSecondsLeftToFinish) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millSecondsLeftToFinish));
                tvTimer.setText(String.format("%02d:%02d ", min, sec));
            }

            @Override
            public void onFinish()
            {
                DelayTimer.cancel();
                delayFin=true;
                DelayRunning=false;
                findViewById(R.id.tRest).setVisibility(View.GONE);
                roundLength(StartTimeInMills);

            }
        };
        DelayTimer.start();
    }
    public void rest(long mili) {
        CurrentTimer="Rest";

        RestTimer = new CountDownTimer(mili, 500)
        {
            @Override
            public void onTick(long millSecondsLeftToFinish) {
                RestRunning=true;
                findViewById(R.id.tRest).setVisibility(View.VISIBLE);
                restLeftInMills=millSecondsLeftToFinish;
                long min = TimeUnit.MILLISECONDS.toMinutes(millSecondsLeftToFinish);
                long sec = TimeUnit.MILLISECONDS.toSeconds(millSecondsLeftToFinish) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millSecondsLeftToFinish));
                tvTimer.setText(String.format("%02d:%02d ", min, sec));
            }

            @Override
            public void onFinish()
            {
                final MediaPlayer ring= MediaPlayer.create(Main2Activity.this,R.raw.boxring);
                RestRunning=false;
                restFin=true;
                RestTimer.cancel();
                findViewById(R.id.tRest).setVisibility(View.GONE);
                roundLength(StartTimeInMills);
            }
        };
        RestTimer.start();
    }
    public void roundLength(long mili)
    {
        CurrentTimer="Round";
        final MediaPlayer ring= MediaPlayer.create(Main2Activity.this,R.raw.boxring);
        ring.start();
        RoundTimer = new CountDownTimer(mili, 500)
        {
            @Override
            public void onTick(final long millSecondsLeftToFinish)
            {
                RoundRunning=true;
                roundLeftInMills=millSecondsLeftToFinish;
                long min = TimeUnit.MILLISECONDS.toMinutes(millSecondsLeftToFinish);
                long sec = TimeUnit.MILLISECONDS.toSeconds(millSecondsLeftToFinish) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millSecondsLeftToFinish));
                tvTimer.setText(String.format("%02d:%02d ", min, sec));

            }

            @Override
            public void onFinish()
            {
                int i=2;
                if(myCounter==i){
                    RoundTimer.cancel();
                    DelayTimer.cancel();
                    RestTimer.cancel();
                    bPause.setVisibility(View.INVISIBLE);
                    bStart.setVisibility(View.VISIBLE);
                    tvTimer.setText("Done");
                    CurrentTimer="Delay";
                    Reset=true;
                }else {
                    roundFin = true;
                    RoundTimer.cancel();
                    RoundRunning = false;
                    rest(RestTimeInMills);
                    myCounter++;
                    roundCtr.setText(Integer.toString(myCounter));
                    final MediaPlayer ring = MediaPlayer.create(Main2Activity.this, R.raw.boxring);
                    ring.start();
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(1000);
                    if ((TimeUnit.MILLISECONDS.toSeconds(0) == 0)) {
                        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 1000 milliseconds
                        v.vibrate(1000);
                    }
                    ring.start();
                }

            }
        };
        RoundTimer.start();
    }
    public void startOnClick(View view){
        findViewById(R.id.roundCounter).setVisibility(View.VISIBLE);
        bStart.setVisibility(View.INVISIBLE);
        bPause.setVisibility(View.VISIBLE);
        if(myCounter==4)
        {
            tvTimer.setText("Done!");
        }else if(CurrentTimer=="Rest"&&RestRunning==false) {
            rest(restLeftInMills);
        } else if(CurrentTimer=="Round"&&RoundRunning==false){
            roundLength(roundLeftInMills);
        } else if(CurrentTimer=="Delay"&&myCounter==1&&DelayRunning==false&&Reset==false){
            delay(delayLeftInMills);
        } else if(CurrentTimer=="Delay"&&myCounter==1||CurrentTimer=="Delay"&&Reset==true){
            delay(DelayTimeInMills);
            Reset=false;
        }
    }

    public void pauseOnClick(View view){
        bPause.setVisibility(View.INVISIBLE);
        bStart.setVisibility(View.VISIBLE);
        if(RestRunning==true&&CurrentTimer=="Rest") {
            RestTimer.cancel();
            long min = TimeUnit.MILLISECONDS.toMinutes(restLeftInMills);
            long sec = TimeUnit.MILLISECONDS.toSeconds(restLeftInMills) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(restLeftInMills));
            tvTimer.setText(String.format("%02d:%02d ", min, sec));
            RestRunning= false;
        }else if(RoundRunning==true&&CurrentTimer=="Round") {
            RoundTimer.cancel();
            long min = TimeUnit.MILLISECONDS.toMinutes(roundLeftInMills);
            long sec = TimeUnit.MILLISECONDS.toSeconds(roundLeftInMills) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(roundLeftInMills));
            tvTimer.setText(String.format("%02d:%02d ", min, sec));
            RoundRunning = false;
        }else if(DelayRunning==true&&CurrentTimer=="Delay") {
            DelayTimer.cancel();
            long min = TimeUnit.MILLISECONDS.toMinutes(delayLeftInMills);
            long sec = TimeUnit.MILLISECONDS.toSeconds(delayLeftInMills) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(delayLeftInMills));
            tvTimer.setText(String.format("%02d:%02d ", min, sec));
            DelayRunning = false;
        }else
            return;

    }

    public void stopOnClick(View view) {
        if (CurrentTimer == "Delay") {
            DelayTimer.cancel();
            Reset = true;
            DelayRunning = false;
        } else if (CurrentTimer == "Round") {
            RoundTimer.cancel();
            Reset = true;
            RoundRunning = false;
        } else if (CurrentTimer == "Rest") {
            RestTimer.cancel();
            Reset = true;
            RestRunning = false;
        } else{
            return;
        }
        bPause.setVisibility(View.INVISIBLE);
        bStart.setVisibility(View.VISIBLE);
        tvTimer.setText("00:00");
        myCounter=1;
        roundCtr.setText(Integer.toString(myCounter));
        CurrentTimer="Delay";

    }

}
