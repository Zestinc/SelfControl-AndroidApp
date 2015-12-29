package com.example.zestinc.selfcontrol;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
                        currentExp += duration/1000/60;
                        int level = sharedPreferences.getInt("level", 0);
                        while(currentExp >= (100 + level * 20)){
                            /*
                            ！！！！！！！！！
                            ！！！！！！！！
                            ！！！！！！
                            ！！！！！
                            ！！！！
                            ！！！
                            ！！
                            ！
                            此处应有升级恭喜提示
                             */
                            currentExp -= (100 + level*20);
                            level += 1;
                            editor.putInt("level", level);
                        }
                        editor.putLong("currentExp", currentExp);
                        editor.commit();
                    }
                }
                finish();
                /*
                提示信息，休息，温馨提示
                 */
            }
        });
    }
}
