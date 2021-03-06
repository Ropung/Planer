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

        //?????????????????? ????????? ??????????????????
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.edit_box, null, false);




        //?????????
        frag1_btn_add = (ImageView) viewGroup.findViewById(R.id.frag1_btn_add);
        recyclerView = viewGroup.findViewById(R.id.frag_recycler);
        box_dialog_img_add = (ImageView) viewGroup.findViewById(R.id.box_dialog_img_add);


        //?????????????????? ??????????????? ?????? ??????
        loadData();


        //?????????????????? ????????? ??????
        recyclerView.setHasFixedSize(true);
        //????????? ??????(????????? ??????) ???????????? ??????
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //???????????? ?????? ????????? ?????????
        adapter = new CustomAdapter(dataList, getContext());

        ItemTouchHelperCallback mCallback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);


        //????????? ?????? ?????????
        adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Toast.makeText(getContext(), "????????? ?????????", Toast.LENGTH_SHORT).show();
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



                //????????? ???????????? ????????? ?????? ( ????????? ) ??????????????? ????????? ??????????????? ????????????
                // Activity_move ?????? ???
                box_dialog_img_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent. setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, GET_GALLERY_IMAGE);
                        Log.e(TAG, "onClick: ??????" );
                    }
                });





                box_dialog_btn.setText("?????????");


                final AlertDialog dialog = builder.create();
                box_dialog_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String strtitle = box_dialog_title.getText().toString();
                        String strcontents = box_dialog_contents.getText().toString();
                        String strcategory = box_dialog_category.getText().toString();

                        //???????????? ??????
                        Log.d(TAG, strtitle);
                        Log.d(TAG, strcontents);
                        Log.d(TAG, strcategory);

                        Data data = new Data(strtitle, strcontents, strcategory);
                        Log.d(TAG, "????????? ??? - " + String.valueOf(data));


                        dataList.add(0, data); //??? ?????? ??????
                        adapter.notifyDataSetChanged(); //????????? ???????????? ????????? ??????
                        //dataList.add(data); //????????? ?????? ??????
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
        Log.d(TAG, "loadData ?????????  - " + Gjson);

        Type Gtype = new TypeToken<ArrayList<Data>>() {
        }.getType();
        dataList = gson.fromJson(Gjson, Gtype);
        Log.d(TAG, "lodadData Gjson ????????? ???????????? - ??????");

        if (dataList == null) {
            dataList = new ArrayList<Data>();
            Log.d(TAG, "load Data - dataList???????????? - ??????");
        }
        Log.d(TAG, "loadData - ??????");
    }
    private void saveData() {

        SharedPreferences shared = getActivity().getSharedPreferences("Gjson", 0);
        //?????????
        SharedPreferences.Editor editor = shared.edit();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //Gson ????????? ???????????????
        String Gjson = gson.toJson(dataList);
        Log.d(TAG, "saveData ???????????? - " + Gjson);
        editor.putString("Gjson", Gjson);
        editor.commit();
        Log.d(TAG, "saveData - ??????");
    }


}