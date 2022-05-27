package com.example.planer_0724;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.text.SimpleDateFormat;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {

    //태그 위치 지정
    private static final String tag = "[메인] : ";


    //바텀 네비
    private BottomNavigationView main_bottom_navi;
    //뷰
    private TextView header_id, header_nick, user_time, login_time;


    //쉐어드 변수
    String shared_data = "shared_data";
    String User_sign;

    private NavigationView navigationView;
    private View drawerView, header;
    private Gson gson;


    private DrawerLayout drawerLayout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //파인더뷰로 연결
        main_bottom_navi = (BottomNavigationView) findViewById(R.id.main_bottom_navi);
        navigationView = (NavigationView) findViewById(R.id.navigation);



        //헤더 연결
        header = navigationView.getHeaderView(0);
        header_nick = (TextView) header.findViewById(R.id.header_nick);
        header_id = (TextView) header.findViewById(R.id.header_id);


//      //---------시작할떄 필요한 처리-------------

        //카카오 정보 불러오기
        updataKakaoLoginUi();

        //쉐어드 값 불러오기 (쉐어드 객체 생성)
        SharedPreferences shared = getSharedPreferences(shared_data, 0);

        //쉐어드 키 불러오기
        gson = new GsonBuilder().create();
        User_sign = shared.getString("User_sign", null);
        UserData userdata = gson.fromJson(User_sign, UserData.class);
        Log.d("[메인 쉐어드 값] - ", User_sign);

        //헤더 닉네임 아이디 지정
        String nick_userdata = userdata.getNick();
        String id_userdata = userdata.getId();
        Log.d("[아이디 값 확인] : ", id_userdata);
        Log.d("[닉네임 값 확인] : ", nick_userdata);

        header_id.setText(id_userdata);
        header_nick.setText(nick_userdata);






        //처음 프레그 먼트 설정  (FrameLayout에 fragment.xml 띄우기)
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, new Frag1()).commit();

        //바텀 네비게이션뷰 클릭리스너
        main_bottom_navi.setOnNavigationItemSelectedListener(new BottomNavigationView.
                OnNavigationItemSelectedListener() {

            //리스너에 해당되는 프래그먼트 뛰우기
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.item_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Frag1()).commit();
                        break;
                    case R.id.item_list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Frag2()).commit();
                        break;
                    case R.id.item_chart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Frag3()).commit();
                        break;
                    case R.id.item_user:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Frag4()).commit();
                        break;
                }
                return true;
            }
        });

    } //온크리에이트 end

    //카톡정보 받아서 적용
    private void updataKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if(user != null) {
                    Log.d(tag, "invoke : id -" + user.getId());
                    Log.d(tag, "invoke : nickname -" + user.getKakaoAccount().getProfile().getNickname());

                    header_id.setText(user.getId().toString());
                    header_nick.setText(user.getKakaoAccount().getProfile().getNickname());
//                    Glide.with(header_imageView).load(user.getKakaoAccount().getProfile()
//                            .getThumbnailImageUrl()).circleCrop().into(header_imageView);
                }else{

                }
                return null;
            }
        });
    }

}
