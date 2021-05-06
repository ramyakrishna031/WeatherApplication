package com.onmicrosoft.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText cityName;
    Button Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            cityName = (EditText) findViewById(R.id.cityName);
            Btn = (Button) findViewById(R.id.Btn);

            Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CallSecondActivity();
                }
            });


            cityName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_DONE) {

                        CallSecondActivity();

                        return true;
                    }
                    return false;
                }
            });


        } catch (Exception e){
            e.getMessage();
        }
    }

    private void CallSecondActivity() {
        try {
            if(!cityName.getText().toString().equalsIgnoreCase("")) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("cityName", cityName.getText().toString());
                startActivity(i);
            }

        } catch (Exception e){
            e.getMessage();
        }
    }


}