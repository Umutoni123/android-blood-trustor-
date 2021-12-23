package com.example.bloodtrustor.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bloodtrustor.database.entities.Donar;
import com.example.bloodtrustor.database.entities.User;

import java.util.List;

@Dao
public interface DonarDao {
    @Query("SELECT * FROM donar")
    List<Donar> getAll();

    @Query("SELECT * FROM donar WHERE id IN (:donarIds)")
    List<Donar> getDonarsById(String[] donarIds);

    @Query("SELECT * FROM donar WHERE id=:donarId")
    User getDonarsById(int donarId);

    @Query("UPDATE donar SET name=:name, email=:phone, address=:address, blood_group=:blood, age=:age WHERE id=:id")
    void update(String name, String phone, String address, String blood, int age, int id);

    @Insert
    void insertAll(Donar... donars);

    @Query("DELETE FROM donar WHERE id=:id")
    void deleteDonar(int id);
}
