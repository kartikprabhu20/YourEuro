package com.artexceptionals.youreuro.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.artexceptionals.youreuro.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    List<Category> getAll();


    @Insert
    void insertAll(List<Category> categories);

    @Delete
    void delete(Category category);

    @Update
    void update(Category category);
}
