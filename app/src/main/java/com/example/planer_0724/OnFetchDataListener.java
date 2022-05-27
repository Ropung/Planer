package com.example.planer_0724;

import com.Models.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsApiRespons> {
    void onFetchData(List<NewsHeadlines> list, String message);
    void onError(String message);
}
