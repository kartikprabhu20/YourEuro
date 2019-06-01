package com.artexceptionals.youreuro;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;

import android.support.v14.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public  class PrefsFragment extends PreferenceFragment {

    CustomSharedPreferences sharedPreferences;
    //    SharedPreferences sharedPreferences;
    Preference switchPreference, changePinPreference;
    private int enteredPIN,enteredOldPIN,enteredNewPIN;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);
        sharedPreferences = CustomSharedPreferences.getInstance(getActivity());
        switchPreference = findPreference("disablePIN");
        changePinPreference = findPreference("changePIN");
        changePinPreference.setOnPreferenceClickListener(onClickPreference);
        switchPreference.setOnPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

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
                int enteredPIN = Integer.parseInt(input.getText().toString());
                if (sharedPreferences.getInt("user_pin") == enteredPIN) {
                    Toast.makeText(getActivity(), "Password Matched", Toast.LENGTH_SHORT).show();
                    ((SwitchPreference) switchPreference).setChecked(false);
                    dialog.dismiss();
                }else {
                    Toast.makeText(getActivity(), "Wrong Password!", Toast.LENGTH_SHORT).show();
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
        alertDialog.setMessage("Enter Pin");

        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        int dim = (int) getResources().getDimension(R.dimen.unit1);
        lp.setMargins(dim,dim,dim,dim);
        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setTransformationMethod(new PasswordTransformationMethod());
        alertDialog.setView(input, 50,0,50,0);

        alertDialog.setPositiveButton("SET",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        enteredPIN = Integer.parseInt(input.getText().toString());
                        sharedPreferences.setInt("user_pin",enteredPIN);
                        Toast.makeText(getActivity(),"Your PIN has been set.",Toast.LENGTH_LONG).show();
                        ((SwitchPreference) switchPreference).setChecked(true);
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
    private void launchDialogChangePIN() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("CHANGE PIN");
        LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.change_pin,null);
        alertDialog.setView(linearLayout);

        EditText oldPIN = (EditText) linearLayout.findViewById(R.id.oldPINEditText);
        EditText newPIN = (EditText) linearLayout.findViewById(R.id.newPINEditText);
        alertDialog.setPositiveButton("SET",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        if (oldPIN.getText() == null || oldPIN.getText().toString().equalsIgnoreCase(""))
                            oldPIN.setError(getResources().getString(R.string.empty_pin));

                        if (newPIN.getText() == null || newPIN.getText().toString().equalsIgnoreCase(""))
                            newPIN.setError(getResources().getString(R.string.empty_pin));

                        if(oldPIN.getText() != null && newPIN.getText() != null && !newPIN.getText().toString().equalsIgnoreCase("")
                                && !oldPIN.getText().toString().equalsIgnoreCase("")){
                        enteredOldPIN = Integer.parseInt(oldPIN.getText().toString());
                        enteredNewPIN = Integer.parseInt(newPIN.getText().toString());
                        if(sharedPreferences.getInt("user_pin") == enteredOldPIN){
                            sharedPreferences.setInt("user_pin",enteredNewPIN);
                            Toast.makeText(getActivity(),"Your New PIN has been set.",Toast.LENGTH_LONG).show();
                        }else {
                            oldPIN.setError(getResources().getString(R.string.wrong_pin));
                        }
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

    Preference.OnPreferenceChangeListener preferenceChangeListener = new Preference.OnPreferenceChangeListener() {

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            boolean switched = ((SwitchPreference) preference).isChecked();
            if (switched) {
                launchDialogDisable();
            } else {
                launchDialogEnable();
            }
            return false;
        }
    };

    private Preference.OnPreferenceClickListener onClickPreference = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            launchDialogChangePIN();
            return false;
        }
    };
}
