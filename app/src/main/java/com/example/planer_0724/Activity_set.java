package com.example.planer_0724;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Activity_set extends AppCompatActivity {

    private ToggleButton tg_btn_1;
    private ToggleButton tg_btn_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);


        tg_btn_1 = (ToggleButton) findViewById(R.id.tg_btn_1);
        tg_btn_2 = (ToggleButton) findViewById(R.id.tg_btn_2);


        SharedPreferences shared = getSharedPreferences("setting", 0);
        boolean tg_sh1 = shared.getBoolean("tg_sh1", false);
        boolean tg_sh2 = shared.getBoolean("tg_sh2", false);


        tg_btn_1.setChecked(tg_sh1);
        tg_btn_2.setChecked(tg_sh2);
        Log.d("진동 - ",Boolean.toString(tg_sh1));
        Log.d("소리 - ",Boolean.toString(tg_sh2));


        tg_btn_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences shared = getSharedPreferences("setting", 0);

                if (isChecked == true) {
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putBoolean("tg_sh1", true);
                    editor.commit();
                    Toast.makeText(Activity_set.this, "진동- ON", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putBoolean("tg_sh1", false);
                    editor.commit();
                    Toast.makeText(Activity_set.this, "진동- OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tg_btn_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences shared = getSharedPreferences("setting", 0);

                if (isChecked == true) {
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putBoolean("tg_sh2", true);
                    editor.commit();
                    Toast.makeText(Activity_set.this, "소리- ON", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putBoolean("tg_sh2", false);
                    editor.commit();
                    Toast.makeText(Activity_set.this, "소리- OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences shared = getSharedPreferences("Gjson", 0);
        //쉐어드
        SharedPreferences.Editor editor = shared.edit();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();


    }


}