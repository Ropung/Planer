package com.example.planer_0724;
import android.app.Application;
import com.kakao.sdk.common.KakaoSdk;


public class GlobalApplication extends Application {
    private static GlobalApplication instance = null;


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        // 네이티브 앱 키로 초기화
        KakaoSdk.init(this, "1e0aa47a29e631ce77ebbe127e34ee02");

    }




}
