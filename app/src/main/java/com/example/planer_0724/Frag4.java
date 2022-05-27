package com.example.planer_0724;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;


public class Frag4 extends Fragment {

    private static final String TAG = "Frag4";

    ViewGroup viewGroup;

    private EditText frag4_input_id, frag4_nick;

    private String id_sh,pw_sh,nick_sh,name_sh,age_sh,pn_sh;

    private ImageButton frag4_btn_fix,frag4_btn_cancel;

    private MainActivity main;

    private String shared_data = "shared_data";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        SharedPreferences shared = context.getSharedPreferences(shared_data, 0);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment4, container, false);
        frag4_btn_cancel = (ImageButton) viewGroup.findViewById(R.id.frag4_btn_cancel);






        frag4_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences shared = getActivity().getSharedPreferences(shared_data,0);
                SharedPreferences.Editor editor = shared.edit();

                String id_check = shared.getString("id_check",null);
                String pw_check = shared.getString("pw_check",null);

                editor.remove("id_check");
                editor.remove("pw_check");
                editor.commit();

                Log.d(TAG,"로그아웃 - 작동");

                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Log.d(TAG,"카카오톡 로그아웃 - 작동");
                        return null;
                    }
                });

                Intent intent = new Intent(getActivity(), Activity_login.class);
                startActivity(intent);
            }
        });






        return viewGroup;
    }





}