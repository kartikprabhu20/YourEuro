package com.artexceptionals.youreuro;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.artexceptionals.youreuro.adapter.AccountAdapter;
import com.artexceptionals.youreuro.adapter.CashRecordAdapter;
import com.artexceptionals.youreuro.model.Account;
import com.artexceptionals.youreuro.model.CashRecord;
import com.artexceptionals.youreuro.model.Category;
import com.artexceptionals.youreuro.model.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recent_history_rv)
    RecyclerView mRecentsRecyclerView;
    @BindView(R.id.account_balance_rv)
    RecyclerView mAccountsRecyclerView;

    private CashRecordAdapter mCashRecordAdapter;
    private AccountAdapter mAccountAdapter;
    private MoneyControlManager moneyControlManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAccountAdapter = new AccountAdapter();
        mAccountsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAccountsRecyclerView.setAdapter(mAccountAdapter);

        mCashRecordAdapter = new CashRecordAdapter();
        mRecentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecentsRecyclerView.setAdapter(mCashRecordAdapter);
        moneyControlManager =  MoneyControlManager.getInstance(mCashRecordAdapter, mAccountAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moneyControlManager.addCashRecord(new CashRecord("test","22-01-2019", "34",
                        Constants.CurrencyType.EURO, Constants.CashRecordType.INCOME, Constants.PaymentType.BANK_ACCOUNT,
                        new Category("shopping", "test"), new Account("account1", "25.5")));

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addAccount(View view){
        moneyControlManager.addAccount(new Account("account1", "25.5"));

    }
}
