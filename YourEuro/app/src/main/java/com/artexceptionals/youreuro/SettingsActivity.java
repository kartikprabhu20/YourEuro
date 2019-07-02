package com.artexceptionals.youreuro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatActivity {
    private MoneyControlManager moneyControlManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PrefsFragment())
                .commit();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        moneyControlManager = MoneyControlManager.getInstance(YourEuroApp.getAppContext());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moneyControlManager.clearCacheCashRecords();
        moneyControlManager.updateAllRecords();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android .R.id.home == item.getItemId()){
            onBackPressed();
        }
        return true;
    }
}
