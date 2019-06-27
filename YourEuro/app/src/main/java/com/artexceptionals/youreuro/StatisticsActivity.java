package com.artexceptionals.youreuro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artexceptionals.youreuro.model.CashRecordFilter;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.artexceptionals.youreuro.model.CashRecordFilter.FILTER_REQUEST_CODE_BARCHART;
import static com.artexceptionals.youreuro.model.CashRecordFilter.FILTER_REQUEST_CODE_PIECHART;

public class StatisticsActivity extends AppCompatActivity implements View.OnClickListener{


    @BindView(R.id.pieChart)
    PieChart pieChart;

    @BindView(R.id.combinedChart)
    CombinedChart combinedChart;

    @BindView(R.id.piechart_filter)
    ImageView pieChartFilter;

    @BindView(R.id.barchart_filter)
    ImageView barChartFilter;

    @BindView(R.id.piechart_refresh)
    ImageView pieChartRefresh;

    @BindView(R.id.barchart_refresh)
    ImageView barChartRefresh;

    @BindView(R.id.statistic_cardview)
    CardView statisticsCardView;

    @BindView(R.id.account_cardview)
    CardView balanceCardView;

    @BindView(R.id.recent_cardview)
    CardView recentsCardView;

    @BindView(R.id.statistic_title)
    TextView statisticsTitle;


    private MoneyControlManager moneyControlManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        moneyControlManager =  MoneyControlManager.getInstance(YourEuroApp.getAppContext());
        statisticsCardView.setVisibility(!(moneyControlManager.getCashRecordAdapter().getItemCount() > 0) ? View.GONE : View.VISIBLE);
        recentsCardView.setVisibility(View.GONE);
        balanceCardView.setVisibility(View.GONE);
        statisticsTitle.setVisibility(View.GONE);

        //Statistics
        barChartFilter.setOnClickListener(this);
        pieChartFilter.setOnClickListener(this);
        barChartRefresh.setOnClickListener(this);
        pieChartRefresh.setOnClickListener(this);

        filterResults(new CashRecordFilter(), true);
        filterResults(new CashRecordFilter(), false);

        setChartHeight();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.barchart_filter:
                startActivityForResult(new Intent(this, FilterActivity.class), CashRecordFilter.FILTER_REQUEST_CODE_BARCHART);
                break;
            case  R.id.piechart_filter:
                startActivityForResult(new Intent(this, FilterActivity.class), CashRecordFilter.FILTER_REQUEST_CODE_PIECHART);
                break;
            case  R.id.piechart_refresh:
                filterResults(new CashRecordFilter(), true);
                break;
            case  R.id.barchart_refresh:
                filterResults(new CashRecordFilter(), false);
                break;
        }
    }

    private void filterResults(CashRecordFilter cashRecordFilter, boolean isPieChart) {
        if (isPieChart){
            //Piechart:
            pieChart.setData(moneyControlManager.getStatisticsManager().setupPieChart(cashRecordFilter));
            pieChart.invalidate();
            pieChart.setDrawEntryLabels(false);
            pieChart.getDescription().setEnabled(false);
            pieChart.getLegend().setWordWrapEnabled(true);
            pieChart.setCenterText((cashRecordFilter.isCategoryFilter() && cashRecordFilter.getCategories().size() == 1)? cashRecordFilter.getCategories().get(0).getCatagoryName():"Categories");
            pieChart.setCenterTextSize(14f);
        }else{
            combinedChart.setData(moneyControlManager.getStatisticsManager().setupCombinedChart(cashRecordFilter, moneyControlManager.getAllCategories()));
            Legend legend = combinedChart.getLegend();
            legend.resetCustom();
            combinedChart.notifyDataSetChanged();
            legend.setCustom(moneyControlManager.getStatisticsManager().getLegends(cashRecordFilter));
            legend.setWordWrapEnabled(true);

            if (cashRecordFilter.isCategoryFilter() && cashRecordFilter.getCategories().size() == 1){
                combinedChart.getAxisLeft().removeAllLimitLines();
                combinedChart.getAxisLeft().addLimitLine(moneyControlManager.getStatisticsManager().getThresholdLimit(cashRecordFilter.getCategories().get(0)));
            }else {
                combinedChart.getAxisLeft().removeAllLimitLines();
            }
            combinedChart.getXAxis().setEnabled(false);
            combinedChart.getDescription().setEnabled(false);
            combinedChart.fitScreen();
            combinedChart.notifyDataSetChanged();
            combinedChart.invalidate();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle.containsKey(CashRecordFilter.FILTER)) {
                if (requestCode == FILTER_REQUEST_CODE_PIECHART) {
                    filterResults(bundle.getParcelable(CashRecordFilter.FILTER), true);
                } else if (requestCode == FILTER_REQUEST_CODE_BARCHART) {
                    filterResults(bundle.getParcelable(CashRecordFilter.FILTER), false);
                }
            }
        }
    }

    private void setChartHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ViewGroup.LayoutParams params = pieChart.getLayoutParams();
        params.height = displayMetrics.widthPixels - 100;
        pieChart.setLayoutParams(params);
        params = combinedChart.getLayoutParams();
        params.height = displayMetrics.widthPixels - 100;
        pieChart.setLayoutParams(params);
    }
}
