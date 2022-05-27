package com.example.planer_0724;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class Data {

    private String title;
    private String contents;
    private String category;


    public Data( String strtitle,String strcontents, String strcategory) {

        this.title = strtitle;
        this.contents = strcontents;
        this.category = strcategory;
    }



    public String getTitle() {return title;}

    public void setTitle(String importance) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



}

