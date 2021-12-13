package com.example.bloodtrustor.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.bloodtrustor.database.dao.DonarDao;
import com.example.bloodtrustor.database.dao.UserDao;
import com.example.bloodtrustor.database.entities.Donar;
import com.example.bloodtrustor.database.entities.User;

@Database(entities = {User.class, Donar.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract DonarDao donarDao();
}
