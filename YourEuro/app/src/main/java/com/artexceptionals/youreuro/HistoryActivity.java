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
import android.view.inputmethod.EditorInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity  extends AppCompatActivity {

    private static final int FILTER_REQUEST_CODE = 123;
    @BindView(R.id.recent_history_rv)
    RecyclerView mRecentsRecyclerView;

    private MoneyControlManager moneyControlManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.history_toolbar);
        setSupportActionBar(toolbar);

        moneyControlManager =  MoneyControlManager.getInstance(this);
        mRecentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecentsRecyclerView.setAdapter(moneyControlManager.getCashRecordAdapter());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.history_search_menu, menu);

            MenuItem searchItem = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_filter == item.getItemId()){
            startActivityForResult(new Intent(this, FilterActivity.class), FILTER_REQUEST_CODE);
        }
        return true;
    }
}
