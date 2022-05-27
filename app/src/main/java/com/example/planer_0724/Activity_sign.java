package com.example.planer_0724;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class Activity_sign extends AppCompatActivity {
    private Button sign_btn_Confirm, sign_btnConfirm_id,
            sign_btnConfirm_pw, sign_btnConfirm_PN, sign_btnConfirm_nickname;

    private static final String tag = "회원가입";



    private EditText sign_input_id, sign_input_pw, sign_inputConfirm_pw, sign_input_nick,
            sign_input_age, sign_input_name, sign_input_pn;

    private String id_str, pw_str;
    private String id_sh, pw_sh,nick_sh,name_sh,age_sh,pn_sh;


    //gson으로 여러값들을 저장
    Gson gson;

    //쉐어드에 저장할 json
    String shared_data = "shared_data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //파인드(Edit)
        sign_input_id = (EditText) findViewById(R.id.sign_input_id);
        sign_input_pw = (EditText) findViewById(R.id.sign_input_pw);
        sign_inputConfirm_pw = (EditText) findViewById(R.id.sign_inputConfirm_pw);
        sign_input_nick = (EditText) findViewById(R.id.sign_input_nick);
        sign_input_age = (EditText) findViewById(R.id.sign_input_age);
        sign_input_name = (EditText) findViewById(R.id.sign_input_name);
        sign_input_pn = (EditText) findViewById(R.id.sing_input_PN);

        //파인드(btn)
        sign_btnConfirm_id = (Button) findViewById(R.id.sign_btnConfirm_id);
        sign_btnConfirm_pw = (Button) findViewById(R.id.sign_btnConfrim_pw);
        sign_btnConfirm_nickname = (Button) findViewById(R.id.sign_btnConfirm_nickname);
        sign_btnConfirm_PN = (Button) findViewById(R.id.sign_btnConfirm_PN);
        sign_btn_Confirm = (Button) findViewById(R.id.sign_up_btn);


        Intent intent = getIntent();

        gson = new GsonBuilder().create();

        //인텐트 받기
        if(getIntent() != null ){
            id_str = intent.getStringExtra("id");
            pw_str = intent.getStringExtra("pw");
            sign_input_id.setText(id_str);
            sign_input_pw.setText(pw_str);
        }


        //중복확인
        sign_btnConfirm_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sign_input_id.getText().toString().equals(null)) {
                    Toast.makeText(Activity_sign.this, "아이디(빈칸)를 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Activity_sign.this, "사용가능한 아이디입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //비밀번호 중복확인
        sign_btnConfirm_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sign_input_pw.getText().toString().equals(sign_inputConfirm_pw.getText().toString())) {
                    Toast.makeText(Activity_sign.this, "비밀번호가 맞습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    sign_inputConfirm_pw.setText(null);
                    Toast.makeText(Activity_sign.this, "비밀번호를 똑같이 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //닉네임 중복확인
        sign_btnConfirm_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sign_input_nick.getText().toString().equals(null)) {
                    Toast.makeText(Activity_sign.this, "아이디(빈칸)를 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else if(sign_input_nick.getText().toString().equals(nick_sh)){
                    Toast.makeText(Activity_sign.this, "이미 사용중인 닉네임입니다.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Activity_sign.this, "사용가능한 아이디입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });




        //회원가입 확인버튼
        sign_btn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sign_input_id.getText().toString().equals("")) {
                    Toast.makeText(Activity_sign.this, "아이디를 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else if (sign_input_pw.getText().toString().equals("")) {
                    Toast.makeText(Activity_sign.this, "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else if (sign_inputConfirm_pw.getText().toString().equals("")) {
                    Toast.makeText(Activity_sign.this, "비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
                } else if (sign_input_nick.getText().toString().equals("")) {
                    Toast.makeText(Activity_sign.this, "닉네임을 확인해주세요!", Toast.LENGTH_SHORT).show();
                } else if (sign_input_name.getText().toString().equals("")) {
                    Toast.makeText(Activity_sign.this, "이름을 확인해주세요!", Toast.LENGTH_SHORT).show();
                } else if (sign_input_age.getText().toString().equals("")) {
                    Toast.makeText(Activity_sign.this, "나이를 확인해주세요!", Toast.LENGTH_SHORT).show();
                } else if (sign_input_pn.getText().toString().equals("")) {
                    Toast.makeText(Activity_sign.this, "휴대폰번를 확인해주세요!", Toast.LENGTH_SHORT).show();
                } else {

                    //쉐어드 저장 (쉐어드 객체 생성)
                    SharedPreferences shared = getSharedPreferences(shared_data, 0);
                    //쉐어드 에디터 초기화
                    SharedPreferences.Editor editor = shared.edit();


                    //파인더를 스트링화 시켜준다.
                    id_sh = sign_input_id.getText().toString();
                    pw_sh = sign_input_pw.getText().toString();
                    nick_sh = sign_input_nick.getText().toString();
                    name_sh = sign_input_name.getText().toString();
                    age_sh = sign_input_age.getText().toString();
                    pn_sh = sign_input_pn.getText().toString();

                    //유저정보를 담을 스트링
                    String User_sign;
                    UserData userdata = new UserData(id_sh,pw_sh,nick_sh,name_sh,age_sh,pn_sh);
                    User_sign = gson.toJson(userdata,UserData.class);
                    editor.putString("User_sign",User_sign);
                    Log.d("JSON으로 저장한 쉐어드값 확인  : " ,User_sign);
                    editor.commit();

                    //apply 또는 commit을 통해 넣어준 value을 저장해준다.
                    //remove() 함수를 이용하여 원하는 key 값을 지워줄수있다.


                   //인텐트 이동저장
                    Intent intent = new Intent(Activity_sign.this, Activity_login.class);
                    intent.putExtra("id", sign_input_id.getText().toString());
                    intent.putExtra("pw", sign_inputConfirm_pw.getText().toString());

                    Toast.makeText(Activity_sign.this, "회원가입 완료!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();


                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }



}