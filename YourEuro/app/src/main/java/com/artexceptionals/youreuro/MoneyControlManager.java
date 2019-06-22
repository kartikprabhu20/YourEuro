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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MoneyControlManager {
    private static MoneyControlManager instance;
    private static Context mContext;
    private final StatisticManager statisticsManager;
    private final RecurringManager recurringManager;
    private CashRecordAdapter cashRecordAdapter;
    private BalanceAdapter balanceAdapter;
    private CashRecordDatabase cashRecordDatabase;
    private CategoryDatabase categoryDatabase;
    private CustomSharedPreferences sharedPreference;
    private StatisticsListener statisticsListener;

    public MoneyControlManager(CashRecordAdapter cashRecordAdapter, BalanceAdapter balanceAdapter, CashRecordDatabase cashRecordDatabase,
                               CategoryDatabase categoryDatabase, CustomSharedPreferences customSharedPreferences,RecurringManager recurringManager,
                               StatisticManager statisticManager) {
        this.cashRecordAdapter = cashRecordAdapter;
        this.balanceAdapter = balanceAdapter;
        this.cashRecordDatabase = cashRecordDatabase;
        this.categoryDatabase = categoryDatabase;
        this.statisticsManager = statisticManager;
        this.recurringManager = recurringManager;
        this.sharedPreference = customSharedPreferences;

    }

    public static MoneyControlManager getInstance(Context context) {
        mContext = context;
        CashRecordDatabase cashRecordDatabase = CashRecordDatabase.getCashRecordDatabase(context);
        if (instance == null) {
            instance = new MoneyControlManager(new CashRecordAdapter(context),
                    new BalanceAdapter(context),
                    cashRecordDatabase,
                    CategoryDatabase.getCategoryDatabase(context),
                    CustomSharedPreferences.getInstance(context),
                    RecurringManager.getInstance(context),
                    StatisticManager.getInstance(context, cashRecordDatabase));
        }

        return instance;
    }

    public synchronized void addCashRecord(CashRecord cashRecord) {
        long uid = cashRecordDatabase.cashRecordDao().insert(cashRecord);
        cashRecord.setUid(uid);
        cashRecordAdapter.addCashRecord(cashRecord);
        balanceAdapter.updateAccount(new Account(cashRecord.getCurrency(),cashRecord.getAmount()));

        if (cashRecord.isRecurringTransaction())
            recurringManager.initialiseNextEvent(cashRecord, listener);

        statisticsListener.listen();
    }

    //For deleting a cashRecord from both database and adapter view
    public synchronized void deleteCashRecord(CashRecord cashRecord){
        cashRecordDatabase.cashRecordDao().delete(cashRecord);
        cashRecordAdapter.deleteCashRecord(cashRecord);
        balanceAdapter.deleteAccount(new Account(cashRecord.getCurrency(),cashRecord.getAmount()));

        if (cashRecord.isRecurringTransaction())
            recurringManager.cancelPendingIntents(cashRecord);

        statisticsListener.listen();
    }

    public synchronized void updateAllRecords() {
        List<CashRecord> cashRecords = cashRecordDatabase.cashRecordDao().getAll();
        cashRecordAdapter.removeAllCashRecords();
        cashRecordAdapter.addCashRecords(cashRecords);

        updateAllAccounts();

        statisticsListener.listen();
    }

    public CashRecordAdapter getCashRecordAdapter() {
        return cashRecordAdapter;
    }

    public List<Category> getAllCategories() {
        return categoryDatabase.categoryDao().getAll();
    }

    public void addCategory(Category category) {
         categoryDatabase.categoryDao().insert(category);
    }

    public void removeCategory(Category category) {
        categoryDatabase.categoryDao().delete(category);
    }

    public void updateAllCategories(List<Category> categories) {
        categoryDatabase.categoryDao().updateAll(categories);
    }

    public void clearCacheCashRecords() {
        cashRecordAdapter.removeAllCashRecords();
    }

    public void loadCashRecords(CashRecordFilter cashRecordFilter) {
        List<CashRecord> cashRecords = cashRecordDatabase.cashRecordDao().getCashRecords(statisticsManager.getQuery(cashRecordFilter));
        cashRecordAdapter.addCashRecords(cashRecords);
    }

    public CustomSharedPreferences getSharedPreference() {
        return sharedPreference;
    }

    public BalanceAdapter getBalanceAdapter() {
        return balanceAdapter;
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
        statisticsListener.listen();
    }

    RecurringReceiver.ReceiverListener listener = new RecurringReceiver.ReceiverListener() {
        @Override
        public void listen(long cashRecordID) {
            CashRecord cashRecord = cashRecordDatabase.cashRecordDao().getCashRecord(cashRecordID);
            cashRecord.setRecurred(true);
            cashRecord.setRecurringTransaction(false);
            cashRecordDatabase.cashRecordDao().update(cashRecord);

            CashRecord newCashRecord = new CashRecord(cashRecord);
            newCashRecord.setTimeStamp(new Date().getTime());
            newCashRecord.setRecurringTransaction(true);
            newCashRecord.setRecurred(false);

            addCashRecord(newCashRecord);
        }
    };

    public StatisticManager getStatisticsManager() {
        return statisticsManager;
    }

    public void setStatisticsListener(StatisticsListener listener) {
        this.statisticsListener = listener;
    }

    public interface StatisticsListener {
        void listen();
    }
}
