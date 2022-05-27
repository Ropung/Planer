package com.example.planer_0724;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class TextViewPagerAdapter extends PagerAdapter {
    private Context ct = null;


    // Context 를 전달받아 context 에 저장하는 생성자 추가.
    public TextViewPagerAdapter(Context ct) {
        this.ct = ct;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // position 값을 받아 주어진 위치에 페이지를 생성한다

        View view = null;

        if(ct != null) {
            // LayoutInflater 를 통해 "/res/layout/page.xml" 을 뷰로 생성.
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_pager, container, false);

            TextView pager_test = view.findViewById(R.id.pager_test);
            pager_test.setText("광고" + position);

        }
        // 뷰페이저에 추가
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // position 값을 받아 주어진 위치의 페이지를 삭제한다
        container.removeView((View) object);
    }



    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
