package com.artexceptionals.youreuro;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.makeText;

public class PinActivity extends AppCompatActivity {

    public static final String CORRECT_PIN = "correct_pin";
    public static final String SECURITY_QUESTION = "security_question";
    public static final String SECURITY_ANSWER = "security_answer";
    private EditText pinEditText,securityAnswerEditText;
    private int actualPIN, enteredPIN;
    CustomSharedPreferences sharedPreferences;

    @BindView(R.id.forgotPin_cb)
    CheckBox forgotPinCheckBox;

    @BindView(R.id.security_questions_layout)
    LinearLayout securityQuestionsLayout;

    @BindView(R.id.security_question)
    TextView securityQuestionTextView;

    @BindView(R.id.AppLogin)
    Button appLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinactivity);
        ButterKnife.bind(this);
        sharedPreferences = CustomSharedPreferences.getInstance(PinActivity.this);
        pinEditText = (EditText) findViewById(R.id.AppPIN);
        securityAnswerEditText = (EditText) findViewById(R.id.securityAnswer);
        forgotPinCheckBox.setOnClickListener(onClickListener);

        securityQuestionTextView.setText(sharedPreferences.genericGetString(SECURITY_QUESTION));
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId()== R.id.forgotPin_cb){
                if(forgotPinCheckBox.isChecked()){
                    securityQuestionsLayout.setVisibility(View.VISIBLE);
                    appLogin.setVisibility(View.GONE);
                }else {
                    securityQuestionsLayout.setVisibility(View.GONE);
                    appLogin.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    public void validation(View view) {

        if (pinEditText.getText().toString().isEmpty()){
            Toast.makeText(this,"PIN can't be empty",Toast.LENGTH_LONG).show();
            return;
        }

        enteredPIN = Integer.parseInt(pinEditText.getText().toString());
        actualPIN = CustomSharedPreferences.getInstance(YourEuroApp.getAppContext()).getInt("user_pin");
        if (enteredPIN == actualPIN) {
            Intent intent = new Intent(PinActivity.this, MainActivity.class);
            intent.putExtra(CORRECT_PIN,true);
            startActivity(intent);
            finish();
        } else{
            Toast.makeText(this,"PIN entered was wrong",Toast.LENGTH_LONG).show();
        }
    }

    public void validateAnswer(View view){
        String enteredAnswer = securityAnswerEditText.getText().toString();
        String actualAnswer = sharedPreferences.genericGetString(SECURITY_ANSWER);
        if(actualAnswer.equalsIgnoreCase(enteredAnswer)){
            launchDialogSetPIN();
        }else {
            securityAnswerEditText.setError("Security answer is not matching");
            securityAnswerEditText.requestFocus();
            securityAnswerEditText.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
        }
    }

    private void launchDialogSetPIN() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PinActivity.this);
        alertDialog.setTitle("SET PIN");

        LayoutInflater layoutInflater = LayoutInflater.from(PinActivity.this);
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.set_pin,null);
        alertDialog.setView(linearLayout);

        Spinner securityQuestionsSpinner_1 = (Spinner)linearLayout.findViewById(R.id.security_questions_spinner_1);
        ArrayAdapter<CharSequence> securityQuestionsAdapter_1 = ArrayAdapter.createFromResource(PinActivity.this,
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
                            Toast.makeText(PinActivity.this,"Your PIN is set",Toast.LENGTH_LONG).show();
                            pinEditText.setText("");
                            securityAnswerEditText.setText("");
                            forgotPinCheckBox.setChecked(false);
                            forgotPinCheckBox.callOnClick();

                        }else if(!securityAnswer.getText().toString().isEmpty()) {
                            Toast.makeText(PinActivity.this,"Your PIN is set",Toast.LENGTH_LONG).show();
                            launchDialogSetPIN();
                        }else if(!setPIN.getText().toString().isEmpty()) {
                            Toast.makeText(PinActivity.this, "Answer can't be empty", Toast.LENGTH_LONG).show();
                            launchDialogSetPIN();
                        }else {
                            Toast.makeText(PinActivity.this, "Pin or Answer can't be empty", Toast.LENGTH_LONG).show();
                            launchDialogSetPIN();
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

}

