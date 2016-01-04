package com.example.zestinc.selfcontrol;

import android.content.SharedPreferences;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RelaxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax);

        //获取hp上限值
        final SharedPreferences sharedPreferences = getSharedPreferences("person", 0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final int hpMax = sharedPreferences.getInt("hpMax", -1);

        //长休息hp回复
        Button button = (Button) findViewById(R.id.longBreakButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hp = sharedPreferences.getInt("hp", -1);
                Log.d("当前HPMAX值为", Integer.toString(hpMax));
                hp = (hp + 10) % (hpMax + 1);
                editor.putInt("hp", hp);
                editor.commit();
            }
        });

        //短休息hp回复
        button = (Button) findViewById(R.id.shortBreakButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hp = sharedPreferences.getInt("hp", -1);
                Log.d("当前HPMAX值为", Integer.toString(hpMax));
                hp = (hp + 5) % (hpMax + 1);
                editor.putInt("hp", hp);
                editor.commit();
            }
        });

        //失败的休息hp减少
        button = (Button) findViewById(R.id.badBreakButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hp = sharedPreferences.getInt("hp", -1);
                hp = (hp - 5);
                hp = (hp < 0 ? 0 : hp);
                editor.putInt("hp", hp);
                editor.commit();
            }
        });
    }
}
