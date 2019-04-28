package com.artexceptionals.youreuro;

import com.artexceptionals.youreuro.adapter.CashRecordAdapter;
import com.artexceptionals.youreuro.model.CashRecord;

public class MoneyControlManager {
    private static MoneyControlManager instance;
    private CashRecordAdapter cashRecordAdapter;

    public MoneyControlManager(CashRecordAdapter cashRecordAdapter) {
        this.cashRecordAdapter = cashRecordAdapter;
    }

    public static MoneyControlManager getInstance(CashRecordAdapter cashRecordAdapter) {
        if (instance == null)
            instance = new MoneyControlManager(cashRecordAdapter);

        return instance;
    }


    public void addCashRecord(CashRecord cashRecord) {
        cashRecordAdapter.addCashRecord(cashRecord);
    }
}
