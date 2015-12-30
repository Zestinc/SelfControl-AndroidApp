package com.example.zestinc.selfcontrol;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化所有用户的数值
        SharedPreferences sharedPreferences = getSharedPreferences("person", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int level = sharedPreferences.getInt("level", -1);
        if(level == -1){
            level = 0;
            editor.putInt("level", level);
            editor.putLong("currentExp", 0);
        }

        Button button = (Button) findViewById(R.id.challengeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Record begin time.
                SharedPreferences sharedPreferences = getSharedPreferences("person", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("time", System.currentTimeMillis());
                editor.commit();

                //Go into challenge activity
                Intent intent = new Intent(MainActivity.this, ChallengeActivity.class);
                startActivity(intent);
            }
        });

        button = (Button) findViewById(R.id.breakButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RelaxActivity.class);
                startActivity(intent);
            }
        });

        button = (Button) findViewById(R.id.MottoSettingButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();

        View decorView = getWindow().getDecorView();
        // Hide the status bar.D
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //Hide ActionBar (always do it when hiding the status bar)
        //NOT WORK, NO CLEAR REASON, MAYBE SINCE IT IS USING A BLANK ACTIVITY
//        ActionBar actionBar = getActionBar();
//        actionBar.hide();

        //Set title center
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        //Update the showing of Lv.
        SharedPreferences sharedPreferences = getSharedPreferences("person", 0);
        int level = sharedPreferences.getInt("level", 0);
        Long currentExp = sharedPreferences.getLong("currentExp", -1);
        Log.d("level", Integer.toString(level));
        Log.d("CurExp MainAct", currentExp.toString());
        TextView textView = (TextView) findViewById(R.id.levelTitle);
        textView.setText("Lv." + Integer.toString(level));
    }
}
