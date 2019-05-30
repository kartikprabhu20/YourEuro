package com.artexceptionals.youreuro;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.artexceptionals.youreuro.adapter.CashRecordAdapter;
import com.artexceptionals.youreuro.database.CashRecordDatabase;
import com.artexceptionals.youreuro.database.CategoryDatabase;
import com.artexceptionals.youreuro.model.CashRecord;
import com.artexceptionals.youreuro.model.CashRecordFilter;
import com.artexceptionals.youreuro.model.Category;

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

    //For deleting a cashRecord from both database and adapter view
    public synchronized void deleteCashRecord(CashRecord cashRecord){
        cashRecordDatabase.cashRecordDao().delete(cashRecord);
        cashRecordAdapter.deleteCashRecord(cashRecord);
    }

    public synchronized void updateAllRecords() {
        List<CashRecord> cashRecords = cashRecordDatabase.cashRecordDao().getAll();
        cashRecordAdapter.removeAllCashRecords();
        cashRecordAdapter.addCashRecords(cashRecords);
    }

    public CashRecordAdapter getCashRecordAdapter() {
        return cashRecordAdapter;
    }

    public List<Category> getAllCategories() {
        return categoryDatabase.categoryDao().getAll();
    }

    public void clearCacheCashRecords() {
        cashRecordAdapter.removeAllCashRecords();
    }

    public void loadCashRecords(CashRecordFilter cashRecordFilter) {

        Category category = cashRecordFilter.getCategory();
        SimpleSQLiteQuery simpleSQLiteQuery = new SimpleSQLiteQuery("SELECT * FROM cashrecord WHERE uid NOT NULL "+
                (cashRecordFilter.isAmountRangeFilter()?"AND amount BETWEEN '"+cashRecordFilter.getStartAmount()+"' AND '"+cashRecordFilter.getEndAmount()+"' ":"")+
                (cashRecordFilter.isDateRangeFilter()?"AND timeStamp BETWEEN '"+cashRecordFilter.getStartTimeStamp()+"' AND '"+cashRecordFilter.getEndTimeStamp()+"' ":"")+
                (cashRecordFilter.isCategoryFilter()?"AND categoryID = '"+category.getCategoryID()+"' AND catagoryName = '"+category.getCatagoryName()+"' AND imageID = '"+category.getImageID()+"' ":"")+
                (cashRecordFilter.isPaymentFilter()?"AND paymenttype = '"+ cashRecordFilter.getPaymentType()+"'":""));

        List<CashRecord> cashRecords = cashRecordDatabase.cashRecordDao().getCashRecords(simpleSQLiteQuery);

        cashRecordAdapter.addCashRecords(cashRecords);
    }
}
