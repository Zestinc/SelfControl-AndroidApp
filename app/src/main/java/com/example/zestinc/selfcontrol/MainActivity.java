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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public DateFormat currentDate = new SimpleDateFormat("HH");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化所有用户的数值
        SharedPreferences sharedPreferences = getSharedPreferences("person", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        final int level = sharedPreferences.getInt("level", -1);
        if(level == -1){
            editor.putInt("level", 0);
            editor.commit();
//            Log.d("初始化成功", "-0-");
            Log.d("level 初始化完成", Integer.toString(level));
        }
        Long currentExp = sharedPreferences.getLong("currentExp", -1);
        if(currentExp == -1){
            editor.putLong("currentExp", 0);
            editor.commit();
            Log.d("currentExp 初始化完成", "0");
        }
        final int hpMax = sharedPreferences.getInt("hpMax", -1);
        if(hpMax == -1){
            editor.putInt("hpMax", 20 + 2*level);
            editor.commit();
            Log.d("hpMax 初始化完成", Integer.toString(hpMax));
        }
        final int hp = sharedPreferences.getInt("hp", -1);
        if(hp == -1){
            editor.putInt("hp", hpMax);
            editor.commit();
            Log.d("hp 初始化完成", Integer.toString(hpMax));
        }

        //每天早上更新hp
//        DateFormat dateFormat = new SimpleDateFormat("HH");
//        Date date = new Date();
//        dateFormat.format(date);
//        Log.d("HH", dateFormat.toString());
//        if(currentDate != dateFormat) {
//            String hour = dateFormat.toString();
//            Log.d("getTimeInstace内容", dateFormat.toString());
//            Log.d("当前日期处理后的hour", hour);
//            if(Integer.parseInt(hour) > 6){
//                level = sharedPreferences.getInt("level", -1);
//                int hp = 20 + level * 2;
//                editor.putInt("hp", hp);
//                editor.commit();
//                Log.d("早上hp恢复完成, 当前hp为", Integer.toString(hp));
//            }
//        }

        //Implement of challenge Button event.
        Button button = (Button) findViewById(R.id.challengeButton);
        button.setOnClickListener(new View.OnClickListener() {
            //开始挑战的flag
            public boolean flag;
            //惩罚系数
            public int coefficient;

            @Override
            public void onClick(View v) {
                //Record begin time.
                SharedPreferences sharedPreferences = getSharedPreferences("person", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //初始化数值
                flag = true;
                coefficient = 1;

                //hp太低的建议
                int hp = sharedPreferences.getInt("hp", -1);
                if(hp > 0 && hp <= 5){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("当前hp不多，建议以清理\"小怪\"为主，切莫挑战需要半小时以上的大Boss");
                    builder.setPositiveButton("=。= 知道啦", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            flag = true;
                        }
                    });
                    builder.setNegativeButton("好吧，我歇息一下0.0", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            flag = false;
                        }
                    });
                    builder.create().show();
                }
                else if(hp < 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setMessage("hp需要恢复，不建议继续进行挑战~=w=~");
                    builder.setNegativeButton("好吧，我歇息一下0.0", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            flag = false;
                        }
                    });
                    builder.setPositiveButton("-.- 不管，继续挑战（失败的话惩罚加倍(⊙o⊙)哦）", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            flag = true;
                            coefficient = 2;
                        }
                    });
                }

                editor.putLong("time", System.currentTimeMillis());
                editor.commit();

                //Go into challenge activity
                Intent intent = new Intent(MainActivity.this, ChallengeActivity.class);
                intent.putExtra("coefficient", coefficient);
                startActivity(intent);
            }
        });

        //Implement of break button
        button = (Button) findViewById(R.id.breakButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RelaxActivity.class);
                startActivity(intent);
            }
        });

        //Implement of Motto Setting Button
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
