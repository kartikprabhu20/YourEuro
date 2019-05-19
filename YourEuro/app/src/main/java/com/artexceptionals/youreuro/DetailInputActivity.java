package com.artexceptionals.youreuro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.artexceptionals.youreuro.adapter.CustomCategoryAdapter;
import com.artexceptionals.youreuro.helpers.CurrencyHelper;
import com.artexceptionals.youreuro.helpers.PaymentTypeHelper;
import com.artexceptionals.youreuro.helpers.RecurringHelper;
import com.artexceptionals.youreuro.model.CashRecord;
import com.artexceptionals.youreuro.model.Category;
import com.artexceptionals.youreuro.model.Constants;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailInputActivity extends AppCompatActivity {

    @BindView(R.id.category_spinner)
    Spinner categorySpinner;

    @BindView(R.id.paymenttype_spinner)
    Spinner paymentTypeSpinner;

    @BindView(R.id.scheduler_spinner)
    Spinner scheduleSpinner;

    @BindView(R.id.amount_et)
    EditText amountEditText;

    @BindView(R.id.date_tv)
    TextView dateTextView;

    @BindView(R.id.time_tv)
    TextView timeTextView;

    @BindView(R.id.currency_symbol_tv)
    TextView currencySymbolTextView;

    @BindView(R.id.calendar_iv)
    ImageView calendarImageView;

    @BindView(R.id.time_iv)
    ImageView timeImageView;

    @BindView(R.id.note_et)
    EditText noteEditText;

    @BindView(R.id.togglebutton_expense)
    ToggleButton expenseToggleButton;

    @BindView(R.id.schedule_layout)
    LinearLayout scheduleLinearLayout;

    @BindView(R.id.recurring_cb)
    CheckBox recurringCheckBox;

    @BindView(R.id.togglebutton_income)
    ToggleButton incomeToggleButton;

    MoneyControlManager moneyControlManager;
    ArrayAdapter<Category> categoryAdapter = null;
    CashRecord cashRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    private void init() {
        moneyControlManager = MoneyControlManager.getInstance(this);
        cashRecord = new CashRecord();
        cashRecord.setCashRecordType(Constants.CashRecordType.EXPENSE);

        ArrayAdapter<CharSequence> paymentTypesAdapter = ArrayAdapter.createFromResource(this,
                R.array.paymenttypes_array, R.layout.spinner_item);
        paymentTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentTypeSpinner.setAdapter(paymentTypesAdapter);

         categoryAdapter = new CustomCategoryAdapter(this,moneyControlManager.getAllCategories());
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> scheduleTypesAdapter = ArrayAdapter.createFromResource(this,
                R.array.schedule_array, R.layout.spinner_item);
        new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item);
        scheduleTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scheduleSpinner.setAdapter(scheduleTypesAdapter);

        expenseToggleButton.setOnClickListener(onClickListener);
        incomeToggleButton .setOnClickListener(onClickListener);
        recurringCheckBox.setOnClickListener(onClickListener);
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
            saveCashRecord();
            return true;
        }else if (id == R.id.details_cancel) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.togglebutton_income:
                    incomeToggleButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    expenseToggleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    cashRecord.setCashRecordType(Constants.CashRecordType.INCOME);
                    amountEditText.setTextColor(getResources().getColor(R.color.green));
                    break;
                case R.id.togglebutton_expense:
                    incomeToggleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    expenseToggleButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    cashRecord.setCashRecordType(Constants.CashRecordType.EXPENSE);
                    amountEditText.setTextColor(getResources().getColor(R.color.red));
                    break;

                case R.id.recurring_cb:
                    if(recurringCheckBox.isChecked()){
                        scheduleLinearLayout.setVisibility(View.VISIBLE);
                    }else {
                        scheduleLinearLayout.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    private void saveCashRecord() {
        cashRecord.setAmount(String.valueOf(amountEditText.getText()));
        cashRecord.setCategory(categoryAdapter.getItem(categorySpinner.getSelectedItemPosition()));
        cashRecord.setNotes(String.valueOf(noteEditText.getText()));
        cashRecord.setTimeStamp(new Date().getTime());
        cashRecord.setCurrency(CurrencyHelper.CurrencyType.EURO);// Save currency type in shared preference in settings, use same sharedpreference to get currency here
        cashRecord.setPaymentType(PaymentTypeHelper.getPaymentType(paymentTypeSpinner.getSelectedItem().toString()));
        moneyControlManager.addCashRecord(cashRecord);

        cashRecord.setRecurringTransaction(recurringCheckBox.isChecked());
        cashRecord.setRecurringType(recurringCheckBox.isChecked()? scheduleSpinner.getSelectedItem().toString(): RecurringHelper.RecurringType.UNKNOWN);

        onBackPressed();
    }
}
