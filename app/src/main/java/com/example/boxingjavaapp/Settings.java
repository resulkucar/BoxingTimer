package com.example.boxingjavaapp;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import java.util.concurrent.TimeUnit;

public class Settings extends AppCompatActivity {
    EditText inNumRound;
    EditText inRoundTime;
    EditText inDelay;
    EditText inRest;
    Button nextPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inNumRound = (EditText) findViewById(R.id.numOfRound);
        inRoundTime = (EditText) findViewById(R.id.roundTime);
        inDelay = (EditText) findViewById(R.id.startDelay);
        inRest = (EditText) findViewById(R.id.restTime);
        nextPage= (Button) findViewById(R.id.nextPage);

    }

    public int NumberOfRounds(int n){
        //String a =inNumRound.getText().toString();
        int numberRounds = 5;//Integer.parseInt(a);
        return numberRounds;

    }



}
