package com.example.ui1.SelfAssessment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ui1.R;

import java.util.ArrayList;

public class SelfDataAdapter extends RecyclerView.Adapter<SelfDataAdapter.MyViewHolder> {

    Context context;
    ArrayList<SelfData> list;

    public SelfDataAdapter(Context context, ArrayList<SelfData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.self_data,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SelfData selfData= list.get(position);
        holder.date.setText(selfData.getDate());
        holder.status.setText(selfData.getStatus());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView  status,date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.txtDate);
            status = itemView.findViewById(R.id.txtStatus);


        }
    }
}