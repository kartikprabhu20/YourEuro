package com.artexceptionals.youreuro;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.artexceptionals.youreuro.adapter.CustomCategoryAdapter;
import com.artexceptionals.youreuro.helpers.CurrencyHelper;
import com.artexceptionals.youreuro.helpers.CurrencyInputFilter;
import com.artexceptionals.youreuro.helpers.PaymentTypeHelper;
import com.artexceptionals.youreuro.helpers.RecurringHelper;
import com.artexceptionals.youreuro.model.CashRecord;
import com.artexceptionals.youreuro.model.Category;
import com.artexceptionals.youreuro.model.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailInputActivity extends AppCompatActivity implements View.OnClickListener {

    private int day,month,year,hour,minute;

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

    @BindView(R.id.category_selected_ll)
    LinearLayout categoryLinearLayout;

    @BindView(R.id.selected_paymenttype)
    TextView paymentTypeTextView;

    MoneyControlManager moneyControlManager;
    ArrayAdapter<Category> categoryAdapter = null;
    CashRecord cashRecord;

    public static final String DEFAULT_TIME ="--:--";
    public static final String DEFAULT_DATE ="--/--/----";
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        init();

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void init() {
        moneyControlManager = MoneyControlManager.getInstance(YourEuroApp.getAppContext());
        cashRecord = new CashRecord();
        cashRecord.setCashRecordType(Constants.CashRecordType.EXPENSE);

        categoryLinearLayout.setVisibility(View.GONE);
        paymentTypeTextView.setVisibility(View.GONE);
        ArrayAdapter<CharSequence> paymentTypesAdapter = ArrayAdapter.createFromResource(this,
                R.array.paymenttypes_array, R.layout.spinner_item);
        paymentTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentTypeSpinner.setAdapter(paymentTypesAdapter);

        categoryAdapter = new CustomCategoryAdapter(this,moneyControlManager.getAllCategories());
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> scheduleTypesAdapter = ArrayAdapter.createFromResource(this,
                R.array.schedule_array, R.layout.spinner_item);
        scheduleTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scheduleSpinner.setAdapter(scheduleTypesAdapter);

        expenseToggleButton.setOnClickListener(onClickListener);
        incomeToggleButton .setOnClickListener(onClickListener);
        recurringCheckBox.setOnClickListener(onClickListener);


        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        String current_time= hour+":"+minute;

        dateTextView.setText(DEFAULT_DATE);
        timeTextView.setText(DEFAULT_TIME);
        calendarImageView.setOnClickListener(this);
        timeImageView.setOnClickListener(this);

        currencySymbolTextView.setText(CurrencyHelper.getSymbol(moneyControlManager.getSharedPreference().genericGetString(CurrencyHelper.CURRENT_CURRENCY, CurrencyHelper.CurrencyType.EURO)));

        amountEditText.setFilters(new InputFilter[]{new CurrencyInputFilter(8,2)});

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(CashRecord.CASHRECORD_DETAIL)) {
            isUpdate = true;
            cashRecord = (CashRecord) bundle.getParcelable(CashRecord.CASHRECORD_DETAIL);
            amountEditText.setText(String.format("%.2f",cashRecord.getAmount()));
            categorySpinner.setSelection(categoryAdapter.getPosition(cashRecord.getCategory()));
            paymentTypeSpinner.setSelection(paymentTypesAdapter.getPosition(cashRecord.getPaymentType()));
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            timeTextView.setText(dateFormat.format(cashRecord.getTimeStamp()));
            dateTextView.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(cashRecord.getTimeStamp()));
            currencySymbolTextView.setText(CurrencyHelper.getSymbol(cashRecord.getCurrency()));

            noteEditText.setText(cashRecord.getNotes());
            recurringCheckBox.setChecked(cashRecord.isRecurringTransaction());
            scheduleLinearLayout.setVisibility(recurringCheckBox.isChecked() ?View.VISIBLE : View.GONE);
            scheduleSpinner.setSelection(scheduleTypesAdapter.getPosition(cashRecord.getRecurringType()));

            if(Constants.CashRecordType.EXPENSE.equalsIgnoreCase(cashRecord.getCashRecordType())){
                incomeToggleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                expenseToggleButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                amountEditText.setTextColor(getResources().getColor(R.color.red));

            }else{
                incomeToggleButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                expenseToggleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                amountEditText.setTextColor(getResources().getColor(R.color.green));
            }
        }else {
            isUpdate = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        if (id == R.id.details_ok) {
            boolean result = true;
            if (amountEditText.getText().toString().equalsIgnoreCase("")) {
                amountEditText.setError("Amount cannot be empty");
                amountEditText.requestFocus();
                amountEditText.startAnimation(shake);
                result = false;
            }
            if (categorySpinner.getSelectedItemPosition() == 0) {
                categorySpinner.startAnimation(shake);
                categorySpinner.requestFocus();
                ((TextView)categorySpinner.getSelectedView().findViewById(R.id.category_spinner_Name)).setTextColor(getResources().getColor(R.color.red));
                ((ImageView)categorySpinner.getSelectedView().findViewById(R.id.category_spinner_image)).setBackgroundColor(getResources().getColor(R.color.highlight_red));
                result = false;
            }
            if (paymentTypeSpinner.getSelectedItemPosition() == 0) {
                paymentTypeSpinner.startAnimation(shake);
                paymentTypeSpinner.requestFocus();
                ((TextView)paymentTypeSpinner.getSelectedView()).setTextColor(getResources().getColor(R.color.red));
                result = false;
            }

            if (dateTextView.getText().toString().equalsIgnoreCase(DEFAULT_DATE)){
                dateTextView.requestFocus();
                dateTextView.setTextColor(getResources().getColor(R.color.red));
                dateTextView.startAnimation(shake);
                calendarImageView.startAnimation(shake);
                calendarImageView.setBackgroundColor(getResources().getColor(R.color.highlight_red));
                result = false;
            }

            if (timeTextView.getText().toString().equalsIgnoreCase(DEFAULT_TIME)){
                timeTextView.requestFocus();
                timeTextView.setTextColor(getResources().getColor(R.color.red));
                timeTextView.startAnimation(shake);
                timeImageView.startAnimation(shake);
                timeImageView.setBackgroundColor(getResources().getColor(R.color.highlight_red));
                result = false;
            }

            if (result)
                saveCashRecord();

            return result;

        }else if (id == R.id.details_cancel) {
            cancelDialog();
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
                    scheduleLinearLayout.setVisibility(recurringCheckBox.isChecked() ?View.VISIBLE : View.GONE);

            }
        }
    };

    private void saveCashRecord() {
        float amount = Float.parseFloat(String.valueOf(amountEditText.getText()));
        cashRecord.setAmount(cashRecord.getCashRecordType().equalsIgnoreCase(Constants.CashRecordType.INCOME) ? amount : amount * -1);
        cashRecord.setCategory(categoryAdapter.getItem(categorySpinner.getSelectedItemPosition()));
        cashRecord.setNotes(String.valueOf(noteEditText.getText()));

        Date timeStamp = new Date();
        try {
            timeStamp = new SimpleDateFormat("dd-MM-yyyy hh:mm").parse(dateTextView.getText() + " " + timeTextView.getText());
        } catch (ParseException e) {
            Log.e("YourEuro", "ParseException in date formatting");
        }
        cashRecord.setTimeStamp(timeStamp.getTime());
        cashRecord.setCurrency(moneyControlManager.getSharedPreference().genericGetString(CurrencyHelper.CURRENT_CURRENCY, CurrencyHelper.CurrencyType.EURO));
        cashRecord.setPaymentType(PaymentTypeHelper.getPaymentType(paymentTypeSpinner.getSelectedItem().toString()));
        cashRecord.setRecurringTransaction(recurringCheckBox.isChecked());
        cashRecord.setRecurringType(recurringCheckBox.isChecked() ? scheduleSpinner.getSelectedItem().toString() : RecurringHelper.RecurringType.UNKNOWN);
        cashRecord.setRecurred(!recurringCheckBox.isChecked());

        if (!isUpdate){
            moneyControlManager.addCashRecord(cashRecord);
        }else {
            moneyControlManager.updateCashRecord(cashRecord);
        }
        onBackPressed();
    }

    private void cancelDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DetailInputActivity.this);
        builder.setMessage("Are you sure you want to cancel?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        if (v == calendarImageView) {
            Calendar c = Calendar.getInstance();
            day = c.get(Calendar.DAY_OF_MONTH);
            month = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    dateTextView.setTextColor(getResources().getColor(R.color.black));
                    calendarImageView.setBackgroundColor(Color.TRANSPARENT);

                    dateTextView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                }
            }, day, month, year);
            datePickerDialog.updateDate(year,month,day);
            datePickerDialog.show();

        }else if (v == timeImageView) {
            Calendar c = Calendar.getInstance();
            Date date = new Date();

            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeTextView.setTextColor(getResources().getColor(R.color.black));
                        timeImageView.setBackgroundColor(Color.TRANSPARENT);
                        timeTextView.setText(hourOfDay + ":" + checkDigit(minute));
                    }

                    public String checkDigit(int minute) {
                        return minute <= 9 ? "0" + minute : String.valueOf(minute);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
        }
    }
}
