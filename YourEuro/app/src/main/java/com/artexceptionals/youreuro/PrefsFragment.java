package com.artexceptionals.youreuro;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;

import android.support.v14.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.artexceptionals.youreuro.helpers.CurrencyHelper;


public  class PrefsFragment extends PreferenceFragment {

    private static final String DISABLE_PIN = "disablePIN";
    private static final String CURRENCY_CHANGE = "changeCurrency";
    CustomSharedPreferences sharedPreferences;
    Preference switchPreference, changePinPreference, currencyPreference, addCategoryPreference, setEmailAccountPreference, addCategoryThreshold;
    private int enteredPIN,enteredOldPIN,enteredNewPIN;
    private String enteredEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);
        sharedPreferences = CustomSharedPreferences.getInstance(YourEuroApp.getAppContext());
        switchPreference = findPreference(DISABLE_PIN);
        changePinPreference = findPreference("changePIN");
        currencyPreference = findPreference("changeCurrency");
        addCategoryPreference = findPreference("modifyCategory");
        addCategoryThreshold = findPreference("modifyThreshold");
        setEmailAccountPreference = findPreference("setEmailAccount");
        changePinPreference.setOnPreferenceClickListener(onClickPreference);
        addCategoryPreference.setOnPreferenceClickListener(onClickPreference);
        addCategoryThreshold.setOnPreferenceClickListener(onClickPreference);
        setEmailAccountPreference.setOnPreferenceClickListener(onClickPreference);
        switchPreference.setOnPreferenceChangeListener(preferenceChangeListener);
        currencyPreference.setOnPreferenceChangeListener(preferenceChangeListener);
        currencyPreference.setDefaultValue(sharedPreferences.genericGetString(CurrencyHelper.CURRENT_CURRENCY, CurrencyHelper.CurrencyType.EURO));
        emailPreferenceSetSummary();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }

    private void emailPreferenceSetSummary(){
        setEmailAccountPreference.setSummary(sharedPreferences.genericGetString("user_Email","Not Set"));
    }

    @SuppressLint("RestrictedApi")
    private void launchDialogDisable() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Confirm PIN");
        alertDialog.setMessage("Enter Pin");

        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setTransformationMethod(new PasswordTransformationMethod());
        alertDialog.setView(input, 50,0,50,0);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                if (!input.getText().toString().isEmpty()) {
                    int enteredPIN = Integer.parseInt(input.getText().toString());
                    if (sharedPreferences.getInt("user_pin") == enteredPIN) {
                        Toast.makeText(getActivity(), "Password Matched", Toast.LENGTH_SHORT).show();
                        ((SwitchPreference) switchPreference).setChecked(false);
                        dialog.dismiss();
                    }else {
                        Toast.makeText(getActivity(), "Wrong Password!", Toast.LENGTH_SHORT).show();
                        launchDialogDisable();
                    }
                }else {
                    Toast.makeText(getActivity(), "Pin can't be empty", Toast.LENGTH_SHORT).show();
                    launchDialogDisable();
                }
            }
        });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    @SuppressLint("RestrictedApi")
    private void launchDialogEnable() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("SET PIN");

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.set_pin,null);
        alertDialog.setView(linearLayout);

        Spinner securityQuestionsSpinner_1 = (Spinner)linearLayout.findViewById(R.id.security_questions_spinner_1);
        ArrayAdapter<CharSequence> securityQuestionsAdapter_1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.questions, R.layout.spinner_item);
        securityQuestionsAdapter_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        securityQuestionsSpinner_1.setAdapter(securityQuestionsAdapter_1);

        EditText setPIN = (EditText) linearLayout.findViewById(R.id.PINEditText);
        EditText securityAnswer = (EditText) linearLayout.findViewById(R.id.securityAnswerEditText_1);

        alertDialog.setPositiveButton("SET",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        if (!setPIN.getText().toString().isEmpty() && !securityAnswer.getText().toString().isEmpty()) {

                            enteredPIN = Integer.parseInt(setPIN.getText().toString());
                            sharedPreferences.setInt("user_pin",enteredPIN);
                            sharedPreferences.genericSetString("security_question", securityQuestionsSpinner_1.getSelectedItem().toString());
                            sharedPreferences.genericSetString("security_answer",securityAnswer.getText().toString());
                            Toast.makeText(getActivity(),"Your PIN is set",Toast.LENGTH_LONG).show();
                            ((SwitchPreference) switchPreference).setChecked(true);

                        }else if(!securityAnswer.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(),"Your PIN is set",Toast.LENGTH_LONG).show();
                            launchDialogEnable();
                        }else if(!setPIN.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Answer can't be empty", Toast.LENGTH_LONG).show();
                            launchDialogEnable();
                        }else {
                            Toast.makeText(getActivity(), "Pin or Answer can't be empty", Toast.LENGTH_LONG).show();
                            launchDialogEnable();
                        }
                    }
                }
        );

        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    @SuppressLint("RestrictedApi")
    private void launchDialogChangePIN() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("CHANGE PIN");

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.change_pin,null);
        alertDialog.setView(linearLayout);

        EditText oldPIN = (EditText) linearLayout.findViewById(R.id.oldPINEditText);
        EditText newPIN = (EditText) linearLayout.findViewById(R.id.newPINEditText);
        alertDialog.setPositiveButton("SET",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        if (oldPIN.getText() == null || oldPIN.getText().toString().isEmpty())
                            Toast.makeText(getActivity(),"Old PIN can't be empty.",Toast.LENGTH_LONG).show();

                        if (newPIN.getText() == null || newPIN.getText().toString().isEmpty())
                            Toast.makeText(getActivity(),"New PIN can't be empty.",Toast.LENGTH_LONG).show();


                        if(oldPIN.getText() != null && newPIN.getText() != null && !newPIN.getText().toString().equalsIgnoreCase("")
                                && !oldPIN.getText().toString().equalsIgnoreCase("")){
                            enteredOldPIN = Integer.parseInt(oldPIN.getText().toString());
                            enteredNewPIN = Integer.parseInt(newPIN.getText().toString());
                            if(sharedPreferences.getInt("user_pin") == enteredOldPIN){
                                sharedPreferences.setInt("user_pin",enteredNewPIN);
                                Toast.makeText(getActivity(),"Your New PIN has been set.",Toast.LENGTH_LONG).show();
                            }else {
                                oldPIN.setError(getResources().getString(R.string.wrong_pin));
                                Toast.makeText(getActivity(),"Entered wrong Old PIN",Toast.LENGTH_LONG).show();
                                launchDialogChangePIN();
                            }
                         }else {
                            launchDialogChangePIN();
                        }
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

    @SuppressLint("RestrictedApi")
    private void launchDialogSetEmail() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("SET Email Account");
        alertDialog.setMessage("Enter mail id");

        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        int dim = (int) getResources().getDimension(R.dimen.unit1);
        lp.setMargins(dim,dim,dim,dim);
        input.setLayoutParams(lp);
        alertDialog.setView(input, 50,0,50,0);

        alertDialog.setPositiveButton("SET",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        enteredEmail = input.getText().toString();
                        sharedPreferences.genericSetString("user_Email",enteredEmail);
                        Toast.makeText(getActivity(),"Your Email account has been set.",Toast.LENGTH_LONG).show();
                        emailPreferenceSetSummary();
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

    Preference.OnPreferenceChangeListener preferenceChangeListener = new Preference.OnPreferenceChangeListener() {

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (DISABLE_PIN.equalsIgnoreCase(preference.getKey())) {

                boolean switched = ((SwitchPreference) preference).isChecked();
                if (switched) {
                    launchDialogDisable();
                } else {
                    launchDialogEnable();
                }
            }else if (CURRENCY_CHANGE.equalsIgnoreCase(preference.getKey())){
                sharedPreferences.genericSetString(CurrencyHelper.CURRENT_CURRENCY, String.valueOf(newValue));
                return true;
            }
            return false;
        }
    };

    private Preference.OnPreferenceClickListener onClickPreference = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {

            if ("changePIN".equalsIgnoreCase(preference.getKey())) {
                launchDialogChangePIN();
            }else if("modifyCategory".equalsIgnoreCase(preference.getKey())){
                Intent intent = new Intent(YourEuroApp.getAppContext(), CategoryActivity.class);
                intent.putExtra(CategoryActivity.TYPE, CategoryActivity.ADD_CATEGORY);
                startActivity(intent);
            }else if("setEmailAccount".equalsIgnoreCase(preference.getKey())){
                launchDialogSetEmail();
            }else if("modifyThreshold".equalsIgnoreCase(preference.getKey())){
                Intent intent = new Intent(YourEuroApp.getAppContext(), CategoryActivity.class);
                intent.putExtra(CategoryActivity.TYPE, CategoryActivity.ADD_THRESHOLD);
                startActivity(intent);
            }
            return false;
        }
    };
}
