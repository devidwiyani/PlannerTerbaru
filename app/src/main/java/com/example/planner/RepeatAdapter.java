package com.example.planner;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RepeatAdapter extends RecyclerView.Adapter<RepeatAdapter.ViewHolder>{

    ArrayList<Repeat> repeatArrayList;
    private Context context;
    DBHelper dbHelper;

    public RepeatAdapter(ArrayList<Repeat> repeatArrayList, Context context) {
        this.repeatArrayList = repeatArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repeat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Repeat list = repeatArrayList.get(position);
        holder.repeatTitle.setText(list.getTitle());
        holder.repeatDeskripsi.setText(list.getDeskripsi());

    }

    @Override
    public int getItemCount() {
        return repeatArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView repeatTitle, repeatDeskripsi;
        ImageButton btnRepeat;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            repeatTitle = itemView.findViewById(R.id.repeat_title);
            repeatDeskripsi = itemView.findViewById(R.id.repeat_deskripsi);
            btnRepeat =  itemView.findViewById(R.id.btn_repeat);
            dbHelper=new DBHelper(context);
            btnRepeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Repeat list = repeatArrayList.get(getAdapterPosition());
                    int id = list.getId();
                    int userId = list.getUserId();
                    Toast.makeText(context.getApplicationContext(), "ID SAVE :" +id, Toast.LENGTH_SHORT).show();
                    int repeat = 1;
                    Intent intent = new Intent(context.getApplicationContext(), DailyActivity.class);
                    ContentValues values = new ContentValues();
                    intent.putExtra("status_repeat", repeat);
                    intent.putExtra("save_id", id);
                    intent.putExtra("user_id", userId);
                    context.startActivity(intent);

                }
            });


        }

    }
}
