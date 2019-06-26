package com.artexceptionals.youreuro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PinActivity extends AppCompatActivity {

    public static final String CORRECT_PIN = "correct_pin";
    private EditText pinEditText;
    public int actualPIN, enteredPIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinactivity);
        pinEditText = (EditText) findViewById(R.id.AppPIN);
    }

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
        } else{
            Toast.makeText(this,"PIN entered was wrong",Toast.LENGTH_LONG).show();
        }
    }
}

