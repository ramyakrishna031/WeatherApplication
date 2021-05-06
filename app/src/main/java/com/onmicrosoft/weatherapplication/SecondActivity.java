package com.onmicrosoft.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SecondActivity extends AppCompatActivity {

    public static TextView cityName;
    RelativeLayout back_lv;
    String URL = "",APIkey = "65d00499677e59496ca2f318eb68c049";
    Dialog dialog;
    RecyclerView List;
    WeatherAdapter weatherAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_second);
            dialog = new Dialog(SecondActivity.this);

            Intent i = getIntent();

            cityName = (TextView) findViewById(R.id.cityName);
            back_lv = (RelativeLayout) findViewById(R.id.back_lv);
            List = (RecyclerView) findViewById(R.id.List);

            cityName.setText(i.getStringExtra("cityName"));

            URL = "https://api.openweathermap.org/data/2.5/forecast?q="+cityName.getText().toString()+"&appid="+APIkey;

            back_lv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });


            WeatherList();


        } catch (Exception e){
            e.getMessage();
        }
    }

    public void WeatherList(){
        try{

            if(NetworkConnection.isNetworkConnected(SecondActivity.this)){

                try{
                    check_dialog();
                    Weather WHandler = new Weather();
                    WHandler.execute(URL);

                    System.out.println(URL);

                } catch (Exception e){

                   e.getMessage();

                }

            } else {
                Toast.makeText(SecondActivity.this,"Check Internet Connection",Toast.LENGTH_LONG).show();
            }

        } catch (Exception e){
            e.getMessage();
        }
    }

    public class Weather extends AsyncTask<String, String, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String...params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);

            if (res == null) {
                dialog.dismiss();
            } else {
                try {
                    JSONObject result = new JSONObject(res);

                    if(result.getString("cod").equalsIgnoreCase("200")){

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS"); //2021-05-06 12:00:00
                        JSONArray arr =  new JSONArray(result.getString("list"));

                        Date date = new Date();
                        Date date1 = sdf.parse(sdf.format(date));

                        boolean dateCompare = false;

                        ArrayList<ListModel> Details =  new ArrayList<ListModel>();

                        for(int i=0; i <arr.length();i++){

                            Date date2 = sdf.parse(arr.getJSONObject(i).getString("dt_txt"));

                            if(dateCompare == false){

                                if(date1.compareTo(date2) < 0)
                                {
                                    dateCompare = true;



                                    JSONObject main = new JSONObject(arr.getJSONObject(i).getString("main"));
                                    JSONArray weather = new JSONArray(arr.getJSONObject(i).getString("weather"));

                                    ListModel LM = new ListModel();
                                    LM.setName("Clear");
                                    LM.setTemp(main.getString("temp"));
                                    LM.setFeels_like(main.getString("feels_like"));
                                    LM.setMain(weather.getJSONObject(0).getString("main"));
                                    LM.setDesc(weather.getJSONObject(0).getString("description"));

                                    Details.add(LM);

                                    ListModel LM1 = new ListModel();
                                    LM1.setName("Cloudy");
                                    LM1.setTemp(main.getString("temp_min"));
                                    LM1.setFeels_like(main.getString("feels_like"));
                                    LM1.setMain(weather.getJSONObject(0).getString("main"));
                                    LM1.setDesc(weather.getJSONObject(0).getString("description"));

                                    Details.add(LM1);
                                }

                            }
                        }
                        System.out.println(Details.size());
                        weatherAdapter = new WeatherAdapter(SecondActivity.this,Details);
                        List.setLayoutManager(new LinearLayoutManager(SecondActivity.this, LinearLayoutManager.VERTICAL, false));
                        List.setItemAnimator(new DefaultItemAnimator());
                        List.setNestedScrollingEnabled(false);
                        List.setAdapter(weatherAdapter);
                        dialog.dismiss();



                    } else {
                        Toast.makeText(SecondActivity.this,result.getString("message"),Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();


                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                } catch (Exception e) {
                    dialog.dismiss();
                    e.getMessage();
                }
            }

        }
    }

    private void check_dialog() {
        try {
            ProgressBar pb;
            dialog.setContentView(R.layout.progressbar);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar);
            pb.getIndeterminateDrawable().setColorFilter(Color.parseColor("#013A71"), PorterDuff.Mode.MULTIPLY);
            dialog.show();
        }
        catch (Exception e){
           e.getMessage();
        }
    }
}