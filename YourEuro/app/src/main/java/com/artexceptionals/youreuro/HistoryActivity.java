package com.artexceptionals.youreuro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.artexceptionals.youreuro.adapter.CashRecordAdapter;
import com.artexceptionals.youreuro.model.CashRecordFilter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity  extends AppCompatActivity {

    @BindView(R.id.recent_history_rv)
    RecyclerView mRecentsRecyclerView;

    @BindView(R.id.noRecords_tv)
    TextView noRecordsTextView;

    SearchView searchView;

    private MoneyControlManager moneyControlManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.history_toolbar);
        setSupportActionBar(toolbar);

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.history_search_menu, menu);

            MenuItem searchItem = menu.findItem(R.id.action_search);
            searchView = (SearchView) searchItem.getActionView();
            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    moneyControlManager.getCashRecordAdapter().getFilter().filter(newText);
                    return false;
                }
            });
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_filter == item.getItemId()){
            startActivityForResult(new Intent(this, FilterActivity.class), CashRecordFilter.FILTER_REQUEST_CODE);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CashRecordFilter.FILTER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                if (bundle.containsKey(CashRecordFilter.FILTER)) {
                    filterResults((CashRecordFilter) bundle.getParcelable(CashRecordFilter.FILTER));
                    searchView.clearFocus();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moneyControlManager.clearCacheCashRecords();
        moneyControlManager.updateAllRecords();
    }

    private void filterResults(CashRecordFilter cashRecordFilter) {
        moneyControlManager.clearCacheCashRecords();
        moneyControlManager.loadCashRecords(cashRecordFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
