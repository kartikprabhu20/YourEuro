package com.artexceptionals.youreuro;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.content.Context;
import android.graphics.Color;


import com.artexceptionals.youreuro.database.CashRecordDatabase;
import com.artexceptionals.youreuro.model.CashRecord;
import com.artexceptionals.youreuro.model.CashRecordFilter;
import com.artexceptionals.youreuro.model.Category;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.data.Entry;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StatisticManager {
    private static StatisticManager instance;
    private final Context context;
    private final CashRecordDatabase cashRecordDatabase;

    int [] color={ ColorTemplate.rgb("#ea5b19"),ColorTemplate.rgb("#eaca18"),ColorTemplate.rgb("#086000"),ColorTemplate.rgb("#02258c"),
            ColorTemplate.rgb("#8c0142"),ColorTemplate.rgb("#8e8c04"),ColorTemplate.rgb("#8401bc"), ColorTemplate.rgb("#ffb744"),
            ColorTemplate.rgb("#44ffa1"),ColorTemplate.rgb("#b500b5"),ColorTemplate.rgb("#936a76"), ColorTemplate.rgb("#fcea85"),
            ColorTemplate.rgb("#91fff7"),ColorTemplate.rgb("#e191ff"),ColorTemplate.rgb("#fc5353"),ColorTemplate.rgb("#00ffdd")};

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

    public SimpleSQLiteQuery getQuery(CashRecordFilter cashRecordFilter) {
        List<Category> filterCategories = cashRecordFilter.getCategories();

        StringBuilder categories = new StringBuilder();
        Iterator<Category> iter = filterCategories.iterator();
        while (iter.hasNext()) {
            categories.append(iter.next().getCategoryID());
            if (iter.hasNext()) {
                categories.append(",");
            }
        }

        SimpleSQLiteQuery simpleSQLiteQuery = new SimpleSQLiteQuery("SELECT * FROM cashrecord WHERE uid NOT NULL " +
                (cashRecordFilter.isAmountRangeFilter() ? "AND amount BETWEEN " + cashRecordFilter.getStartAmount() + " AND " + cashRecordFilter.getEndAmount() + " " : "") +
//                (cashRecordFilter.isAmountRangeFilter()?"AND amount BETWEEN "+cashRecordFilter.getStartAmount() * -1+" AND "+cashRecordFilter.getEndAmount() * -1+" ":"")+
                (cashRecordFilter.isDateRangeFilter() ? "AND timeStamp BETWEEN " + cashRecordFilter.getStartTimeStamp() + " AND " + cashRecordFilter.getEndTimeStamp() + " " : "") +
                (cashRecordFilter.isRecurryingFilter() ? "AND recurringTransaction = " + (cashRecordFilter.isRecurryingFilter() ? 1 : 0) : "") +
                (cashRecordFilter.isCategoryFilter() ? "AND categoryID IN (" + categories.toString() + ") " : "") +
                (cashRecordFilter.isPaymentFilter() ? "AND paymenttype = '" + cashRecordFilter.getPaymentType() + "'" : ""));

        return simpleSQLiteQuery;
    }

    public PieData setupPieChart(CashRecordFilter cashRecordFilter) {
        List<CashRecord> cashRecords = cashRecordDatabase.cashRecordDao().getCashRecords(getQuery(cashRecordFilter));
        return setupPieChart(cashRecords, cashRecordFilter);
    }

    public PieData setupPieChart() {
        return setupPieChart(cashRecordDatabase.cashRecordDao().getAll(), new CashRecordFilter()); // get all cashrecord
    }

    public PieData setupPieChart(List<CashRecord> cashRecords, CashRecordFilter cashRecordFilter) {
        List<PieEntry> pieEntries = new ArrayList<>();

        Map<String, Float> mapAmount = (cashRecordFilter.isCategoryFilter() && cashRecordFilter.getCategories().size() == 1) ?
                getMonthAmount(cashRecords) : getCategoryAmount(cashRecords);

        for (String key : mapAmount.keySet()) {
            pieEntries.add(new PieEntry(mapAmount.get(key), key));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "#YourEuro");
        dataSet.setColors(color);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        return data;
    }

    public BarData setupBarChart() {
        return setupBarChart(cashRecordDatabase.cashRecordDao().getAll(), new CashRecordFilter()); // get all cashrecord
    }

    public BarData setupBarChart(CashRecordFilter cashRecordFilter) {
        List<CashRecord> cashRecords = cashRecordDatabase.cashRecordDao().getCashRecords(getQuery(cashRecordFilter));
        return setupBarChart(cashRecords, cashRecordFilter);
    }

    public BarData setupBarChart(List<CashRecord> cashRecords, CashRecordFilter cashRecordFilter) {
        List<BarEntry> barEntries = new ArrayList<>();
        Map<String, Float> mapAmount = (cashRecordFilter.isCategoryFilter() && cashRecordFilter.getCategories().size() == 1) ?
                getMonthAmount(cashRecords) : getCategoryAmount(cashRecords);

        int i = 0;
        for (String key : mapAmount.keySet()) {
            BarEntry barEntry = new BarEntry(i, mapAmount.get(key));
            barEntries.add(barEntry);
            i++;
        }

        BarDataSet barSet = new BarDataSet(barEntries, "#YourEuro");
        barSet.setColors(color);
        BarData data = new BarData(barSet);
        return data;
    }

    private Map<String, Float> getCategoryAmount(List<CashRecord> cashRecords) {
        Map<String, Float> categoryAmount = new HashMap<>();
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
        return categoryAmount;
    }

    private Map<String, Float> getMonthAmount(List<CashRecord> cashRecords) {
        Map<String, Float> monthAmount = new HashMap<>();
        for (CashRecord cashRecord : cashRecords) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(cashRecord.getTimeStamp());
            String month = getMonth(calendar.get(Calendar.MONTH));
            if (!monthAmount.keySet().contains(month)) {
                monthAmount.put(month, Math.abs(cashRecord.getAmount()));
            } else {
                float oldvalue = monthAmount.get(month);
                monthAmount.remove(month);
                monthAmount.put(month, oldvalue + Math.abs(cashRecord.getAmount()));
            }
        }
        return monthAmount;
    }

    public static String getMonth(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

    public List<LegendEntry> getLegends(CashRecordFilter cashRecordFilter) {
        List<CashRecord> cashRecords = cashRecordDatabase.cashRecordDao().getCashRecords(getQuery(cashRecordFilter));

        Map<String, Float> mapAmount = (cashRecordFilter.isCategoryFilter() && cashRecordFilter.getCategories().size() == 1) ?
                getMonthAmount(cashRecords) : getCategoryAmount(cashRecords);


        List<LegendEntry> legendEntries = new ArrayList<>();
        int i = 0;
        for (String key : mapAmount.keySet()) {
            LegendEntry legendEntry = new LegendEntry(key, Legend.LegendForm.SQUARE, 8f, 8f, null, ColorTemplate.COLORFUL_COLORS[i%5]);
            legendEntries.add(legendEntry);
            i++;
        }
        return legendEntries;
    }

    public CombinedData setupCombinedChart(CashRecordFilter cashRecordFilter, List<Category> allCategories) {
        CombinedData data = new CombinedData();
        data.setData(setupBarChart(cashRecordFilter));
        data.setData(setupLineChart(cashRecordFilter, allCategories));
        return data;
    }

    private LineData setupLineChart(CashRecordFilter cashRecordFilter, List<Category> allCategories) {

        LineData lineData = new LineData();

        ArrayList<Category> filterCategories = new ArrayList<>();
        ArrayList<Entry> entries = new ArrayList<>();
        Map<String, Float> categoryAmount = getCategoryAmount(cashRecordDatabase.cashRecordDao().getCashRecords(getQuery(cashRecordFilter)));

        if (cashRecordFilter.isCategoryFilter()) {
            if (cashRecordFilter.getCategories().size() > 1) {
                filterCategories.addAll(cashRecordFilter.getCategories());
            }else {
                return lineData;
            }
        }else {
            for (Category category: allCategories) {
                if (categoryAmount.keySet().contains(category.getCatagoryName()))
                     filterCategories.add(category);
            }
        }

        int index = 0;
        for (Category category: filterCategories) {
            entries.add(new Entry(index, category.getThreshold()));
            index++;
        }

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.RED);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.RED);
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.RED);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineData.addDataSet(set);

        return lineData;

    }

    public LimitLine getThresholdLimit(Category category){
        return new LimitLine((int)category.getThreshold(), "Threshold - " + category.getCatagoryName());
    }

    public List<Integer> getColors(){
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        return colors;
    }
}


