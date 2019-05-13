package com.artexceptionals.youreuro;

import android.support.v7.widget.RecyclerView;

import com.artexceptionals.youreuro.adapter.CashRecordAdapter;
import com.artexceptionals.youreuro.model.CashRecord;

public class MoneyControlManager {
    private static MoneyControlManager instance;
    private CashRecordAdapter cashRecordAdapter;

    public MoneyControlManager(CashRecordAdapter cashRecordAdapter) {
        this.cashRecordAdapter = cashRecordAdapter;
    }

    public static MoneyControlManager getInstance() {
        if (instance == null)
            instance = new MoneyControlManager(new CashRecordAdapter());

        return instance;
    }


    public void addCashRecord(CashRecord cashRecord) {
        cashRecordAdapter.addCashRecord(cashRecord);
    }

    public RecyclerView.Adapter getCashRecordAdapter() {
        return cashRecordAdapter;
    }

}
