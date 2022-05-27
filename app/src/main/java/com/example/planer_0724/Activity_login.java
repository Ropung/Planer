package com.example.planer_0724;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.aware.DiscoverySessionCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kakao.sdk.user.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class Activity_login extends AppCompatActivity {

    private static final String TAG="Activity_login -";

    private EditText login_Input_id, login_input_pw;
    private Button login_btn, login_btn_sign,login_btn_naver;
    private CheckBox login_checkBox;
    private ImageButton login_btn_kakao;

    //저장할 쉐어드 이름
    String shared_data = "shared_data";
    //사용할 제이손 이름 (자동로그인)
    private Gson gson;

    private String set_id, set_pw;
    private String id_userdata, pw_userdata;
    private String User_sign;
    private String id_check, pw_check;
    private String id_intent, pw_intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //파인더
        login_Input_id = (EditText) findViewById(R.id.login_Input_id);
        login_input_pw = (EditText) findViewById(R.id.login_input_pw);
        login_btn = (Button) findViewById(R.id.login_btn);
        login_btn_sign = (Button) findViewById(R.id.login_btn_sign);
        login_btn_kakao = (ImageButton) findViewById(R.id.login_btn_kakao);
        login_btn_naver = (Button) findViewById(R.id.login_btn_naver);
        login_checkBox = (CheckBox) findViewById(R.id.login_checkBox);


        //인텐트
        Intent getintent = getIntent();
        id_intent = getintent.getStringExtra("id");
        pw_intent = getintent.getStringExtra("pw");



        //인텐트 값이 Null이 아니면 받아온값들을 셋팅해줌
        if (id_intent != null && pw_intent != null) {
            login_Input_id.setText(id_intent);
            login_input_pw.setText(pw_intent);
            Log.d("[인텐트 저장된 값]", "아이디:" + id_intent + ",비밀번호:" + pw_intent);
        } else {
            Log.d("[받을 인텐트 없음]", "- 작동");
        }


        //쉐어드 객체생성(수신)
        SharedPreferences shared = getSharedPreferences(shared_data, 0);

        //겟한 쉐어드를 스트링에 [키]지정해주기
        User_sign = shared.getString("User_sign", null);
        id_check = shared.getString("id_check", null);
        pw_check = shared.getString("pw_check", null);
        Log.d("[저장된 체크박스 확인]", "아이디:" + id_check + ",비밀번호:" + pw_check);
        gson = new Gson();
        UserData userdata = gson.fromJson(User_sign, UserData.class);
        Log.d("[저장된 userdata 값 :] - ", String.valueOf(userdata));

        //키값을 받은 스트링에 값이 있다면
        if (User_sign != null) {
            Log.d("[쉐어드]", "- 있음");
            Log.d("[쉐어드 값]", User_sign);

            if (userdata != null) {
                id_userdata = userdata.getId();
                pw_userdata = userdata.getPw();
                Log.d("[데이터 불러오기]", "- Id:" + id_userdata + "- Pw:" + pw_userdata);
            }


            // [쉐어드의 체크박스 확인후 저장된것이있으면 자동로그인 구현]
            if (id_check != null && pw_check != null) {
                login_Input_id.setText(id_check);
                login_input_pw.setText(pw_check);
                Log.d("[체크박스 불러오기]", "-Id:" + id_check + ",Pw:" + pw_check);
            }


            // id,pw 적힌값 정의
            set_id = login_Input_id.getText().toString();
            set_pw = login_input_pw.getText().toString();

            //체크박스에 체크된 이미 있는 회원정보일떈 바로 로그인
            if (id_userdata.equals(set_id) && pw_userdata.equals(set_pw)) {
                //아이디 비밀번호가 일치하면 로그인성공 사용자가 입력한값 로그확인
                Log.d("[데이터일치 자동로긴]: ", "아이디:" + set_id + "  , 비밀번호:" + set_pw);
                Intent intent = new Intent(Activity_login.this, MainActivity.class);
                startActivity(intent);
            }
        } else {
            Log.d("[저장된 쉐어드값 없음]: ", "");
        }


        //로그인버튼
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkboxClick(v);
                set_id = login_Input_id.getText().toString();
                set_pw = login_input_pw.getText().toString();

                if (User_sign != null) {
                    // id,pw 적힌값 정의

                    id_userdata = userdata.getId();
                    pw_userdata = userdata.getPw();
                    Log.d("[쉐어드 저장된 회원정보 (리스너)]", "- Id:" + id_userdata + "Pw:" + pw_userdata);
                    Log.d("[데이터 일치(리스너)]", "- Id:" + set_id + "Pw:" + set_pw);

                    if (set_id.equals(id_userdata) && set_pw.equals(pw_userdata)) {
                        Log.d("[저장된 데이터 일치] : ", "입력된 아이디 :" + set_id + "비밀번호 : " + set_pw);

                        //로그인 기록 저장
                        SharedPreferences shared = getSharedPreferences(shared_data, 0);
                        SharedPreferences.Editor editor = shared.edit();

                        editor.commit();

                        Toast.makeText(Activity_login.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Activity_login.this, MainActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(Activity_login.this, "아이디 비밀번호를 확인해주세요! ", Toast.LENGTH_SHORT).show();
                    Log.d("[로그인 실패] : ", "입력된 아이디 :" + set_id + "비밀번호 : " + set_pw);
                }

                if (set_id.equals(null) == set_pw.equals(null)){
                    Toast.makeText(Activity_login.this, "아이디 비밀번호를 확인해주세요! ", Toast.LENGTH_SHORT).show();
                    Log.d("[로그인 실패] : ", "입력된 아이디 :" + set_id + "비밀번호 : " + set_pw);
                }

            }
        });


        //회원가입 버튼
        login_btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_login.this, Activity_agree.class);
                intent.putExtra("id", login_Input_id.getText().toString());
                intent.putExtra("pw", login_input_pw.getText().toString());
                Toast.makeText(Activity_login.this, "회원가입!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken != null){
                    //로그인성공시 작업
                    Log.d(TAG,"카톡 로그인 성공!");
                    Intent intent = new Intent(Activity_login.this,MainActivity.class);
                    startActivity(intent);
                }
                if(throwable != null){
                    //로그인 실패
                    Toast.makeText(Activity_login.this,"로그인 실패!",Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"카톡 로그인 실패!");
                }
