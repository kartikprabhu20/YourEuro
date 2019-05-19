package com.artexceptionals.youreuro.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.artexceptionals.youreuro.model.CashRecord;

import java.util.List;


@Dao
public interface CashRecordDao {

    @Query("SELECT * FROM cashrecord")
    List<CashRecord> getAll();

    @Insert
    void insertAll(CashRecord... cashRecords);

    @Delete
    void delete(CashRecord cashRecord);

    @Update
    void update(CashRecord cashRecord);
}
