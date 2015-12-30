package com.example.zestinc.selfcontrol;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DialerFilter;
import android.widget.Toast;

public class ChallengeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        Button button = (Button) findViewById(R.id.successButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("person", 0);
                Long beginTime = sharedPreferences.getLong("time", -1);
                Long endTime = System.currentTimeMillis();
                if(beginTime == -1){
//                    获取开始时间失败，输出程序错误信息
                    Log.e("error", " 获取开始时间失败");
                }
                else {
                    Long duration = endTime - beginTime;
                    Log.d("time to exp.", duration.toString());
                    if(duration > 24*60*60*1000){
                        //持续时间超过1天，惊为天人
                    }
                    else{
                        //正常计算时间转化为的经验值
                        /*
                        经验值计算方式（暂定）： 1EXP/MIN
                        自控力上限：MaxSelfControlValue = 20 + Level
                         */
                        Long currentExp = sharedPreferences.getLong("currentExp", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        //正常经验值添加，每分钟1经验
//                        currentExp += duration/1000/60;
                        //临时经验值添加，每秒10经验
                        currentExp += duration/100;
                        int level = sharedPreferences.getInt("level", 0);
                        while(currentExp >= (100 + level * 20)){
                            /*
                            ！！！！！！！！！
                            ！！！！！！！！
                            ！！！！！！
                            ！！！！！
                             */
                            Toast.makeText(ChallengeActivity.this, R.string.upgrade, Toast.LENGTH_LONG).show();
                            /*
                            ！！！！
                            ！！！
                            ！！
                            ！
                            此处应有升级恭喜提示！！
                             */

                            currentExp -= (100 + level*20);
                            level += 1;
                            Log.d("level up", Integer.toString(level));
                            editor.putInt("level", level);
                            editor.commit();
                        }
                        editor.putLong("currentExp", currentExp);
                        editor.commit();
                    }
                }
                /*
                弹窗提示信息，休息，温馨提示
                 */
                AlertDialog.Builder builder = new AlertDialog.Builder(ChallengeActivity.this);
                builder .setMessage(R.string.challengeSuccess)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.notOk, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        })
                        .create()
                        .show();

                Log.d("break msg", "break!");

            }
        });

        button = (Button) findViewById(R.id.failButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("person", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Long currentExp = sharedPreferences.getLong("currentExp", -1);
                if(currentExp == -1){
                    Log.e("error", "getCurrentExp error");
                }
                int level = sharedPreferences.getInt("level", -1);
                currentExp -= 10 + level*10;
                editor.putLong("currentExp", currentExp);
                Log.d("currentExp --", currentExp.toString());
                while(currentExp <= 0){
                    level -= 1;
                    currentExp = (100 + level*20) + currentExp;
                    editor.putInt("level", level);
                    editor.putLong("currentExp", currentExp);
                    Log.d("level --", Integer.toString(level));
                    Toast.makeText(getApplicationContext(), "喵~~~~~~降级了~喵", Toast.LENGTH_LONG).show();
                }
                editor.commit();
                /*
                弹窗提示信息，休息，温馨提示
                 */
                AlertDialog.Builder builder = new AlertDialog.Builder(ChallengeActivity.this);
                builder.setMessage(R.string.challengeFail)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
                            }
                        })
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.notOk, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }
}
