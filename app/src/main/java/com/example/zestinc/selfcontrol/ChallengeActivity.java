package com.example.zestinc.selfcontrol;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChallengeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        Button button = (Button) findViewById(R.id.successButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("challendge", 0);
                Long beginTime = sharedPreferences.getLong("Time", -1);
                Long endTime = System.currentTimeMillis();
                if(beginTime == -1){
//                    System.out.print();
                }
            }
        });
    }
}
