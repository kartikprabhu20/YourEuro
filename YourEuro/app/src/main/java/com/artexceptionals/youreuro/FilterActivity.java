package com.artexceptionals.youreuro;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.artexceptionals.youreuro.adapter.CategoryFilterAdapter;
import com.artexceptionals.youreuro.model.CashRecordFilter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterActivity  extends AppCompatActivity implements View.OnClickListener {

    ImageView istartdate,ienddate;
    TextView tstartdate,tenddate;

    private int day,month,year;

    @BindView(R.id.paymentype_filter_spinner)
    Spinner paymentTypeSpinner;

    @BindView(R.id.save_filter)
    Button saveFilterButton;

    @BindView(R.id.cancel_filter)
    Button cancelFilterButton;

    @BindView(R.id.start_amount_et)
    EditText startAmountEditText;

    @BindView(R.id.end_amount_et)
    EditText endAmountEditText;

    @BindView(R.id.date_range_ll)
    LinearLayout dateRangeLinearLayout;

    @BindView(R.id.amount_range_ll)
    LinearLayout amountRangeLinearLayout;


    @BindView(R.id.category_filter_checkbox)
    CheckBox categoryFilterCheckbox;

    @BindView(R.id.date_filter_checkbox)
    CheckBox dateFilterCheckbox;

    @BindView(R.id.range_filter_checkbox)
    CheckBox rangeFilterCheckbox;

    @BindView(R.id.payment_filter_checkbox)
    CheckBox paymentFilterCheckbox;

    @BindView(R.id.category_filter_listView)
    ListView categoryListView;

    MoneyControlManager moneyControlManager;
    CategoryFilterAdapter categoryAdapter = null;
    private CashRecordFilter cashRecordFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.filter_toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    private void init() {
        moneyControlManager = MoneyControlManager.getInstance(YourEuroApp.getAppContext());

        ArrayAdapter<CharSequence> paymentTypesAdapter = ArrayAdapter.createFromResource(this,
                R.array.paymenttypes_array, R.layout.spinner_item);
        paymentTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentTypeSpinner.setAdapter(paymentTypesAdapter);

        categoryAdapter = new CategoryFilterAdapter(this,moneyControlManager.getAllCategories());
        categoryListView.setAdapter(categoryAdapter);

        cancelFilterButton.setOnClickListener(onClickListener);
        saveFilterButton.setOnClickListener(onClickListener);

        categoryFilterCheckbox.setOnClickListener(onClickListener);
        rangeFilterCheckbox.setOnClickListener(onClickListener);
        paymentFilterCheckbox.setOnClickListener(onClickListener);
        dateFilterCheckbox.setOnClickListener(onClickListener);

        categoryListView.setVisibility(View.GONE);
        dateRangeLinearLayout.setVisibility(View.GONE);
        amountRangeLinearLayout.setVisibility(View.GONE);
        paymentTypeSpinner.setVisibility(View.GONE);
        
        String current_date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        istartdate = (ImageView) findViewById(R.id.start_date_iv);
        ienddate = (ImageView) findViewById(R.id.end_date_iv);
        tstartdate = (TextView) findViewById(R.id.start_date_tv);
        tstartdate.setText(current_date);
        tenddate = (TextView) findViewById(R.id.end_date_tv);
        tenddate.setText(current_date);
        istartdate.setOnClickListener(this);
        ienddate.setOnClickListener(this);

    }

    View.OnClickListener onClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case  R.id.save_filter:
                    String startAmount = String.valueOf(startAmountEditText.getText());
                    String endAmount = String.valueOf(endAmountEditText.getText());

                    Date startTimeStamp = new Date();
                    Date endTimeStamp = new Date();
                    try {
                        startTimeStamp = new SimpleDateFormat("dd-MM-yyyy").parse(tstartdate.getText().toString());
                        endTimeStamp = new SimpleDateFormat("dd-MM-yyyy").parse(tstartdate.getText().toString());

                    } catch (ParseException e) {
                        Log.e("YourEuro", "ParseException in dateformating");
                    }

                    if(!checkDates(tstartdate.getText().toString(),tenddate.getText().toString())) {
                        break;
                    }
                    cashRecordFilter = new CashRecordFilter(startTimeStamp.getTime(),endTimeStamp.getTime(),
                            Float.valueOf(startAmount.isEmpty()? "0": startAmount),Float.valueOf(endAmount.isEmpty()? "100000000000":endAmount),
                            categoryAdapter.getSelectedItems(),paymentTypeSpinner.getSelectedItem().toString(),
                            categoryFilterCheckbox.isChecked(),dateFilterCheckbox.isChecked(),paymentFilterCheckbox.isChecked(),rangeFilterCheckbox.isChecked());
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(CashRecordFilter.FILTER, cashRecordFilter);
                    setResult(RESULT_OK,returnIntent);
                    finish();
                    break;

                case  R.id.cancel_filter:
                    Intent returnIntentCancelled = new Intent();
                    setResult(RESULT_CANCELED, returnIntentCancelled);
                    finish();
                    onBackPressed();
                    break;

                case R.id.category_filter_checkbox:
                        categoryListView.setVisibility(categoryFilterCheckbox.isChecked()? View.VISIBLE:View.GONE);
                    break;
                case R.id.date_filter_checkbox:
                    dateRangeLinearLayout.setVisibility(dateFilterCheckbox.isChecked()? View.VISIBLE:View.GONE);
                    break;
                case R.id.range_filter_checkbox:
                    amountRangeLinearLayout.setVisibility(rangeFilterCheckbox.isChecked()? View.VISIBLE:View.GONE);
                    break;
                case R.id.payment_filter_checkbox:
                    paymentTypeSpinner.setVisibility(paymentFilterCheckbox.isChecked()? View.VISIBLE:View.GONE);
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {

        if (v == istartdate) {
            Calendar c = Calendar.getInstance();
            day = c.get(Calendar.DAY_OF_MONTH);
            month = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    tstartdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                }
            }, day, month, year);
            datePickerDialog.updateDate(year, month, day);
            datePickerDialog.show();
        }

        if (v == ienddate) {
            Calendar c = Calendar.getInstance();
            day = c.get(Calendar.DAY_OF_MONTH);
            month = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    tenddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                }
            }, day, month, year);
            datePickerDialog.updateDate(year, month, day);
            datePickerDialog.show();
        }
    }

    public boolean checkDates(String tstartdate, String tenddate) {
        SimpleDateFormat dfDate = new SimpleDateFormat("dd-MM-yyyy");

        boolean b = true;

        try {
            if (dfDate.parse(tstartdate).before(dfDate.parse(tenddate))) {
                return true;//If start date is before end date
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "End date is before Start date", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
