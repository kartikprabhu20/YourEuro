package com.artexceptionals.youreuro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.artexceptionals.youreuro.adapter.CashRecordAdapter;
import com.artexceptionals.youreuro.model.CashRecordFilter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecurringActivity  extends AppCompatActivity {

    private static final int FILTER_REQUEST_CODE = 123;
    @BindView(R.id.recent_history_rv)
    RecyclerView mRecentsRecyclerView;

    @BindView(R.id.noRecords_tv)
    TextView noRecordsTextView;

    private MoneyControlManager moneyControlManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.history_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        moneyControlManager =  MoneyControlManager.getInstance(YourEuroApp.getAppContext());
        mRecentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecentsRecyclerView.setAdapter(moneyControlManager.getCashRecordAdapter());
        noRecordsTextView.setVisibility(moneyControlManager.getCashRecordAdapter().getItemCount() >0 ? View.GONE : View.VISIBLE);

        moneyControlManager.getCashRecordAdapter().attachCashRecordListListener(new CashRecordAdapter.CashRecordListListener() {
            @Override
            public void checkRecordList() {
                noRecordsTextView.setVisibility(moneyControlManager.getCashRecordAdapter().getItemCount() >0 ? View.GONE : View.VISIBLE);
            }
        });

        CashRecordFilter cashRecordFilter = new CashRecordFilter();
        cashRecordFilter.setRecurryingFilter(true);
        filterResults(cashRecordFilter);
    }

    private void filterResults(CashRecordFilter cashRecordFilter) {
        moneyControlManager.clearCacheCashRecords();
        moneyControlManager.loadCashRecords(cashRecordFilter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moneyControlManager.clearCacheCashRecords();
        moneyControlManager.updateAllRecords();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
