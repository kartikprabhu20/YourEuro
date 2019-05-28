package com.artexceptionals.youreuro;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TimePicker;

import com.artexceptionals.youreuro.adapter.CashRecordAdapter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerlay;
    private ActionBarDrawerToggle abdt;

    @BindView(R.id.recent_history_rv)
    RecyclerView mRecentsRecyclerView;

    @BindView(R.id.noRecords_tv)
    TextView noRecordsTextView;


    private MoneyControlManager moneyControlManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        drawerlay=(DrawerLayout)findViewById(R.id.drawerlay);
        abdt=new ActionBarDrawerToggle(this,drawerlay,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);


        drawerlay.addDrawerListener(abdt);
        abdt.syncState();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_View=(NavigationView)findViewById(R.id.nav_view);

        nav_View.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.home) {
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.statistics) {
                    Toast.makeText(MainActivity.this, "Statistics", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.history) {
                    startActivity(new Intent(MainActivity.this, HistoryActivity.class));


                } else if (id == R.id.settings) {
                    Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.aboutus) {
                    Toast.makeText(MainActivity.this, "About Us", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        Bundle pinBundle = getIntent().getExtras();

        if ( null == pinBundle || !pinBundle.getBoolean(PinActivity.CORRECT_PIN, false)) {
            Intent intent = new Intent(this, PinActivity.class);
            startActivity(intent);
        }
        moneyControlManager =  MoneyControlManager.getInstance(this);
        mRecentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecentsRecyclerView.setAdapter(moneyControlManager.getCashRecordAdapter());
        noRecordsTextView.setVisibility(moneyControlManager.getCashRecordAdapter().getItemCount() >0 ? View.GONE : View.VISIBLE);

        moneyControlManager.getCashRecordAdapter().attachCashRecordListListener(new CashRecordAdapter.CashRecordListListener() {
            @Override
            public void checkRecordList() {
                noRecordsTextView.setVisibility(moneyControlManager.getCashRecordAdapter().getItemCount() >0 ? View.GONE : View.VISIBLE);
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
        }else if (id == R.id.history) {
            startActivity(new Intent(this, HistoryActivity.class));
            return true;
        }

        return abdt.onOptionsItemSelected(item)|| super.onOptionsItemSelected(item);
    }


}
