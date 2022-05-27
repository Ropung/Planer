package com.example.planer_0724;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Activity_intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
        @Override
        public void run() {
            Intent intent = new Intent(Activity_intro.this, Activity_login.class);
            startActivity(intent);
            finish();
        }
    }, 2000);
}


    //인트로 화면은 한번만 뛰울거기떄문에 퍼스상태가되면 엑티비티 피니쉬
    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }

    // 카카오톡 API사용관련 키해시 얻기 (1회용)
    public String getKeyHash(){
        try{
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),PackageManager.GET_SIGNATURES);
            if(packageInfo == null) return null;
            for(Signature signature: packageInfo.signatures){
                try{
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return android.util.Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                }catch (NoSuchAlgorithmException e){
                    Log.w("getKeyHash", "Unable to get MessageDigest. signature="+signature, e);
                }
            }
        }catch(PackageManager.NameNotFoundException e){
            Log.w("getPackageInfo", "Unable to getPackageInfo");
        }
        return null;
    }


}

