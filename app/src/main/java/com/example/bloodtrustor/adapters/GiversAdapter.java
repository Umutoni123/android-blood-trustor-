package com.example.bloodtrustor.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodtrustor.EditDonarActivity;
import com.example.bloodtrustor.Global;
import com.example.bloodtrustor.R;
import com.example.bloodtrustor.database.entities.Donar;

import java.util.Collections;
import java.util.List;

public class GiversAdapter  extends RecyclerView.Adapter<GiversAdapter.ViewHolder> {
    List<Donar> donars;
    public static AppCompatActivity ACTIVITY;

    public GiversAdapter(List<Donar> donars, AppCompatActivity activity) {
        Collections.reverse(donars);
        this.donars = donars;
        GiversAdapter.ACTIVITY = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_blood_givers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(donars.get(position));
    }

    @Override
    public int getItemCount() {
        return donars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPhone = itemView.findViewById(R.id.txtPhone);
        }
        public void bind(final Donar donar){
            txtName.setText(donar.name);
            txtPhone.setText(donar.email);
            itemView.setOnClickListener(view -> {
                Global.user = donar;
                Intent intent = new Intent(itemView.getContext(), EditDonarActivity.class);
                GiversAdapter.ACTIVITY.startActivity(intent);
                GiversAdapter.ACTIVITY.finish();
            });
        }
    }
}

