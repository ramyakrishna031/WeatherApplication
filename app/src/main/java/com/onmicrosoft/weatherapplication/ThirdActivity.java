package com.onmicrosoft.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    TextView temp,feels,main,desc;
    RelativeLayout back_lv;
    TextView cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_third);

            Intent i = getIntent();

            cityName = (TextView) findViewById(R.id.cityName);
            temp = (TextView) findViewById(R.id.temp);
            feels = (TextView) findViewById(R.id.feels);
            main = (TextView) findViewById(R.id.main);
            desc = (TextView) findViewById(R.id.desc);
            back_lv = (RelativeLayout) findViewById(R.id.back_lv);


            cityName.setText(SecondActivity.cityName.getText().toString());
            temp.setText(i.getStringExtra("Temp"));
            feels.setText("Feels Like : "+i.getStringExtra("feels_like"));
            main.setText(i.getStringExtra("main"));
            desc.setText(i.getStringExtra("desc"));


            back_lv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });





        } catch (Exception e){
            e.getMessage();
        }
    }
}