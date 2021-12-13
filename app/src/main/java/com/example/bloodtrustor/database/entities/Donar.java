package com.example.bloodtrustor.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "donar")
public class Donar {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "blood_group")
    public String bloodGroup;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "age")
    public int age;

    @ColumnInfo(name = "email")
    public String email;
}
