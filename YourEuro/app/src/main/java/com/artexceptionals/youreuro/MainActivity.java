package com.artexceptionals.youreuro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.artexceptionals.youreuro.model.CashRecordFilter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.artexceptionals.youreuro.adapter.CashRecordAdapter;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.artexceptionals.youreuro.model.CashRecordFilter.FILTER_REQUEST_CODE_BARCHART;
import static com.artexceptionals.youreuro.model.CashRecordFilter.FILTER_REQUEST_CODE_PIECHART;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DrawerLayout drawerlay;
    private ActionBarDrawerToggle abdt;

    @BindView(R.id.recent_history_rv)
    RecyclerView mRecentsRecyclerView;

    @BindView(R.id.balance_rv)
    RecyclerView mBalanceRecyclerView;

    @BindView(R.id.noRecords_tv)
    TextView noRecordsTextView;

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

    private MoneyControlManager moneyControlManager;

    CustomSharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        drawerlay=(DrawerLayout)findViewById(R.id.drawerlay);
        abdt=new ActionBarDrawerToggle(this,drawerlay,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        sharedPreferences = CustomSharedPreferences.getInstance(MainActivity.this);

        drawerlay.addDrawerListener(abdt);
        abdt.syncState();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_View=(NavigationView)findViewById(R.id.nav_view);

        boolean firstStart = sharedPreferences.getBoolean("firstStart");
        if(!firstStart){
             startActivity(new Intent(MainActivity.this,AppIntroActivity.class));
             sharedPreferences.setBoolean("firstStart",true);
        }

        nav_View.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.home) {
                    drawerlay.closeDrawer(Gravity.LEFT);
                } else if (id == R.id.statistics) {
                    startActivity(new Intent(MainActivity.this, StatisticsActivity.class));
                } else if (id == R.id.history) {
                    startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                } else if (id == R.id.recurring) {
                    startActivity(new Intent(MainActivity.this, RecurringActivity.class));
                } else if (id == R.id.settings) {
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                } else if (id == R.id.aboutus) {
                    startActivity(new Intent(MainActivity.this,AboutUsActivity.class));
                } else if (id == R.id.exportingpdf){
                    startActivity(new Intent(MainActivity.this, ExportPdfActivity.class));
                }
                drawerlay.closeDrawers();
                return true;
            }
        });

        Boolean enableStatus = getBooleanSharedPreferenceValues("disablePIN");
        if (enableStatus) {
            Bundle pinBundle = getIntent().getExtras();

            if (null == pinBundle || !pinBundle.getBoolean(PinActivity.CORRECT_PIN, false)) {
                Intent intent = new Intent(this, PinActivity.class);
                startActivity(intent);
                finish();
            }
        }
        moneyControlManager =  MoneyControlManager.getInstance(YourEuroApp.getAppContext());
        moneyControlManager.setStatisticsListener(statisticsListener);

        mRecentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecentsRecyclerView.setAdapter(moneyControlManager.getCashRecordAdapter());
        noRecordsTextView.setVisibility(moneyControlManager.getCashRecordAdapter().getItemCount() > 0 ? View.GONE : View.VISIBLE);
        statisticsCardView.setVisibility(!(moneyControlManager.getCashRecordAdapter().getItemCount() > 0) ? View.GONE : View.VISIBLE);

        mBalanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBalanceRecyclerView.setAdapter(moneyControlManager.getBalanceAdapter());

        moneyControlManager.getCashRecordAdapter().attachCashRecordListListener(new CashRecordAdapter.CashRecordListListener() {
            @Override
            public void checkRecordList() {
                noRecordsTextView.setVisibility(moneyControlManager.getCashRecordAdapter().getItemCount() > 0 ? View.GONE : View.VISIBLE);
                statisticsCardView.setVisibility(!(moneyControlManager.getCashRecordAdapter().getItemCount() > 0) ? View.GONE : View.VISIBLE);
            }
        });
        moneyControlManager.updateAllRecords();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), DetailInputActivity.class));
            }
        });

        //Statistics
        statisticsListener.listen();
        barChartFilter.setOnClickListener(this);
        pieChartFilter.setOnClickListener(this);
        barChartRefresh.setOnClickListener(this);
        pieChartRefresh.setOnClickListener(this);
        setChartHeight();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return abdt.onOptionsItemSelected(item)|| super.onOptionsItemSelected(item);
    }

    public Boolean getBooleanSharedPreferenceValues(String key){
        SharedPreferences preferencesfragment = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean value = preferencesfragment.getBoolean(key,false);
        return value;
    }

    MoneyControlManager.StatisticsListener statisticsListener = new MoneyControlManager.StatisticsListener() {
        @Override
        public void listen() {
            //Statistics
            //Piechart:
            filterResults(new CashRecordFilter(), true);
            //Barchart:
            filterResults(new CashRecordFilter(), false);

        }
    };

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
