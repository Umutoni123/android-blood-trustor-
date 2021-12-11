package com.example.bloodtrustor.database;

import android.content.Context;

import androidx.room.Room;

public class Connection {
    public static AppDatabase database (Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, "blood-trustor").fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }
}
