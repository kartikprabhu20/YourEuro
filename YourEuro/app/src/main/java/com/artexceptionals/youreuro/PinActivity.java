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
    private EditText PIN;
    private Button Login;
    private Button settingPIN;
    public String actualPIN;
    public String givenPIN;
    public static final String Default = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinactivity);
        settingPIN = (Button) findViewById(R.id.SetPIN);
        PIN = (EditText) findViewById(R.id.AppPIN);
        Login = (Button) findViewById(R.id.AppLogin);

    }

    public void Validation(View view) {
        givenPIN = PIN.getText().toString();
        actualPIN = CustomSharedPreferences.getInstance(PinActivity.this).genericGetString("user_pin");
        if (givenPIN.equals(Default)){
            Toast.makeText(this,"Please Enter the PIN",Toast.LENGTH_LONG).show();
        }
        else if (givenPIN.equals(actualPIN)) {
            Intent intent = new Intent(PinActivity.this, MainActivity.class);
            intent.putExtra(CORRECT_PIN,true);
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"PIN entered was wrong",Toast.LENGTH_LONG).show();
        }

    }

    public void CreatePIN(View view) {
        Intent intent1 = new Intent(PinActivity.this, SetPinActivity.class);
        startActivity(intent1);
    }
}

