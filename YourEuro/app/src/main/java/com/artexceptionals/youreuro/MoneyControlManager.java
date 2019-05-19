package com.artexceptionals.youreuro;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.artexceptionals.youreuro.adapter.CashRecordAdapter;
import com.artexceptionals.youreuro.database.CashRecordDatabase;
import com.artexceptionals.youreuro.database.CategoryDatabase;
import com.artexceptionals.youreuro.model.CashRecord;
import com.artexceptionals.youreuro.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MoneyControlManager {
    private static MoneyControlManager instance;
    private CashRecordAdapter cashRecordAdapter;
    private CashRecordDatabase cashRecordDatabase;
    private CategoryDatabase categoryDatabase;

    public MoneyControlManager(CashRecordAdapter cashRecordAdapter, CashRecordDatabase cashRecordDatabase, CategoryDatabase categoryDatabase) {
        this.cashRecordAdapter = cashRecordAdapter;
        this.cashRecordDatabase = cashRecordDatabase;
        this.categoryDatabase = categoryDatabase;

    }

    public static MoneyControlManager getInstance(Context context) {
        if (instance == null) {
            instance = new MoneyControlManager(new CashRecordAdapter(context),
                    CashRecordDatabase.getCashRecordDatabase(context),
                    CategoryDatabase.getCategoryDatabase(context));
        }

        return instance;
    }

    public synchronized void addCashRecord(CashRecord cashRecord) {
        cashRecordAdapter.addCashRecord(cashRecord);
        cashRecordDatabase.cashRecordDao().insertAll(cashRecord);
    }

    public synchronized void updateAllRecords() {
        List<CashRecord> cashRecords = cashRecordDatabase.cashRecordDao().getAll();
        cashRecordAdapter.removeAllCashRecords();
        cashRecordAdapter.addCashRecords(cashRecords);
    }

    public RecyclerView.Adapter getCashRecordAdapter() {
        return cashRecordAdapter;
    }

    public List<Category> getAllCategories() {
        return categoryDatabase.categoryDao().getAll();
    }
}
