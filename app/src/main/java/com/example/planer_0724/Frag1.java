package com.example.planer_0724;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;


import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Frag1 extends Fragment {

    private static final String TAG = "Frag1";


    private ViewGroup viewGroup;
    private ImageView frag1_btn_add;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapter adapter;
    private ArrayList<Data> dataList;
    String shared_data = "shared_data";

    private ImageView box_dialog_img_add;


    private final int GET_GALLERY_IMAGE = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //프래그먼트라 뷰그룹 컨텍스트설정
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.edit_box, null, false);




        //파인더
        frag1_btn_add = (ImageView) viewGroup.findViewById(R.id.frag1_btn_add);
        recyclerView = viewGroup.findViewById(R.id.frag_recycler);
        box_dialog_img_add = (ImageView) viewGroup.findViewById(R.id.box_dialog_img_add);


        //리싸이클러뷰 저장된것을 다시 로드
        loadData();


        //리싸이클러뷰 사이즈 고정
        recyclerView.setHasFixedSize(true);
        //매니저 설정(레이어 설정) 리니어로 설정
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //어댑터에 값과 참조를 넣어줌
        adapter = new CustomAdapter(dataList, getContext());

        ItemTouchHelperCallback mCallback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);


        //어댑터 클릭 리스너
        adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Toast.makeText(getContext(), "아이템 숏클릭", Toast.LENGTH_SHORT).show();
            }
        });


        frag1_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.edit_box, null, false);

                final Button box_dialog_btn = (Button) view.findViewById(R.id.box_dialog_btn);
                final EditText box_dialog_contents = (EditText) view.findViewById(R.id.box_dialog_contents);
                final EditText box_dialog_title = (EditText) view.findViewById(R.id.box_dialog_title);
                final TextView box_dialog_category = (TextView) view.findViewById(R.id.box_dialog_category);
                ImageView box_dialog_img_add = (ImageView) view.findViewById(R.id.box_dialog_img_add);

                builder.setView(view);
                Spinner spinner = (Spinner) view.findViewById(R.id.dialog_spinner);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        box_dialog_category.setText(""+parent.getItemAtPosition(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });



                //다이얼 로그에서 이미지 추가 ( 수정중 ) 프래그먼트 딴에서 불가능하다 생각이듬
                // Activity_move 에서 구
                box_dialog_img_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent. setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, GET_GALLERY_IMAGE);
                        Log.e(TAG, "onClick: 작동" );
                    }
                });





                box_dialog_btn.setText("만들기");


                final AlertDialog dialog = builder.create();
                box_dialog_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String strtitle = box_dialog_title.getText().toString();
                        String strcontents = box_dialog_contents.getText().toString();
                        String strcategory = box_dialog_category.getText().toString();

                        //셋팅한값 확인
                        Log.d(TAG, strtitle);
                        Log.d(TAG, strcontents);
                        Log.d(TAG, strcategory);

                        Data data = new Data(strtitle, strcontents, strcategory);
                        Log.d(TAG, "추가한 값 - " + String.valueOf(data));


                        dataList.add(0, data); //첫 줄에 삽입
                        adapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영
                        //dataList.add(data); //마지막 줄에 삽입
//                      adapter.notifyItemInserted(0);

                        saveData();

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        return viewGroup;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        View view1 = LayoutInflater.from(getContext())
                .inflate(R.layout.edit_box, null, false);

        box_dialog_img_add = (ImageView) view1.findViewById(R.id.box_dialog_img_add);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        Bitmap bitmap = null;

        try {
            if (data.getData() != null) {
                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);
//                Log.e(TAG, );
            } else {
                Log.e("Cold", "data is null");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (bitmap != null) {

            box_dialog_img_add.setImageBitmap(bitmap);
        } else {
            Log.e("Cold", "Bitmap is null");
        }
    }

    private void loadData() {
        SharedPreferences shared = getActivity().getSharedPreferences("Gjson", 0);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String Gjson = shared.getString("Gjson", null);
        Log.d(TAG, "loadData 값확인  - " + Gjson);

        Type Gtype = new TypeToken<ArrayList<Data>>() {
        }.getType();
        dataList = gson.fromJson(Gjson, Gtype);
        Log.d(TAG, "lodadData Gjson 값확인 넣어주기 - 작동");

        if (dataList == null) {
            dataList = new ArrayList<Data>();
            Log.d(TAG, "load Data - dataList인스턴스 - 생성");
        }
        Log.d(TAG, "loadData - 작동");
    }
    private void saveData() {

        SharedPreferences shared = getActivity().getSharedPreferences("Gjson", 0);
        //쉐어드
        SharedPreferences.Editor editor = shared.edit();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //Gson 변환후 쉐어드저장
        String Gjson = gson.toJson(dataList);
        Log.d(TAG, "saveData 저장된값 - " + Gjson);
        editor.putString("Gjson", Gjson);
        editor.commit();
        Log.d(TAG, "saveData - 작동");
    }


}