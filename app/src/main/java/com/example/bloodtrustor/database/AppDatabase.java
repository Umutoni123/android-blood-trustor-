package com.example.bloodtrustor.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.bloodtrustor.database.dao.UserDao;
import com.example.bloodtrustor.database.entities.User;

@Database(entities = {User.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
