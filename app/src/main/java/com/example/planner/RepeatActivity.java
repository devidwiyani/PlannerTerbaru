package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;

public class RepeatActivity extends AppCompatActivity {

    SharedPrefManager spm;
    EditText inputDailyPlan, inputStartTime, inputEndTime;
    DBHelper dbHelper;
    ArrayList<Repeat> repeatArrayList;
    RepeatAdapter customAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);

        int idUser= spm.getSPId(this);

        repeatArrayList = new ArrayList<>();
        dbHelper = new DBHelper(RepeatActivity.this);

        repeatArrayList = dbHelper.readRepeat(idUser);

        customAdapter = new RepeatAdapter(repeatArrayList, RepeatActivity.this);
        recyclerView = findViewById(R.id.recyler_repeat);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RepeatActivity.this, RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(customAdapter);
    }
}