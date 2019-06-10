package com.artexceptionals.youreuro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetPinActivity extends AppCompatActivity {

    private Button SetButton;
    private EditText GivenPIN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);
        SetButton = (Button)findViewById(R.id.SetBtn);
        GivenPIN = (EditText)findViewById(R.id.PinSet);
    }

    public void SettingPin(View view) {
        setPIN();
        Intent intent2 = new Intent(SetPinActivity.this,PinActivity.class);
        startActivity(intent2);
    }

    public void setPIN(){
        CustomSharedPreferences.getInstance(YourEuroApp.getAppContext()).setInt("user_pin", Integer.parseInt(GivenPIN.getText().toString()));
        Toast.makeText(this,"Your PIN has been set.",Toast.LENGTH_LONG).show();
    }

}
