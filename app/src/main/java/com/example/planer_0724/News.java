package com.example.planer_0724;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.Models.NewsApiRespons;
import com.Models.NewsHeadlines;

import java.util.List;

public class News extends AppCompatActivity implements SelectListener , View.OnClickListener {

    RecyclerView recyclerView;
    CustomAdapterNews adapterNews;

    ProgressDialog dialog;
    Button b1,b2,b3,b4,b5,b6,b7;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                dialog.setTitle("새로운 뉴스 검색중 ..");
                dialog.show();
                RequestManager manager = new RequestManager(News.this);
                manager.getNewsHeadlines(listener, "general",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setTitle("새로운 뉴스 받는중 ..");
        dialog.show();

        b1 = findViewById(R.id.btn_1);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.btn_2);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn_3);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn_4);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn_5);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn_6);
        b6.setOnClickListener(this);
        b7 = findViewById(R.id.btn_7);
        b7.setOnClickListener(this);




        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "general",null);
    }


    private final OnFetchDataListener<NewsApiRespons> listener = new OnFetchDataListener<NewsApiRespons>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if (list.isEmpty()){
                Toast.makeText(News.this,"뉴스를 찾을수없습니다!",Toast.LENGTH_SHORT).show();
            }
            else{
                showNews(list);
                dialog.dismiss();
            }

        }

        @Override
        public void onError(String message) {
            Toast.makeText(News.this,"오류발생!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapterNews = new CustomAdapterNews(this,list,this);
        recyclerView.setAdapter(adapterNews);
    }


    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
                startActivity(new Intent(News.this,DetailsActivity.class)
                .putExtra("data",headlines));
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();

        dialog.setTitle("새로운 카테고리 불러오는중..");
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, category,null);
    }
}