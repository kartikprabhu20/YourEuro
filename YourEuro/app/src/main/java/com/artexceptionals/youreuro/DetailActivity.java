package com.artexceptionals.youreuro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.category_spinner)
    Spinner categorySpinner;

    @BindView(R.id.paymenttype_spinner)
    Spinner paymentTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);


        ArrayAdapter<CharSequence> paymentTypesAdapter = ArrayAdapter.createFromResource(this,
                R.array.paymenttypes_array, R.layout.spinner_item);
        paymentTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentTypeSpinner.setAdapter(paymentTypesAdapter);

        ArrayAdapter<CharSequence> categoryTypesAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, R.layout.spinner_item);
        categoryTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryTypesAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.details_ok) {

            return true;
        }else if (id == R.id.detail_toolbar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
