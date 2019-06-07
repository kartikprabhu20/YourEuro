package com.artexceptionals.youreuro;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.content.Context;

import com.artexceptionals.youreuro.adapter.BalanceAdapter;
import com.artexceptionals.youreuro.adapter.CashRecordAdapter;
import com.artexceptionals.youreuro.database.CashRecordDatabase;
import com.artexceptionals.youreuro.database.CategoryDatabase;
import com.artexceptionals.youreuro.model.Account;
import com.artexceptionals.youreuro.model.CashRecord;
import com.artexceptionals.youreuro.model.CashRecordFilter;
import com.artexceptionals.youreuro.model.Category;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MoneyControlManager {
    private static MoneyControlManager instance;
    private final CustomSharedPreferences sharedPreference;
    private CashRecordAdapter cashRecordAdapter;
    private BalanceAdapter balanceAdapter;
    private CashRecordDatabase cashRecordDatabase;
    private CategoryDatabase categoryDatabase;

    public MoneyControlManager(CashRecordAdapter cashRecordAdapter, BalanceAdapter balanceAdapter, CashRecordDatabase cashRecordDatabase,
                               CategoryDatabase categoryDatabase, CustomSharedPreferences customSharedPreferences) {
        this.cashRecordAdapter = cashRecordAdapter;
        this.balanceAdapter = balanceAdapter;
        this.cashRecordDatabase = cashRecordDatabase;
        this.categoryDatabase = categoryDatabase;
        this.sharedPreference = customSharedPreferences;

    }

    public static MoneyControlManager getInstance(Context context) {
        if (instance == null) {
            instance = new MoneyControlManager(new CashRecordAdapter(context),
                    new BalanceAdapter(context),
                    CashRecordDatabase.getCashRecordDatabase(context),
                    CategoryDatabase.getCategoryDatabase(context),
                    CustomSharedPreferences.getInstance(context));
        }

        return instance;
    }

    public synchronized void addCashRecord(CashRecord cashRecord) {
        long uid = cashRecordDatabase.cashRecordDao().insert(cashRecord);
        cashRecord.setUid(uid);
        cashRecordAdapter.addCashRecord(cashRecord);
        balanceAdapter.updateAccount(new Account(cashRecord.getCurrency(),cashRecord.getAmount()));

    }

    //For deleting a cashRecord from both database and adapter view
    public synchronized void deleteCashRecord(CashRecord cashRecord){
        cashRecordDatabase.cashRecordDao().delete(cashRecord);
        cashRecordAdapter.deleteCashRecord(cashRecord);
        balanceAdapter.deleteAccount(new Account(cashRecord.getCurrency(),cashRecord.getAmount()));
    }

    public synchronized void updateAllRecords() {
        List<CashRecord> cashRecords = cashRecordDatabase.cashRecordDao().getAll();
        cashRecordAdapter.removeAllCashRecords();
        cashRecordAdapter.addCashRecords(cashRecords);

        updateAllAccounts();
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

        List<Category> filterCategories = cashRecordFilter.getCategories();

        StringBuilder categories  = new StringBuilder();
        Iterator<Category> iter = filterCategories.iterator();
        while(iter.hasNext())
        {
            categories.append(iter.next().getCategoryID());
            if(iter.hasNext()){
                categories.append(",");
            }
        }

        SimpleSQLiteQuery simpleSQLiteQuery = new SimpleSQLiteQuery("SELECT * FROM cashrecord WHERE uid NOT NULL "+
                (cashRecordFilter.isAmountRangeFilter()?"AND amount BETWEEN "+cashRecordFilter.getStartAmount()+" AND "+cashRecordFilter.getEndAmount()+" ":"")+
                (cashRecordFilter.isDateRangeFilter()?"AND timeStamp BETWEEN "+cashRecordFilter.getStartTimeStamp()+" AND "+cashRecordFilter.getEndTimeStamp()+" ":"")+
                (cashRecordFilter.isCategoryFilter()?"AND categoryID IN ("+categories.toString()+") ":"")+
                (cashRecordFilter.isPaymentFilter()?"AND paymenttype = '"+ cashRecordFilter.getPaymentType()+"'":""));

        List<CashRecord> cashRecords = cashRecordDatabase.cashRecordDao().getCashRecords(simpleSQLiteQuery);

        cashRecordAdapter.addCashRecords(cashRecords);
    }

    public CustomSharedPreferences getSharedPreference() {
        return sharedPreference;
    }

    public BalanceAdapter getBalanceAdapter() {
        return balanceAdapter;
    }

    public synchronized void addAccount(Account account) {
        balanceAdapter.addAccount(account);
    }

    public synchronized void updateAllAccounts() {
        List<CashRecord> cashRecords = cashRecordDatabase.cashRecordDao().getAll();

        List<Account> accountList = new ArrayList<>();
        for (CashRecord cashRecord: cashRecords){
            Account account = new Account(cashRecord.getCurrency(), cashRecord.getAmount());

            if (accountList.contains(account)){
                accountList.get(accountList.indexOf(account)).setBalance(accountList.get(accountList.indexOf(account)).getBalance() + account.getBalance());
            }else {
                accountList.add(account);
            }
        }

        balanceAdapter.updateAccounts(accountList);
    }
}
