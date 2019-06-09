package com.artexceptionals.youreuro.database;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import com.artexceptionals.youreuro.model.CashRecord;
import com.artexceptionals.youreuro.model.Category;

import java.util.List;


@Dao
public interface CashRecordDao {

    @Query("SELECT * FROM cashrecord ORDER BY timeStamp DESC")
    List<CashRecord> getAll();

    @Insert
    long insert(CashRecord cashRecords);

    @Delete
    void delete(CashRecord cashRecord);

    @Update
    void update(CashRecord cashRecord);

    @RawQuery
    List<CashRecord> getCashRecords(SupportSQLiteQuery query);


    @Query("SELECT * FROM cashrecord WHERE uid = :uid")
    CashRecord getCashRecord(long uid);
}
