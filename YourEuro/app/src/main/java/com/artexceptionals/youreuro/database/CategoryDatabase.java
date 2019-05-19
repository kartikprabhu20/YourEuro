package com.artexceptionals.youreuro.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.artexceptionals.youreuro.R;
import com.artexceptionals.youreuro.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {Category.class}, version = 1)
public abstract class CategoryDatabase extends RoomDatabase {

    private static CategoryDatabase INSTANCE;

    public abstract CategoryDao categoryDao();

    public static CategoryDatabase getCategoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), CategoryDatabase.class, "category-database")
                            .allowMainThreadQueries()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            List<Category> categoryList = new ArrayList<>();
                                            categoryList.add(new Category("Shopping", R.drawable.youreuro_square));
                                            categoryList.add(new Category("Transportation", R.drawable.youreuro_square));
                                            categoryList.add(new Category("Education", R.drawable.youreuro_square));
                                            categoryList.add(new Category("Entertainment", R.drawable.youreuro_square));

                                            getCategoryDatabase(context).categoryDao().insertAll(categoryList);
                                        }
                                    });
                                }
                            })
                            .build();
        }
            return INSTANCE;

    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
