package com.example.planner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DailyActivity extends AppCompatActivity {

    Button btnCretaeDaily, btnSave;
    EditText inputDailyPlan, inputStartTime, inputEndTime;
    DBHelper dbHelper;
    ArrayList<DailyPlaner> dailyArrayList, values, dailyList;
    RecyclerViewAdapter customAdapter;
    RecyclerView recyclerView;
    SharedPrefManager spm;
    int statusRepeat, saveId, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        int idUser= spm.getSPId(this);

        dailyArrayList = new ArrayList<>();
        dbHelper = new DBHelper(DailyActivity.this);

        Intent bundle = getIntent();
        statusRepeat = bundle.getIntExtra("status_repeat", 0);
        saveId = bundle.getIntExtra("save_id", 0);
        userId = bundle.getIntExtra("user_id", 0);

        if(statusRepeat == 0){
            dailyList = dbHelper.readDaily(idUser);
            if (dailyList.size() == 0){
                Toast.makeText(DailyActivity.this, "saveid :" +idUser, Toast.LENGTH_SHORT).show();
                dailyArrayList = dbHelper.readDailyRepeatList();
            }else{
                dailyArrayList = dbHelper.readDaily(idUser);
            }
        }else{
            values = dbHelper.readDailyRepeat(saveId);
            dbHelper.insertDifDate(values, saveId, idUser);
            dailyArrayList = dbHelper.readDaily(idUser);
        }

        customAdapter = new RecyclerViewAdapter(dailyArrayList, DailyActivity.this);
        recyclerView = findViewById(R.id.daftarCart);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DailyActivity.this, RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(customAdapter);

        btnCretaeDaily = findViewById(R.id.btn_create_daily);
        btnCretaeDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DailyActivity.this, CreateDailyActivity.class);
                ContentValues values = new ContentValues();
                values.put("id", idUser);
                startActivity(intent);
            }
        });

        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<DailyPlaner> list = dailyArrayList;


                AlertDialog.Builder dialog1 = new AlertDialog.Builder(DailyActivity.this);
                View view = getLayoutInflater().inflate(R.layout.alert_save_daily,null);

                EditText input_title = (EditText) view.findViewById(R.id.input_title);
                EditText input_deskripsi = (EditText) view.findViewById(R.id.input_deskripsi);

                view.findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String title = input_title.getText().toString();
                        String deskripsi = input_deskripsi.getText().toString();
                        int userId = idUser;
                        Toast.makeText(v.getRootView().getContext(), "Save Daily "+title, Toast.LENGTH_LONG).show();

                        long id=dbHelper.saveDaily(userId, title, deskripsi);
                        dbHelper.updateSaveDaily((int) id,dailyArrayList);

                    }
                });

                dialog1.setView(view);
                AlertDialog dialog2 = dialog1.create();
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.show();
            }
        });
    }
}