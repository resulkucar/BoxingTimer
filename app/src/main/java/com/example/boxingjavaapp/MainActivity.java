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
import java.util.Locale;
import java.util.concurrent.TimeUnit;

    public class MainActivity extends AppCompatActivity
    {
        private static int InputMinRound = 0;
        private static int InputSecRound = 10;
        private static int InputMinRest = 0;
        private static int InputSecRest = 5;
        private static int myCounter = 0;
        private static int x = getCounter();
        private static int InputDelayMin = 0;
        private static int InputDelaySec = 3;
        private static long RestMinutes = InputMinRest * 60000;
        private static long RestSeconds = InputSecRest * 1000;
        private static long RestTimeInMills = RestMinutes+RestSeconds;
        private static long DelayMin = InputDelayMin * 60000;
        private static long DelaySec = InputDelaySec * 1000;
        private static final long DelayTimeInMills = DelayMin + DelaySec;
        private long delayLeftInMills = DelayTimeInMills;
        private long restLeftInMills = RestTimeInMills;
        private long roundLeftInMills=StartTimeInMills;
        private static long minutes = InputMinRound * 60000;
        private static long seconds = (InputSecRound) * 1000;
        private static final long StartTimeInMills = minutes + seconds;
        private boolean DelayRunning=true;
        private boolean RoundRunning=true;
        private boolean RestRunning =true;
        public static boolean delayFin=false;
        public static boolean roundFin=false;
        public static boolean restFin=false;
        public static boolean lastStateRound=false;
        public static int getCounter()
        {
            int a=myCounter++;
            return a;
        }

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
            setContentView(R.layout.activity_main);
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
                    DelayRunning = false;
                    findViewById(R.id.tRest).setVisibility(View.GONE);
                    roundLength(StartTimeInMills);

                }
            };
            DelayTimer.start();
        }
        public void rest(long mili) {

            RestTimer = new CountDownTimer(mili, 500)
            {
                @Override
                public void onTick(long millSecondsLeftToFinish) {
                    findViewById(R.id.tRest).setVisibility(View.VISIBLE);
                    RestRunning=true;
                    restLeftInMills=millSecondsLeftToFinish;
                    long min = TimeUnit.MILLISECONDS.toMinutes(millSecondsLeftToFinish);
                    long sec = TimeUnit.MILLISECONDS.toSeconds(millSecondsLeftToFinish) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millSecondsLeftToFinish));
                    tvTimer.setText(String.format("%02d:%02d ", min, sec));
                }

                @Override
                public void onFinish()
                {
                    getCounter();
                    final MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.boxring);
                    RestRunning=false;
                    restFin=true;
                    RestTimer.cancel();
                    ring.start();
                    findViewById(R.id.tRest).setVisibility(View.GONE);
                    roundLength(StartTimeInMills);
                }
            };
            RestTimer.start();
        }
        public void roundLength(long mili)
        {
            final MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.boxring);
            ring.start();
            RoundTimer = new CountDownTimer(mili, 500)
            {
                @Override
                public void onTick(final long millSecondsLeftToFinish)
                {
                    lastStateRound= true;
                    RoundRunning=true;
                    roundLeftInMills=millSecondsLeftToFinish;
                    long min = TimeUnit.MILLISECONDS.toMinutes(millSecondsLeftToFinish);
                    long sec = TimeUnit.MILLISECONDS.toSeconds(millSecondsLeftToFinish) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millSecondsLeftToFinish));
                    tvTimer.setText(String.format("%02d:%02d ", min, sec));
                    roundCtr.setText(Integer.toString(x));
                }

                @Override
                public void onFinish()
                {
                    RoundTimer.cancel();
                    RoundRunning=false;
                    roundFin=true;
                    RestRunning=true;
                    rest(RestTimeInMills);
                    final MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.boxring);
                    ring.start();
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(1000);
                    if ((TimeUnit.MILLISECONDS.toSeconds(0) == 0)) {
                        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 1000 milliseconds
                        v.vibrate(1000);
                    }
                        ring.start();
                        RoundTimer.cancel();
                        rest(RestTimeInMills);
                }
            };
            RoundTimer.start();
        }

        public void startOnClick(View view){
            findViewById(R.id.roundCounter).setVisibility(View.VISIBLE);
            bStart.setVisibility(View.INVISIBLE);
            bPause.setVisibility(View.VISIBLE);
            if(restFin==true){
                roundLength(StartTimeInMills);
            }
            if(RoundRunning==false&&RestRunning==false&&roundFin==true)
            {
                rest(restLeftInMills);
            }
            else if(RoundRunning==false&&delayFin==true||restFin==true){
                roundLength(roundLeftInMills);
                RoundRunning=true;
            }else if(RoundRunning=false&&RestRunning==true&&roundFin==true){//condition 1
                rest(RestTimeInMills);
            }else if(RoundRunning==true&&delayFin==true||restFin==true) {
                roundLength(StartTimeInMills);
                RoundRunning=true;
            }else if(RoundRunning==false&&restFin==true) {
                roundLength(roundLeftInMills);
                RoundRunning=true;
            }else if(RoundRunning==true&&restFin==true) {
                roundLength(StartTimeInMills);
                RoundRunning=true;
            } else if(DelayRunning==true) {
                delay(DelayTimeInMills);
            }else if(DelayRunning==false){
                delay(delayLeftInMills);
            }else
                return;



        }

        public void pauseOnClick(View view){
            bPause.setVisibility(View.INVISIBLE);
            bStart.setVisibility(View.VISIBLE);
            if(DelayRunning==true) {
                DelayTimer.cancel();
                long min = TimeUnit.MILLISECONDS.toMinutes(delayLeftInMills);
                long sec = TimeUnit.MILLISECONDS.toSeconds(delayLeftInMills) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(delayLeftInMills));
                tvTimer.setText(String.format("%02d:%02d ", min, sec));
                DelayRunning = false;

            }
            else if(RoundRunning==true&&DelayRunning==false) {
                RoundTimer.cancel();
                long min = TimeUnit.MILLISECONDS.toMinutes(roundLeftInMills);
                long sec = TimeUnit.MILLISECONDS.toSeconds(roundLeftInMills) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(roundLeftInMills));
                tvTimer.setText(String.format("%02d:%02d ", min, sec));
                RoundRunning = false;
            }
            else if(RestRunning==true&&DelayRunning==false) {
                RestTimer.cancel();
                long min = TimeUnit.MILLISECONDS.toMinutes(restLeftInMills);
                long sec = TimeUnit.MILLISECONDS.toSeconds(restLeftInMills) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(restLeftInMills));
                tvTimer.setText(String.format("%02d:%02d ", min, sec));
                RestRunning= false;
            }else
                return;

        }

        public void stopOnClick(View view)
        {
            tvTimer.setText("00:00");
            RoundTimer.cancel();
            RestTimer.cancel();
            DelayTimer.cancel();
            // reset
            myCounter=0;

        }

    }