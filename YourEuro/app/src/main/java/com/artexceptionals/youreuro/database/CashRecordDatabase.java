package com.artexceptionals.youreuro.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.artexceptionals.youreuro.model.CashRecord;


@Database(entities = {CashRecord.class}, version = 2)
public abstract class CashRecordDatabase  extends RoomDatabase {

    private static CashRecordDatabase INSTANCE;

    public abstract CashRecordDao cashRecordDao();

    public static CashRecordDatabase getCashRecordDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), CashRecordDatabase.class, "cashrecord-database")
                            .allowMainThreadQueries()
                            .addMigrations(MIGRATION_1_2)
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };
}