//                updataKakaoLoginUi();
                return null;
            }
        };

        //카카오 로그인 Api
        login_btn_kakao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(Activity_login.this)){
                    UserApiClient.getInstance().loginWithKakaoTalk(Activity_login.this,callback);
                }else{
                    UserApiClient.getInstance().loginWithKakaoAccount(Activity_login.this,callback);
                }
            }
        });

        //암시적인텐트 웹페이지 열기(네이버)
        login_btn_naver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_login.this, Activity_move.class);
                startActivity(intent);
            }
        });


    }




    @Override
    protected void onPause() {
        super.onPause();

//        SharedPreferences shared = getSharedPreferences(shared_data, 0);
//        SharedPreferences.Editor editor = shared.edit();
//
//        if (login_checkBox.isChecked()) {
//
//            //입력된 아이디와 비밀번호를 스트링으로 변환
//            id_check = login_Input_id.getText().toString();
//            pw_check = login_input_pw.getText().toString();
//
//            editor.putString("id_check", id_check);
//            editor.putString("pw_check", pw_check);
//            editor.commit();
//            //토스트로 잘 작동되었는지 확인
//            Toast.makeText(Activity_login.this, "자동체크 정보저장", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(Activity_login.this, "자동체크 안됨", Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // CheckBox 에 onClick 메소드 설정, CheckBox에 설정한 id 값으로 제어
    public void checkboxClick(View v) {

        id_check = login_Input_id.getText().toString();
        pw_check = login_input_pw.getText().toString();

        if (login_checkBox.isChecked()) {
            SharedPreferences shared = getSharedPreferences(shared_data, 0);
            SharedPreferences.Editor editor = shared.edit();

            editor.putString("id_check", id_check);
            editor.putString("pw_check", pw_check);
            editor.commit();
            Log.d("[체크박스저장] : ", "입력된 아이디 :" + id_check + "비밀번호 : " + pw_check);
        }
        else {
            SharedPreferences shared = getSharedPreferences(shared_data, 0);
            SharedPreferences.Editor editor = shared.edit();

            editor.remove("id_check");
            editor.remove("pw_check");
            editor.commit();
            Log.d("[체크박스 삭제] : ", "저장 안됨");
        }
    }

}