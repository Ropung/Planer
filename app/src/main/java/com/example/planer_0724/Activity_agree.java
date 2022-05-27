package com.example.planer_0724;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_agree extends AppCompatActivity {


    //회원가입 주의사항 동의 같은거 하는 엑티비티


    private EditText agree_test;
    private Button agree_btn_yes,agree_btn_no;
    private String id_str,pw_str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree);

        agree_btn_yes = (Button) findViewById(R.id.agree_btn_yes);
        agree_btn_no = (Button) findViewById(R.id.agree_btn_no);
        agree_test = (EditText) findViewById(R.id.agree_test);


        if(getIntent() != null){
            Intent getintent = getIntent();
            id_str = getintent.getStringExtra("id");
            pw_str = getintent.getStringExtra("pw");
            agree_test.setText(id_str);

        }


        agree_btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_agree.this, Activity_sign.class);
                intent.putExtra("id",id_str);
                intent.putExtra("pw",pw_str);
                startActivity(intent);
                finish();
            }
        });

        agree_btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_agree.this, Activity_login.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
