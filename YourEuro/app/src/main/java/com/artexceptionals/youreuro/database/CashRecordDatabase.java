package com.artexceptionals.youreuro.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.artexceptionals.youreuro.model.CashRecord;


@Database(entities = {CashRecord.class}, version = 1)
public abstract class CashRecordDatabase  extends RoomDatabase {

    private static CashRecordDatabase INSTANCE;

    public abstract CashRecordDao cashRecordDao();

    public static CashRecordDatabase getCashRecordDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), CashRecordDatabase.class, "cashrecord-database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}

