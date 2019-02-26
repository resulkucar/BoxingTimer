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

public class MainActivity extends AppCompatActivity {
    EditText inNumRound;
    EditText inRoundTime;
    EditText inDelay;
    EditText inRest;
    Button nextPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        inNumRound = (EditText) findViewById(R.id.numOfRound);
        inRoundTime = (EditText) findViewById(R.id.roundTime);
        inDelay = (EditText) findViewById(R.id.startDelay);
        inRest = (EditText) findViewById(R.id.restTime);
        nextPage= (Button) findViewById(R.id.nextPage);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity();
            }
        });
    }

    public void startActivity() {
        Intent intent = new Intent (this, Main2Activity.class);
        startActivity(intent);

    }
}
