package com.artexceptionals.youreuro;

import android.content.Context;


import com.artexceptionals.youreuro.database.CashRecordDatabase;
import com.artexceptionals.youreuro.model.CashRecord;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticManager {
    private static StatisticManager instance;
    private final Context context;
    private final CashRecordDatabase cashRecordDatabase;


    public StatisticManager(Context context, CashRecordDatabase cashRecordDatabase) {
        this.context = context;
        this.cashRecordDatabase = cashRecordDatabase;
    }

    public static StatisticManager getInstance(Context context, CashRecordDatabase cashRecordDatabase) {
        if (instance == null) {
            instance = new StatisticManager(context, cashRecordDatabase);
        }
        return instance;
    }

    public PieData setupPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        List<CashRecord> cashRecords = cashRecordDatabase.cashRecordDao().getAll(); // get all cashrecord
        Map<String, Float> categoryAmount = new HashMap<>(); //we just need category and amt
        for (CashRecord cashRecord : cashRecords) {
            String categoryName = cashRecord.getCategory().getCatagoryName();
            if (!categoryAmount.keySet().contains(categoryName)) {
                categoryAmount.put(categoryName, Math.abs(cashRecord.getAmount()));
            } else {
                float oldvalue = categoryAmount.get(categoryName);
                categoryAmount.remove(categoryName);
                categoryAmount.put(categoryName, oldvalue + Math.abs(cashRecord.getAmount()));
            }
        }

        for (String key : categoryAmount.keySet()) {
            pieEntries.add(new PieEntry(categoryAmount.get(key), key));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "#MoneyControl");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData data = new PieData(dataSet);

        return data;
    }


    public BarData setupBarChart() {
        List<BarEntry> barEntries = new ArrayList<>();
        List<CashRecord> cashRecords = cashRecordDatabase.cashRecordDao().getAll();
        Map<String, Float> categoryAmount = new HashMap<>();
        for (CashRecord cashRecord : cashRecords) {
            String categoryName = cashRecord.getCategory().getCatagoryName();
            if (!categoryAmount.keySet().contains(categoryName)) {
                categoryAmount.put(categoryName, cashRecord.getAmount());
            } else {
                float oldvalue = categoryAmount.get(categoryName);
                categoryAmount.remove(categoryName);
                categoryAmount.put(categoryName, oldvalue + cashRecord.getAmount());
            }
        }

        int i = 0;
        for (String key : categoryAmount.keySet()) {
            barEntries.add(new BarEntry(i,categoryAmount.get(key)));
            i++;
        }
        BarDataSet barSet = new BarDataSet(barEntries, "#MoneyControl");
        barSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(barSet);
        return data;
    }

    public LimitLine getBarLimit(){
        int maxCapacity = 20;
        LimitLine ll = new LimitLine(maxCapacity, "Max Limit");
        return ll;
    }

    public LimitLine getBarLimit2(){
        int maxCapacity = -20;
        LimitLine ll = new LimitLine(maxCapacity, "Min Limit");
        return ll;
    }

}


