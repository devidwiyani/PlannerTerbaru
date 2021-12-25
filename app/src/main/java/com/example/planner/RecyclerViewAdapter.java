package com.example.planner;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    ArrayList<DailyPlaner> dailyArrayList;
    private Context context;

    //konstruktor
    RecyclerViewAdapter(ArrayList<DailyPlaner> dailyPlanerArrayList, Context context){
        this.dailyArrayList = dailyPlanerArrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //mengambil data assignmentArrayList berdsarkan posisi
        Log.d("tesError",Integer.toString(dailyArrayList.size()));
        DailyPlaner list = dailyArrayList.get(position);
        //setText sesuai data pada list
        holder.dailyPlan.setText(list.getTambahdailyplaner());
        holder.startTime.setText(list.getTambahstarttime());
        holder.endTime.setText(list.getTambahendtime());
        if(list.getTambahstatus().equals("done")){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }

//        ke update
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, CreateDailyActivity.class);

                i.putExtra("id", String.valueOf(list.getId()));
                i.putExtra("dailyPlan", list.getTambahdailyplaner());
                i.putExtra("startTime", list.getTambahstarttime());
                i.putExtra("endTime", list.getTambahendtime());

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dailyArrayList.size();
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView dailyPlan, startTime, endTime;
        ImageButton btnDelete, btnDone;
        DBHelper dbHelper;
        CheckBox checkBox;

         ViewHolder(@NonNull View itemView) {

             //menginisialisasi view untuk diguanakn pada recycler view
            super(itemView);
            dailyPlan = itemView.findViewById(R.id.daily_plan_name);
            startTime = itemView.findViewById(R.id.daily_plan_time);
            endTime = itemView.findViewById(R.id.daily_end_time);
            checkBox = itemView.findViewById(R.id.check_box);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            dbHelper=new DBHelper(context);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DailyPlaner list = dailyArrayList.get(getAdapterPosition());
                    int id = list.getId();
                    Toast.makeText(context.getApplicationContext(), "Entered: "+id, Toast.LENGTH_SHORT).show();
                    dbHelper.deleteDaily(id);
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    DailyPlaner list = dailyArrayList.get(getAdapterPosition());
                    if ( isChecked )
                    {
                        int id = list.getId();
                        Toast.makeText(context.getApplicationContext(), "ID" +id, Toast.LENGTH_SHORT).show();
                        dbHelper.setDone(id);
                    }else{
                        int id = list.getId();
                        Toast.makeText(context.getApplicationContext(), "ID" +id, Toast.LENGTH_SHORT).show();
                        dbHelper.setUnDone(id);
                    }


                }
            });

        }


    }


}
