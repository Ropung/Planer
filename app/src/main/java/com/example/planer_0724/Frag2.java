package com.example.planer_0724;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;


public class Frag2 extends Fragment{

    private ViewGroup viewGroup;
    private Button button1, button2;




    //뷰페이저 스레드
    private ViewPager viewPager;
    private TextViewPagerAdapter pagerAdapter;
    private Context ct;

    //뷰페이저 설정
    int currentPage = 0;
    Timer pager_timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);
        ct = viewGroup.getContext();

        //findViewById


        button1 = viewGroup.findViewById(R.id.button1);
        button2 = viewGroup.findViewById(R.id.button2);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Activity_Map.class);
                startActivity(intent);
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),News.class);
                startActivity(intent);
            }
        });


        //뷰페이저
        viewPager = viewGroup.findViewById(R.id.view_pager);
        pagerAdapter = new TextViewPagerAdapter(ct);
        viewPager.setAdapter(pagerAdapter);

        final Handler pager_handler = new Handler();

        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == 5) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        pager_timer = new Timer();
        pager_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                pager_handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);





        return viewGroup;
    }


}
