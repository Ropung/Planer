package com.example.planer_0724;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context,"memodb",null,DATABASE_VERSION);
    }



    @Override
    //onCreate() 함수에서 tb_memo 라는 테이블을 만듬
    public void onCreate(SQLiteDatabase db) {
        //칼럼은 _id, title, content 3개로 구성합니다.
        String memoSQL = "create table tb_memo"+
                "(_id integer primary key autoincrement,"+
                "strcontents,"+"strsee,"+"strcategory)";
        db.execSQL(memoSQL) ;
//만약에 테이블 생성을 잘못해서 수정해도 onCreate() 함수는 앱 설치 후
//최초 한 번만 호출되므로 수정한 부분이 반영이 되지는 않습니다.
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == DATABASE_VERSION){
            //잘못 만든 테이블을 삭제 후 (drop table)
            //다시 만들게 작성하였습니다. (onCreate(db))
            db.execSQL("drop table tb_memo");
        }
    }









}
