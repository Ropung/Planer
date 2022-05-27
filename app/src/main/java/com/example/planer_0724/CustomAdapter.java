package com.example.planer_0724;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>
        implements ItemTouchHelperListener {

    private static final String TAG = "Adapter";

    private ArrayList<Data> dataList = new ArrayList<>();
    private Context ct;
//    private int positon;
    private String shared_data = "shared_data";

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener listener = null;
    private OnItemLongClickListener Llistener = null;


    ////////////////////////    ////////////////////////

    // 어댑터 내에서 커스텀 리스너 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View v, int pos);
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.Llistener = listener;
    }

    ////////////////////////    ////////////////////////

    //어댑터 매개변수 설정(매서드)
    public CustomAdapter(ArrayList<Data> dataList, Context ct) {
        this.dataList = dataList;
        this.ct = ct;

    }


    //파인더 역활을할 뷰홀더 생성 뷰홀더 정의
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //로그 확인
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_frag, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    //뷰홀더에서 초기화한 값들을 불러와서 실질적으로 데이터들을 바인딩해주는 부분
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.title.setText(dataList.get(position).getTitle());
        holder.contents.setText(dataList.get(position).getContents());
        holder.category.setText(dataList.get(position).getCategory());

        holder.attention_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "조회수 리스너!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.views_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "좋아요 리스너!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.category_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "카테고리 설정!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //리싸이클러뷰의 크기 설정 (스크룰했을떄 최대크기)
    @Override
    public int getItemCount() {
        //물음표 연산자 3항 연산자
        return (null != dataList ? dataList.size() : 0);
    }


    // 리사이클러뷰 추가 메소드

    public void sh_remove(int position) {
        Log.d(TAG,"sh_remove 작동");

        try{
            SharedPreferences shared = ct.getSharedPreferences("Gjson", 0);
            SharedPreferences.Editor editor = shared.edit();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            String Gjson = shared.getString("Gjson", null);
            Type Gtype = new TypeToken<ArrayList<Data>>() {}.getType();

//            Data remove_data = gson.fromJson(Gjson,Gtype);
            Log.d(TAG,"dataList 값 확인1 -"+Gjson);


            editor.remove("Gjson");
            editor.commit();

            dataList.remove(position);
            notifyItemRemoved(position);

        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }


    public boolean onItemMove(int from_position, int to_position) {

        //이동할 객체 저장
        Data data = dataList.get(from_position);
        //이동할 객체 삭제
        dataList.remove(from_position);
        //이동하고 싶은 position에 추가
        dataList.add(to_position, data);
        //Adapter에 데이터 이동알림
        notifyItemMoved(from_position, to_position);

        saveData();
        return true;
    }

    public void saveData(){

        SharedPreferences shared = ct.getSharedPreferences("Gjson", 0);
        SharedPreferences.Editor editor = shared.edit();
        Gson gson = new Gson();

        String Gjson = gson.toJson(dataList);
        editor.putString("Gjson", Gjson);
        editor.commit();
        Log.d(TAG, "수정 쉐어드저장 : " + Gjson);
    }


    public void onItemSwipe(int position) {

        dataList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();


    }


    //inner 클레스 커스텀 뷰홀더 구현
    //컨텍스트 메뉴를 사용하려면 뷰홀더 상속받은 클레스에서 리스너를 구현해야됨
    public class CustomViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {
        //로그 찍어보기
        //바인더에서 참조할 변수
        protected TextView title;
        protected TextView contents;
        protected TextView category;
        protected TextView see;

        protected ImageView img;
        protected ImageView attention_iv;
        protected ImageView category_iv;
        protected ImageButton views_btn;
        protected CheckBox itemfrag_check;

        //아이템 뷰에서 클릭 이벤트를 직접 처리하고,
        //아이템 뷰는 뷰홀더 객체가 가지고 있으니,
        //아이템 클릭 이벤트는 뷰홀더에서 작성.

        public CustomViewHolder(View view) {
            //뷰 클레스를 이용해 파인터 호출
            super(view);

            this.title = (TextView) view.findViewById(R.id.itemfrag_title);
            this.contents = (TextView) view.findViewById(R.id.itemfrag_tv_contents);
            this.see = (TextView) view.findViewById(R.id.itemfrag_tv_see);
            this.category = (TextView) view.findViewById(R.id.itemfrag_tv_category);

            this.img = view.findViewById(R.id.itemfrag_img);
            this.attention_iv = (ImageView) view.findViewById(R.id.itemfrag_iv_attention);
            this.category_iv = (ImageView) view.findViewById(R.id.itemfrag_iv_category);
            this.views_btn = (ImageButton) view.findViewById(R.id.itemfrag_btn_like);
            this.itemfrag_check = (CheckBox) view.findViewById(R.id.itemfrag_check);


            //리스너를 현재 클래스에서 구현한다고 설정
            view.setOnCreateContextMenuListener(this);

            //아이템 클릭 이벤트처리
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (listener != null) {
                            listener.onItemClick(v, position);
                        }
                    }
                }
            });
        }


        // 꾹 누를때 뜨는 판업 메뉴 구현 !!
        //위에서 리스너를 등록해 줘야한다.
        //ID 1001 로 어떤 메뉴를 선택했는지 리스너에서 구분하게됨
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "수정");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");

            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }


        //컨텍스트 메뉴에서 항목 클릭시 동작을 설정
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case 1001:

                        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
                        // 다이얼로그를 보여주기 위해 edit_box.xml 파일을 사용합니다.
                        View view = LayoutInflater.from(ct)
                                .inflate(R.layout.edit_box, null, false);
                        builder.setView(view);

                        final Button box_dialog_btn = (Button) view.findViewById(R.id.box_dialog_btn);
                        final EditText box_dialog_title = (EditText) view.findViewById(R.id.box_dialog_title);
                        final EditText box_dialog_contents = (EditText) view.findViewById(R.id.box_dialog_contents);
                        final TextView box_dialog_category = (TextView) view.findViewById(R.id.box_dialog_category);
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




                        // 6. 해당 줄에 입력되어 있던 데이터를 불러와서 다이얼로그에 보여줍니다.
                        box_dialog_title.setText(dataList.get(getAdapterPosition()).getTitle());
                        box_dialog_contents.setText(dataList.get(getAdapterPosition()).getContents());
                        box_dialog_category.setText(dataList.get(getAdapterPosition()).getCategory());




                        final AlertDialog dialog = builder.create();
                        box_dialog_btn.setOnClickListener(new View.OnClickListener() {
                            // 7. 수정 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로
                            public void onClick(View v) {
                                String strtitle = box_dialog_title.getText().toString();
                                String strcontents = box_dialog_contents.getText().toString();
                                String strcategory = box_dialog_category.getText().toString();

                                Data data = new Data(strtitle, strcontents, strcategory);

                                dataList.set(getAdapterPosition(), data);
                                notifyItemChanged(getAdapterPosition());


                                saveData();
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                        break;

                    case 1002:  // 5. 삭제 항목을 선택시

                        // 6. ArratList에서 해당 데이터를 삭제하고 쉐어드값도 삭제
                        sh_remove(getAdapterPosition());
//                        dataList.remove(getAdapterPosition());

                        // 7. 어댑터에서 RecyclerView에 반영하도록 합니다.
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), dataList.size());
                        saveData();
                        break;
                }
                return true;
            }
        };


    }
}
