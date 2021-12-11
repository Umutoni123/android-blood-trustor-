package com.example.bloodtrustor.database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.bloodtrustor.database.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> getUsersById(String[] userIds);

    @Query("SELECT * FROM user WHERE id=:userId")
    User getUsersById(int userId);

    @Query("SELECT * FROM user WHERE email=:email AND password=:password")
    User login(String email, String password);

    @Insert
    void insertAll(User... users);

    @Insert
    void insertOne(User user);

    @Delete
    void delete(User user);
}
