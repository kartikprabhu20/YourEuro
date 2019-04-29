package com.artexceptionals.youreuro;

import com.artexceptionals.youreuro.adapter.AccountAdapter;
import com.artexceptionals.youreuro.adapter.CashRecordAdapter;
import com.artexceptionals.youreuro.model.Account;
import com.artexceptionals.youreuro.model.CashRecord;

public class MoneyControlManager {
    private static MoneyControlManager instance;
    private CashRecordAdapter cashRecordAdapter;
    private AccountAdapter accountAdapter;

    public MoneyControlManager(CashRecordAdapter cashRecordAdapter, AccountAdapter accountAdapter) {
        this.cashRecordAdapter = cashRecordAdapter;
        this.accountAdapter = accountAdapter;
    }

    public static MoneyControlManager getInstance(CashRecordAdapter cashRecordAdapter, AccountAdapter accountAdapter) {
        if (instance == null)
            instance = new MoneyControlManager(cashRecordAdapter, accountAdapter);

        return instance;
    }


    public void addCashRecord(CashRecord cashRecord) {
        cashRecordAdapter.addCashRecord(cashRecord);
    }

    public void addAccount(Account account) {
        accountAdapter.addAccount(account);
    }
}
