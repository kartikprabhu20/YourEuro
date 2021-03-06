package com.artexceptionals.youreuro;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.artexceptionals.youreuro.helpers.CurrencyHelper;
import com.artexceptionals.youreuro.model.CashRecord;
import com.artexceptionals.youreuro.model.Category;
import com.artexceptionals.youreuro.model.Constants;
import com.maltaisn.icondialog.IconHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailDisplayActivity extends AppCompatActivity {

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

    @BindView(R.id.category_selected_image)
    ImageView categorySelectedImageView;

    @BindView(R.id.category_selected_Name)
    TextView categorySelectedTextView;

    @BindView(R.id.selected_paymenttype)
    TextView paymentSelectedTextView;

    MoneyControlManager moneyControlManager;
    ArrayAdapter<Category> categoryAdapter = null;
    CashRecord cashRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey(CashRecord.CASHRECORD_DETAIL)) {
            cashRecord = (CashRecord) bundle.getParcelable(CashRecord.CASHRECORD_DETAIL);
        }
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    private void init() {
        moneyControlManager = MoneyControlManager.getInstance(YourEuroApp.getAppContext());

        if(Constants.CashRecordType.EXPENSE.equalsIgnoreCase(cashRecord.getCashRecordType())){
            expenseToggleButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            expenseToggleButton.setClickable(false);
            incomeToggleButton.setVisibility(View.GONE);
            amountEditText.setTextColor(getResources().getColor(R.color.red));

        }else{
            incomeToggleButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            incomeToggleButton.setClickable(false);
            expenseToggleButton.setVisibility(View.GONE);
            amountEditText.setTextColor(getResources().getColor(R.color.green));
        }

        paymentTypeSpinner.setVisibility(View.GONE);
        categorySpinner.setVisibility(View.GONE);
        if(cashRecord.getCategory().isDefault) {
            categorySelectedImageView.setImageDrawable(this.getResources().getDrawable(this.getResources().getIdentifier(cashRecord.getCategory().getImageID(), "drawable", this.getPackageName())));
        }else{
            IconHelper iconHelper = IconHelper.getInstance(this);
            iconHelper.addLoadCallback(new IconHelper.LoadCallback() {
                @Override
                public void onDataLoaded() {
                    categorySelectedImageView.setImageDrawable(iconHelper.getIcon(Integer.parseInt(cashRecord.getCategory().getImageID())).getDrawable(getApplicationContext()));
                }
            });
        }

        categorySelectedTextView.setText(cashRecord.getCategory().getCatagoryName());
        paymentSelectedTextView.setText(cashRecord.getPaymentType());

        recurringCheckBox.setChecked(cashRecord.isRecurringTransaction());
        recurringCheckBox.setEnabled(false);
        ArrayAdapter<CharSequence> scheduleTypesAdapter = ArrayAdapter.createFromResource(this,
                R.array.schedule_array, R.layout.spinner_item);
        new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item);
        scheduleTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scheduleSpinner.setAdapter(scheduleTypesAdapter);
        scheduleLinearLayout.setVisibility(cashRecord.isRecurringTransaction()?View.VISIBLE:View.GONE);
        scheduleSpinner.setSelection(scheduleTypesAdapter.getPosition(cashRecord.getRecurringType()));
        scheduleSpinner.setEnabled(false);

        amountEditText.setText(String.format("%.2f",cashRecord.getAmount()));
        amountEditText.setEnabled(false);

        noteEditText.setText(cashRecord.getNotes());
        noteEditText.setEnabled(false);

        calendarImageView.setVisibility(View.GONE);
        timeImageView.setVisibility(View.GONE);

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        timeTextView.setText(dateFormat.format(cashRecord.getTimeStamp()));
        dateTextView.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(cashRecord.getTimeStamp()));

        currencySymbolTextView.setText(CurrencyHelper.getSymbol(cashRecord.getCurrency()));

    }

    private void launchDialog(CashRecord cashRecord) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailDisplayActivity.this);
        alertDialog.setTitle("Please confirm to Delete");

        alertDialog.setPositiveButton("CONFIRM",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        moneyControlManager.deleteCashRecord(cashRecord);
                        onBackPressed();
                        Toast.makeText(DetailDisplayActivity.this,
                                "cashRecord Deleted", Toast.LENGTH_SHORT).show();
                    }
                });

        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.details_delete) {
            launchDialog(cashRecord);
            return true;
        }else if (id == R.id.details_edit) {
            Intent intent = new Intent(this, DetailInputActivity.class);
            intent.putExtra(CashRecord.CASHRECORD_DETAIL,cashRecord);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
